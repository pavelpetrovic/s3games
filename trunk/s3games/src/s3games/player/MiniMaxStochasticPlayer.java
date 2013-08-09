package s3games.player;

import java.util.*;

import s3games.ai.Heuristic;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;

/** Extension of the standard minimax player that considers only some of
 * the moves in each game state hoping to search more in depth */
public class MiniMaxStochasticPlayer extends MiniMaxPlayer 
{
    /** the percentage each of the moves will be used */
    public static double mmRatio = 0.7;
    
    /** random generator used by this player */
    private Random randomGenerator;
            
    /** create a minimax player for the specified game with the heuristic provided */
    public MiniMaxStochasticPlayer(GameSpecification specs, Heuristic heuristic)
    {
        super(specs, heuristic);
        randomGenerator = new Random();
    }
    
    /** the only difference to standard minimax player is that here we ignore some
     * of the moves when we expand a move */
    public HashMap<Move, GameState> expand(GameState state, HashSet<Move> moves, double ratio) throws Exception
    {
        if (moves.isEmpty()) return new HashMap<Move, GameState>();
        
        HashMap<Move, GameState> expanded = null;
        expanded = new HashMap<Move, GameState>();
        
        int countExpanded = 0;
        for (Move mv: moves)
        {
            if (randomGenerator.nextDouble() < ratio)           
            {
                GameState newState = state.getCopy();
                newState.performMove(mv);
                expanded.put(mv, newState);
                countExpanded++;
            }
        }
        
        if (countExpanded == 0)
        { 
            int selected = randomGenerator.nextInt(moves.size());
            Iterator<Move> it = moves.iterator();
            while (selected-- > 0)
                it.next();
            Move mv = it.next();
            GameState newState = state.getCopy();
            newState.performMove(mv);
            expanded.put(mv, newState);
        }
        
        return expanded;
    }
}
