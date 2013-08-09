package s3games.player;

import java.util.*;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.gui.GameWindow;

/** Mouse player represents a human playing using/clicking the mouse in the
 * window with graphical visualisation of the game board */
public class MousePlayer extends Player
{
    /** local reference to the game specification */
    GameSpecification specs;
    /** local reference to the game window */
    GameWindow win;
    
    /** construct a new mouse player */
    public MousePlayer(GameSpecification specs, GameWindow win)
    {
        this.specs = specs;
        this.win = win;
    }        
    
    /** wait for the human user to perform a move and return it */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) 
    {
        try { 
            synchronized(win.lastMoveReady)
            {
                win.allowedMoves = allowedMoves;
                win.waitingForMove = true;
                win.lastMoveReady.wait();
            } 
        } catch (InterruptedException ex) {}
        return win.lastMove;
    }    
}
