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
 * @author petrovic
 */
public class RandomGeneralPlayer extends Player
{
    GameSpecification specs;
    Random rnd;
    
    public RandomGeneralPlayer(GameSpecification specs)
    {
        this.specs = specs;
        rnd = new Random();
    }        
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) 
    {
        try { Thread.sleep(1000); } catch (Exception e) {}
        return allowedMoves.get(rnd.nextInt(allowedMoves.size()));
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {    
    }
    
}
