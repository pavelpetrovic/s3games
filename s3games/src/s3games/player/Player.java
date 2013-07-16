/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.player;

import java.util.ArrayList;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author petrovic16
 */
public abstract class Player
{
    protected int number;
    public enum playerType { HUMAN, COMPUTER };
    public enum boardType { REALWORLD, SIMULATED };

    public abstract Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception;   
    public abstract void otherMoved(Move move, GameState newState);
    
    public void setPlayerNumber(int pn) {
        number = pn;
    }
}
