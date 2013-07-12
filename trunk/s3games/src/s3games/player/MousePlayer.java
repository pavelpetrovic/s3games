/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        win.waitingForMove = true;
        try { win.lastMoveReady.wait(); } catch (InterruptedException ex) {}
        win.waitingForMove = false;
        return win.lastMove;
    }

    @Override
    public void otherMoved(Move move, GameState newState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
