/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import java.util.Set;
import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.player.MonteCarloRatioPlayer;
import s3games.player.Player;

/**
 *
 * @author Nastavnik
 */
public class MonteCarloRatioStrategy extends Strategy{
    @Override
    public Player getPlayer(GameSpecification specs) {
      return new MonteCarloRatioPlayer();
    }

    @Override
    public void learn(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
