/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MonteCarloRatioPlayer;
import s3games.player.MonteCarloRatioPlayer2;
import s3games.player.Player;

/**
 *
 * @author yann
 */
public class MonteCarloRatioStrategy2 extends Strategy{
    @Override
    public Player getPlayer(GameSpecification specs) {
      return new MonteCarloRatioPlayer2();
    }

    @Override
    public void learn(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
