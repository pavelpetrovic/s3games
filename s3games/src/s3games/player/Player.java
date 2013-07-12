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
    public enum playerType { HUMAN, COMPUTER };
    public enum boardType { REALWORLD, SIMULATED };

    public abstract Move move(GameState state, ArrayList<Move> allowedMoves);    
    public abstract void otherMoved(Move move, GameState newState);
}
