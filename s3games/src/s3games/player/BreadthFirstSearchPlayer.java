package s3games.player;

import java.util.*;
import s3games.engine.GameState;
import s3games.engine.Move;

/** Standard Breadth-first-search player for one-player games. */
public class BreadthFirstSearchPlayer extends Player
{
    /** FIFO queue of open nodes that are waiting to be processed */
    private Queue<Node> open;
    /** set of visited states */
    private HashSet<GameState> visited;
     
    /** nodes placed in the open list remember how they were formed and
     * from where so that we can recover the path from the root state to 
     * the winning state */
    class Node 
    {
        /** a move that was performed to get to this state */
        Move moveToThisState;
        /** the game state of this node */
        GameState gs;
        /** node from where we got here */
        Node previous;
        
        /** constructs a new node remembering the previous node and the move */
        Node(Node p, Move m, GameState g) 
        {
            moveToThisState = m;
            previous = p;
            gs = g;
        }
    }
    
    /** take a move using BFS algorithm. Runs the complete state-space search until
     * the closest winning state is found, then returns the move that goes in that
     * direction */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        try { Thread.sleep(1000); } catch (Exception e) {}
        open = new LinkedList<Node>();
        visited = new HashSet<GameState>();
        
        GameState activeState;
        visited.add(state);   //rooot
        open.add(new Node(null, null, state));
        
        while (open.size() > 0)  //while fifo stack(queue) is not empty
        {            
            Node actualNode = open.poll();   //retrieve and remove head of the queue
            activeState = actualNode.gs;
            
            Set<Move> possibleMoves = activeState.possibleMoves();
            for (Move mv: possibleMoves) 
            {
                GameState gs = activeState.getCopy();
                gs.performMove(mv);
                if (!visited.contains(gs)) {   //after move that new state  wasnt examined yet
                    visited.add(gs);  //prevent of repeating same states waiting for examination in open
                    if (gs.winner == number)    //then find path to the first move 
                    {
                        if (actualNode.previous == null) return mv;
                        while(actualNode.previous.previous!=null) 
                               actualNode = actualNode.previous;
                        return actualNode.moveToThisState;
                    }
                    if (gs.winner == -1) {  //game continues
                        open.add(new Node(actualNode, mv,gs));  //previous state, move to this state, state
                    }
                }
            }
        }
        
        return allowedMoves.iterator().next();
    }
}
