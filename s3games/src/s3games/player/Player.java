/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.player;

import s3games.engine.Move;

/**
 *
 * @author petrovic16
 */
public abstract class Player
{
    public enum playerType { HUMAN, COMPUTER };
    public enum boardType { REALWORLD, SIMULATED };
    
    public abstract Move move();
}
