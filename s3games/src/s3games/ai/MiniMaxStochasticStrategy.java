/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MiniMaxPlayer;
import s3games.player.MiniMaxStochasticPlayer;
import s3games.player.Player;

/**
 *
 * @author yann
 */
public class MiniMaxStochasticStrategy extends Strategy {
    public MiniMaxStochasticStrategy(Heuristic h)
    {
        setHeuristic(h);
    }
    
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new MiniMaxStochasticPlayer(specs, heuristic);
    }

    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
}
