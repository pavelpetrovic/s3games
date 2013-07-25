/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.*;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author Zuzka
 */
public class AStarPlayer extends Player {

    private GameSpecification specs;
    private PriorityQueue<Node> open; 
    private HashSet<GameState> visited;
     
    class Node implements Comparable{
        Move moveToThisState;
        GameState gs;
        Node previous;
        
        double distanceFromRoot;
        double estimatedDistance;
        
        Node(Node p,Move m, GameState g) {
            moveToThisState = m;
            previous = p;
            gs = g;
        }      
        
        public double getEstimation() {
           return distanceFromRoot+estimatedDistance;
        }
        
        @Override
        public int compareTo(Object o) {
            Node n = (Node)o;
            if (this.getEstimation() > n.getEstimation()) {
               return 1;
            } else 
            return (this.getEstimation()==n.getEstimation())?0:-1;
        }
    }
    public AStarPlayer(GameSpecification specs) {
        this.specs = specs;
    }
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception {
        try { Thread.sleep(1000); } catch (Exception e) {};
        open = new PriorityQueue<Node>();
        visited = new HashSet<GameState>();
        
        GameState activeState;
        visited.add(state);   //rooot
        open.add(new Node(null, null, state));
        
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
                        open.add(new Node(actualNode, mv,gs));  //previous state, move to this state, state
                    }
                }
            }
        }
        
        return allowedMoves.iterator().next();
    }

    @Override
    public void otherMoved(Move move, GameState newState) {
       
    }
    
}
