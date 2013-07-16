/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zuzycka
 */
public class DepthFirstSearchPlayer extends Player {
    
    private GameSpecification specs;
    private ArrayList<Node> open; 
    private HashSet<GameState> closed;
    
    class Node {
        Move moveToThisState;
        GameState gs;
        Node previous;
        
        Node(Node p,Move m, GameState g) {
            moveToThisState = m;
            previous = p;
            gs = g;
        }
        
    }

    public DepthFirstSearchPlayer(GameSpecification specs) {
        this.specs = specs;
    }
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) {
        try { Thread.sleep(1000); } catch (Exception e) {};
        open = new ArrayList<Node>();
        closed = new HashSet<GameState>();
        
        GameState activeState;
        open.add(new Node(null, null, state));
       
        while (open.size()>0) {
            try {
                Node actualNode = open.get(open.size()-1);
                activeState = actualNode.gs;
                open.remove(open.size()-1);
                closed.add(activeState);
                
                ArrayList<Move> possibleMoves = activeState.possibleMoves();
                for(int i=0; i<possibleMoves.size();i++) {
                    GameState gs = activeState.getCopy();
                   
                    gs.performMove(possibleMoves.get(i));
                    if (gs.winner==number) {  //find a path to the first move
                          while(actualNode.previous!=null) {
                               actualNode = actualNode.previous;
                          }  
                         return actualNode.moveToThisState;
                    }
                    if (!closed.contains(gs) && gs.winner==-1)   //not visited and not finished
                       open.add(new Node(actualNode,possibleMoves.get(i),gs));
                
                }
            } catch (Exception ex) {
                Logger.getLogger(DepthFirstSearchPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return allowedMoves.get(0);
    }
    
    @Override
    public void otherMoved(Move move, GameState newState) {
    
    }
    
}
