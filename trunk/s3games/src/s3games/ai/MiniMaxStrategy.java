package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.MiniMaxPlayer;
import s3games.player.Player;

/** Strategy class for the minimax player. */
public class MiniMaxStrategy extends Strategy
{    
    /** constructor requires a heuristic for evaluating states that are beyond the time limit */
    public MiniMaxStrategy(Heuristic h)
    {
        setHeuristic(h);
    }
    
    /** returns a minimax player for the specified game */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new MiniMaxPlayer(specs, heuristic);
    }

    /** constructs the a full game tree with winning strategy so that play() will then be able to play according to it. remains for future work */
    @Override
    public void learn(Game game) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
