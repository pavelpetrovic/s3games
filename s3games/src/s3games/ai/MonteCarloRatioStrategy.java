package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MonteCarloRatioPlayer;
import s3games.player.Player;

/** Strategy class for the monte carlo ratio player. The winning/losing states are scaled by a weight coefficient that depends on branching in all opponent nodes that are on the way to that state (as contrasted to -1/1 in classical monte carlo).*/
public class MonteCarloRatioStrategy extends Strategy
{
    /** returns a monte carlo ratio player for the specified game */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
      return new MonteCarloRatioPlayer();
    }

    /** constructs the a full game tree with almost winning strategy so that play() will then be able to play according to it. remains for future work */
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
