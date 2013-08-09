package s3games.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/** A version of a monte-carlo player that calculates the winning trials
 * scaled with a quotient that is calculated as a multiply of 1/N_i constants,
 * where N_i is branching in the opponent move state. The idea is that the
 * winning trial that is far and prone to not be selected due to other options
 * of the opponent should be less valuable than a winning trial that is reachable
 * in each case regardless the opponents selection. */
public class MonteCarloRatioPlayer extends AbstractMonteCarloPlayer 
{
    /** the accumulative quotient */
    private double q;
    
    /** the weighted number of winning trials for each player */
    private Map<Integer, Double> winning;
    
    /** the weighted number of losing trials for each player */
    private Map<Integer, Double> losing;
    
    /** the weighted number of draw/undecided trials for each player */
    private Map<Integer, Double> other;
   
    /** construct a new monte carlo player */
    public MonteCarloRatioPlayer () 
    {
        winning = new HashMap<Integer, Double>();
        losing = new HashMap<Integer, Double>();
        other = new HashMap<Integer, Double>();
    }
   
    /** each trial starts with ratio = 1 */
    @Override
    protected void initializeRatio() 
    {
        q = 1.0;
    }

    /** opponent moves are taxing the ratio depending on the state neighborhood size */
    @Override
    protected void updateRatio(GameState gs, Set<Move> moves) 
    {
        if (gs.currentPlayer != number)         
            q *= 1.0/(double)moves.size();
    }    

    /** initialize the scores before all trials are started */
    @Override
    protected void initializeScore (int i) 
    {
        winning.put(i, 0.0);
        losing.put(i, 0.0);
        other.put(i, 0.0);
    }

    /** add the score after each trial */
    @Override
    protected void addScore(GameState gs, int i) 
    {
        if (gs.winner == number) 
            winning.put(i, winning.get(i)+q);
        else if (gs.winner > 0) 
            losing.put(i, losing.get(i)+q);
        else other.put(i, other.get(i)+q);
    }

    /** calculate the score after all trials finished */
    @Override
    protected double calculateScore(int i) 
    {
        double wn = winning.get(i);
        double lo = losing.get(i);
        double ot = other.get(i);
        if (ot + lo + wn == 0) return 0.0;
        return (wn - lo) / (wn + lo + ot);
    }
}
