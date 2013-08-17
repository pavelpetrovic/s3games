package s3games.player;

import s3games.ai.Heuristic;
import s3games.engine.GameSpecification;

/** Extension of the standard minimax player that considers only some of
 * the moves in each game state hoping to search more in depth */
public class MiniMaxStochasticPlayer extends MiniMaxPlayer 
{
    /** create a minimax player for the specified game with the heuristic provided */
    public MiniMaxStochasticPlayer(GameSpecification specs, Heuristic heuristic)
    {
        super(specs, heuristic);        
        mmRatio = 0.7;
    }
}
