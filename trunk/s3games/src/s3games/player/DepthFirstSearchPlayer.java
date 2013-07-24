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
 * @author zuzycka
 */
public class DepthFirstSearchPlayer extends Player {
    
    private GameSpecification specs;
    private ArrayList<Node> open; 
    private HashSet<GameState> visited;
    private HashSet<GameState> history;
    
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
        history = new HashSet<GameState>();
    }
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception {
        try { Thread.sleep(1000); } catch (Exception e) {};
        open = new ArrayList<Node>();
        visited = new HashSet<GameState>();
        visited.addAll(history);
        
        GameState activeState;
        history.add(state.getCopy());
        visited.add(state);
        open.add(new Node(null, null, state));
        System.out.println(history.size());
       
        while (open.size()>0) 
        {
                Node actualNode = open.get(open.size()-1);
                activeState = actualNode.gs;
                open.remove(open.size()-1);
                
                ArrayList<Move> possibleMoves = activeState.possibleMoves();
                for(int i=0; i<possibleMoves.size();i++) 
                {
                    GameState gs = activeState.getCopy(); 
                    gs.performMove(possibleMoves.get(i)); //perfom move and get next state
                    if ((!visited.contains(gs)) && (!history.contains(gs))) {
                        visited.add(gs);      //to prevent adding same neighbours
                        if (gs.winner==number)   //find a path to the first move
                        {
                            if (actualNode.previous == null) return possibleMoves.get(i);
                            while(actualNode.previous.previous!=null) 
                                   actualNode = actualNode.previous;
                            return actualNode.moveToThisState;
                        }
                        if (gs.winner==-1)   //not visited and not finished
                           open.add(new Node(actualNode,possibleMoves.get(i),gs));    
                    }
                }
        }        
        return allowedMoves.get(0);
    }
    
    @Override
    public void otherMoved(Move move, GameState newState) {
    
    }
    
}
