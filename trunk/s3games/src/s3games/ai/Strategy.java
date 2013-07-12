/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.engine.Move;
import s3games.player.Player;

/**
 *
 * @author petrovic16
 */
public abstract class Strategy
{
    public abstract Player getPlayer(GameSpecification specs);
    public abstract void learn(Game game);

    public static String[] availableStrategies(String gameName)
    {
        return new String[] {"Random"};
    }
    
    public static boolean learnable(String strategyName)
    {
        return true;
    }
    
    public static Strategy getStrategy(String name)
    {
        if (name.equals("Random"))
            return new RandomGeneralStrategy();
        return null;
    }
}
