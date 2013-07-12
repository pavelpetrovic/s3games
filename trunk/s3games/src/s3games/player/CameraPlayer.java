/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author petrovic
 */
public class CameraPlayer extends Player
{
    GameSpecification specs;
    
    public CameraPlayer(GameSpecification specs)
    {
        this.specs = specs;
    }        
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) 
    {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
