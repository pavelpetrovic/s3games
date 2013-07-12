/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.gui.GameWindow;

/**
 *
 * @author petrovic
 */
public class MousePlayer extends Player
{
    GameSpecification specs;
    GameWindow win;
    
    public MousePlayer(GameSpecification specs, GameWindow win)
    {
        this.specs = specs;
        this.win = win;
    }        
    
    @Override
    public Move move(GameState state) 
    {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void otherMoved(Move move, GameState newState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
