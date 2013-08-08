package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.BreadthFirstSearchPlayer;
import s3games.player.Player;

/** Strategy class for BFS player */
public class BreadthFirstSearchStrategy extends Strategy
{
    /** return a new BFS player for the specified game 
     * @param specs the game to be played
     * @return a new instance of BFS player */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new BreadthFirstSearchPlayer(specs);
    }

    /** no learning for BFS yet - could learn the best path, and then play() could just follow it - future work... */
    @Override
    public void learn(Game game) 
    {
         throw new UnsupportedOperationException("Not supported yet.");
    }
}
