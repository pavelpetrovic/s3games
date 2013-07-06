/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.ai;

import s3games.engine.Game;
import s3games.engine.Move;

/**
 *
 * @author petrovic16
 */
public abstract class Strategy
{
    public abstract void play(Game game);
    public abstract void learn(Game game);
    public abstract Move move();
}
