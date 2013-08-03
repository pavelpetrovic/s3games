/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.*;
import s3games.ai.*;
import s3games.engine.*;

/**
 *
 * @author petrovic
 */
public class MiniMaxPlayer extends Player
{
    public enum NodeType { MIN, MAX };
    
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

    public class Node implements Comparable
    {
        Node previous;
        double value;
        NodeType type;
        int depth;
        
        Node(Node previous, double value, NodeType type, int depth)
        {
            this.previous = previous;
            this.value = value;
            this.type = type;
            this.depth = depth;
        }
        
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

        @Override
        public int compareTo(Object o) 
        {
            Node other = (Node)o;
            if (depth > other.depth) return -1;
            else if (depth < other.depth) return 1;
            else return 0;
        }
    }
    
    public class Leaf
    {
        Node previous;
        GameState gs;        
        
        public Leaf(GameState gs, Node previous)
        {
            this.gs = gs;
            this.previous = previous;
        }
    }
    
    Heuristic heuristic;
    GameSpecification specs;
    HashMap<GameState, HashMap<Move, GameState>> openedStates;
    LinkedList<Leaf> leaves;
    PriorityQueue<Node> modified;
    long nodesOpened;
    
    public MiniMaxPlayer(GameSpecification specs, Heuristic heuristic)
    {
        this.specs = specs;
        this.heuristic = heuristic;
        openedStates = new HashMap<GameState, HashMap<Move, GameState>>();        
    }

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

    public HashMap<Move, GameState> expand(GameState state, HashSet<Move> moves) throws Exception
    {
        HashMap<Move, GameState> expanded = openedStates.get(state);
        if (expanded == null)
        {
            expanded = new HashMap<Move, GameState>();
            for (Move mv: moves)
            {
                GameState newState = state.getCopy();
                newState.performMove(mv);      
                expanded.put(mv, newState);                
            }            
            openedStates.put(state, expanded);
            if (openedStates.size() > maxCacheSize) 
            {
                Iterator<GameState> it = openedStates.keySet().iterator();
                it.next();
                it.remove();
            }
        }
        return expanded;
    }
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        startMove();
        nodesOpened = 0;
        if (allowedMoves.size() == 1) return allowedMoves.get(0);
        
        HashMap<Move, Node> topMoves = new HashMap<Move, Node>();
        HashMap<Move, GameState> newStates = expand(state, new HashSet<Move>(allowedMoves));
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
        return bestMove;
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {
    }    
}
