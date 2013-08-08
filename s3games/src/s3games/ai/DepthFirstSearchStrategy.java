package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.DepthFirstSearchPlayer;
import s3games.player.Player;

/** Strategy class for DFS player */
public class DepthFirstSearchStrategy extends Strategy
{
    /** return a new DFS player for the specified game 
     * @param specs the game to be played
     * @return a new instance of DFS player */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
        return new DepthFirstSearchPlayer(specs);
    }

    /** no learning for DFS yet - could learn the best path, and then play() could just follow it - future work... */
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}
