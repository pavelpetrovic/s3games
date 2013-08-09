package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.Player;
import s3games.player.RandomGeneralPlayer;

/** A strategy class for the general random player */
public class RandomGeneralStrategy extends Strategy
{
    /** random does not learn */
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }    

    /** returns a random general player instance for the specified game */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
        return new RandomGeneralPlayer();        
    }
}
