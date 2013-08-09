package s3games.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/** This is the default version of monte carlo player, it only 
 * looks at the number of won trials compared to the number of all trials */
public class MonteCarloClassicPlayer extends AbstractMonteCarloPlayer 
{
    /** the number of winning trials for each player */
    private Map<Integer, Integer> win;
    /** the number of all other trials for each player */
    private Map<Integer, Integer> other;
    
    /** construct a new classical monte carlo player */
    public MonteCarloClassicPlayer() 
    {
        win = new HashMap<Integer, Integer>();
        other = new HashMap<Integer, Integer>();
    }
    
    /** classical monte-carlo has always ration 1.0 */
    @Override
    protected void initializeRatio() 
    {
    }
    
    /** we add 1, if we won */
    @Override
    protected void addScore(GameState gs, int playerNumber) 
    {
        if (gs.winner == number) win.put(playerNumber, win.get(playerNumber)+1);
        else other.put(playerNumber, other.get(playerNumber)+1);
    }
    
    /** the number of trials won against the total number of games */
    @Override
    protected double calculateScore(int playerNumber) 
    {
        int ot = other.get(playerNumber);
        int wn = win.get(playerNumber);
        if (ot + wn == 0) return 0;
        return (double)wn / (double)(ot + wn);
    }

    /** before the first trial, reset the number of winning/other trials */
    @Override
    protected void initializeScore (int playerNumber) 
    {
        win.put(playerNumber, 0);
        other.put(playerNumber, 0);
    }
        
    /** classical monte-carlo does not update the ratio */
    @Override
    protected void updateRatio(GameState gs, Set<Move> moves) 
    {
    }    
}
