package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MonteCarloClassicPlayer;
import s3games.player.Player;

/** Strategy class for the monte carlo player. The game is played many times from the current state, and all winning/other situations are counted. */
public class MonteCarloStrategy extends Strategy 
{
    /** returns a monte carlo player for the specified game */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
      return new MonteCarloClassicPlayer();
    }

    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
