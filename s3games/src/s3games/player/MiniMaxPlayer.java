package s3games.player;

import java.util.*;
import s3games.ai.*;
import s3games.engine.*;

/** Minimax player searches the game tree in a breadth-first search manner,
 * taking the best result on the players move and the worst result on the
 * opponent move into account. */
public class MiniMaxPlayer extends Player
{
    /** opened nodes are MAX if it is this player's turn, states when it is opponent's turn are MIN */
    public enum NodeType { MIN, MAX };
    
    /** creates a new node that was reached by performing some move from the previous node.
     * the method opens the nodes and adds all resulting states to the leaves list */
    public Node newNode(Node previous, GameState gs) throws Exception
    {
        NodeType type = NodeType.MIN;
        if (gs.currentPlayer == number) type = NodeType.MAX;        
        
        double val;
        if (type == NodeType.MIN) val = Double.POSITIVE_INFINITY;
        else val = Double.NEGATIVE_INFINITY;    
        
        int depth = 1;
        if (previous != null) depth = previous.depth + 1;
        
        Node node = new Node(previous, val, type, depth);
        node.open(gs);
        
        return node;
    }

    /** holds the information about the state, the best/worst value (depending on 
     * the node type, and the depth that is used when propagating the values
     * from the leaf nodes up the search tree */
    public class Node implements Comparable
    {
        /** previous node from which we got here by performing a move */
        Node previous;
        /** the current best/worst estimate of the state value +1/-1 for winning/losing, or another value if score is involved */
        double value;
        /** node type min/max */
        NodeType type;
        /** nodes propagate their value up the priority queue ordered by the depth */
        int depth;
        
        /** create a new node with the specified values */
        Node(Node previous, double value, NodeType type, int depth)
        {
            this.previous = previous;
            this.value = value;
            this.type = type;
            this.depth = depth;
        }
        
        /** open the game state and insert all neighboring states to list of leaves */
        void open(GameState state) throws Exception
        {            
            if (state.winner >= 0)
                value = valueOfWinner(state.winner);
            else
            {
                HashMap<Move, GameState> newStates = expand(state, state.possibleMoves()); 
                if (newStates.isEmpty())
                    value = 0;
                else
                {
                    for(GameState gs: newStates.values())
                        leaves.add(new Leaf(gs, this));
                    nodesOpened += newStates.size();
                    return;
                }
            }          
            if (previous != null)
                previous.update(value);
        }
        
        /** update the min or max value of this node, add the node to the 
         * modified priority list, if its value has changed to update its
         * parent nodes */
        void update(double val)
        {
            if (type == NodeType.MAX)
            {
                if (val > value)
                    value = val;
                else return;
            }
            else if (val < value)
                value = val;
            else return;
            modified.add(this);
        }

        /** nodes are ordered in the priority queue based on their depth so that
         * the value updating propagates always in a bottom-up manner */
        @Override
        public int compareTo(Object o) 
        {
            Node other = (Node)o;
            if (depth > other.depth) return -1;
            else if (depth < other.depth) return 1;
            else return 0;
        }
    }
    
    /** leaf that is waiting to be opened */
    public class Leaf
    {
        /** previous node from which a move was performed to obtain this game state */
        Node previous;
        /** the game state of this leaf */
        GameState gs;        
        
        /** construct a new leaf */
        public Leaf(GameState gs, Node previous)
        {
            this.gs = gs;
            this.previous = previous;
        }
    }
    
    /** all leaves that were not opened are evaluated using this heuristic when 
     * the time is gone */
    Heuristic heuristic;
    /** game we are playing */
    GameSpecification specs;
    /** list of all nodes that are waiting to be opened */
    LinkedList<Leaf> leaves;
    /** this queue holds all nodes in which the MIN or MAX value was modified 
     * based on request from a child node */
    PriorityQueue<Node> modified;
    /** number of nodes opened in this move so far */
    long nodesOpened;
    
    /** create a minimax player for the specified game with the heuristic provided */
    public MiniMaxPlayer(GameSpecification specs, Heuristic heuristic)
    {
        this.specs = specs;
        this.heuristic = heuristic; 
    }

    /** determine the value of the state if we know the number of the winning player */ 
    double valueOfWinner(int winner)
    {
        double val = Double.NaN;        
        if (winner == number)
            val = 1;
        else if (winner == 0)
            val = 0;
        else if (winner > 0)
            val = -1;    
        return val;
    }

    /** performs all requested moves from the specified state obtaining 
     * a list of new states, indexed through the moves */
    public HashMap<Move, GameState> expand(GameState state, HashSet<Move> moves) throws Exception
    {
        HashMap<Move, GameState> expanded = null;
        expanded = new HashMap<Move, GameState>();
        for (Move mv: moves)
        {
            GameState newState = state.getCopy();
            newState.performMove(mv);      
            expanded.put(mv, newState);                
        }
        return expanded;
    }
    
    /** make one minimax move: try to expand the game tree as far as it gets,
     * evaluating the rest with the heuristic when the time is used up */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        startMove();
        nodesOpened = 0;
                
        HashSet<Move> possibleMoves = new HashSet<Move>(allowedMoves);
        if (possibleMoves.size() == 1) return allowedMoves.get(0);
        
        HashMap<Move, Node> topMoves = new HashMap<Move, Node>();
        HashMap<Move, GameState> newStates = expand(state, possibleMoves);
        modified = new PriorityQueue<Node>();
        leaves = new LinkedList<Leaf>();
        
        for(Map.Entry<Move, GameState> mv: newStates.entrySet())
            topMoves.put(mv.getKey(), newNode(null, mv.getValue()));
        
//        while ((nodesOpened < maxNodes) && (!leaves.isEmpty()))
        while (ratioTimeLeft() > 0 && !leaves.isEmpty())
        {
            Leaf lf = leaves.poll();
            if (lf.gs.winner >= 0)
                lf.previous.update(valueOfWinner(lf.gs.winner));
            else
                newNode(lf.previous, lf.gs);
        }
        
        while (!leaves.isEmpty())
        {
            Leaf lf = leaves.poll();
            double val = valueOfWinner(lf.gs.winner);
            if (Double.isNaN(val))
                val = heuristic.heuristic(lf.gs, number);
            lf.previous.update(val);                
        }
        
        while (!modified.isEmpty())
        {
            Node node = modified.poll();
            if (node.previous != null) node.previous.update(node.value);
        }
        
        double max = Double.NEGATIVE_INFINITY;
        Move bestMove = null;
        for (Map.Entry<Move, Node> mv: topMoves.entrySet())
        {
            double val = mv.getValue().value;
            if (val > max)
            {
                max = val;
                bestMove = mv.getKey();
            }
        }
        System.out.println("Minimax - total nodes: " + nodesOpened);                  
        return bestMove;
    }
}
