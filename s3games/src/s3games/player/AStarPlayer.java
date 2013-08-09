package s3games.player;

import java.util.*;
import s3games.ai.Heuristic;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;

/** A* algorithm player that utilizes a heuristic */
public class AStarPlayer extends Player {

    /** local reference to the game specification */
    private GameSpecification specs;
    /** list of open nodes */
    private PriorityQueue<Node> open; 
    /** list of visited game states */
    private HashSet<GameState> visited;
    /** reference to the heuristic used */
    private Heuristic heuristic;
     
    /** a game state that is waiting to be processed in the open list */
    class Node implements Comparable
    {
        /** by which move, how did we get to this state */
        Move moveToThisState;
        /** the current state */
        GameState gs;
        /** from where did we get here */
        Node previous;        
        
        /** how many steps did it take to get here */
        double distanceFromRoot;
        /** how far do we foresee a winning end */
        double estimatedDistance;
        
        /** construct a new node with the given parameters 
         * @param p node from which we arrived to this state
         * @param m move that we performed to get to this state
         * @param g the state we are in
         * @param d the estimated distance to a winning goal state */
        Node(Node p, Move m, GameState g,  double d) 
        {
            moveToThisState = m;
            previous = p;
            gs = g;
            if (p!=null)
                this.distanceFromRoot = p.distanceFromRoot + 1;
            else
                distanceFromRoot = 0;
            this.estimatedDistance = d;
        }      
        
        /** A* value function: f(state) = h(state) + g(state) */
        public double getEstimation() 
        {
           return distanceFromRoot+estimatedDistance;
        }
        
        /** priority queue will order the nodes in the open list based on the A* value function */
        @Override
        public int compareTo(Object o) 
        {
            Node n = (Node)o;
            if (this.getEstimation() > n.getEstimation()) {
               return 1;
            } else 
            return (this.getEstimation()==n.getEstimation())?0:-1;
        }
    }
    
    /** construct a new A* player for the specified game with the given heuristic */
    public AStarPlayer(GameSpecification specs, Heuristic heuristic) 
    {
        this.specs = specs;
        this.heuristic = heuristic;
    }
    
    /** A* player makes a move - it searches all the way to find the closest winning state and performs a move that is leading towards it. */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        try { Thread.sleep(1000); } catch (Exception e) {};
        open = new PriorityQueue<Node>();
        visited = new HashSet<GameState>();
        
        GameState activeState;
        visited.add(state);   //rooot
        open.add(new Node(null, null, state, heuristic.heuristic(state, number)));
        
        while (open.size()>0)  //while fifo stack(queue) is not empty
        {            
            Node actualNode = open.poll();   //retrieve and remove head of the queue
            activeState = actualNode.gs;
            
            Set<Move> possibleMoves = activeState.possibleMoves();
            for (Move mv : possibleMoves) 
            {
                GameState gs = activeState.getCopy();
                gs.performMove(mv);
                if (!visited.contains(gs)) {   //after move that new state  wasnt examined yet
                    visited.add(gs);  //prevent of repeating same states waiting for examination in open
                    if (gs.winner==number)    //then find path to the first move 
                    {
                        if (actualNode.previous == null) return mv;
                        while(actualNode.previous.previous!=null) 
                               actualNode = actualNode.previous;
                        return actualNode.moveToThisState;
                    }
                    if (gs.winner == -1) {  //game continues
                        open.add(new Node(actualNode, mv,gs,heuristic.heuristic(gs, number)));  //previous state, move to this state, state
                    }
                }
            }
        }
        
        return allowedMoves.iterator().next();
    }
}
