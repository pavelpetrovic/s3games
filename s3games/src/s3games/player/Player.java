/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.player;

import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author petrovic16
 */
public abstract class Player
{
    public enum playerType { HUMAN, COMPUTER };
    public enum boardType { REALWORLD, SIMULATED };

    public abstract Move move(GameState state);
    
    /** after a player made an illegal move, this method will be called,
     * it has a chance to return true to be allowed to try to make another move */
    public boolean retryMoveNotAllowed()
    {
        return false;  //default behaviour - player will lose the game
    }
    
    /** notifies the player that the move returned by move() was correct */
    public void moveApproved(GameState state)
    {        
    }
    
    public abstract void otherMoved(Move move, GameState newState);
}
