/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.AStarPlayer;
import s3games.player.MiniMaxPlayer;
import s3games.player.Player;

/**
 *
 * @author petrovic
 */
public class MiniMaxStrategy extends Strategy
{
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new MiniMaxPlayer(specs, heuristic);
    }

    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
