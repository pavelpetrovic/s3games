package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MiniMaxStochasticPlayer;
import s3games.player.Player;

/** Strategy class for the minimax stochastic player. It is equivalent to minimax player, but ignores some of the possible moves */
public class MiniMaxStochasticStrategy extends Strategy 
{
    /** constructor requires a heuristic for evaluating states that are beyond the time limit */
    public MiniMaxStochasticStrategy(Heuristic h)
    {
        setHeuristic(h);
    }
    
    /** returns a minimax stochastic player for the specified game */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new MiniMaxStochasticPlayer(specs, heuristic);
    }

    /** constructs the a partial game tree with almost winning strategy so that play() will then be able to play according to it. remains for future work */
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
}
