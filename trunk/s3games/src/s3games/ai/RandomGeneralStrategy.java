/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.Player;
import s3games.player.RandomGeneralPlayer;

/**
 *
 * @author petrovic
 */
public class RandomGeneralStrategy extends Strategy
{
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }    

    @Override
    public Player getPlayer(GameSpecification specs) 
    {
        return new RandomGeneralPlayer(specs);        
    }
}
