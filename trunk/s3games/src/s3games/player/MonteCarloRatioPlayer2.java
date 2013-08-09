package s3games.player;

import java.util.*;

import s3games.engine.GameState;
import s3games.engine.Move;

/** Monte carlo player where trials have differnt weight - depending
 * on the depth of the trial - the deeper the trial (the more moves needed),
 * the lower its contribution to the overall scores */
public class MonteCarloRatioPlayer2 extends AbstractMonteCarloPlayer 
{
    /** current trial ratio */
    private double q;
    
    /** the weighted number of winning trials for all players */
    private Map<Integer, Double> winning;
    
    /** the weighted number of losing trials for all players */
    private Map<Integer, Double> losing;
    
    /** the weighted number of other trials for all players */
    private Map<Integer, Double> other;
   
    /** construct this type of monte-carlo player */
    public MonteCarloRatioPlayer2 () 
    {
        winning = new HashMap<Integer, Double>();
        losing = new HashMap<Integer, Double>();
        other = new HashMap<Integer, Double>();
    }
   
    /** initialize the ratio at the beginning of each trial */
    @Override
    protected void initializeRatio() 
    {
        q = 1.0;
    }

    /** update the current trial ratio every time a move is made */
    @Override
    protected void updateRatio(GameState gs, Set<Move> moves) 
    {
        if (gs.currentPlayer != number) {
            q *= 0.9;
        }
    }    

    /** initialize the scores before all trials are started */
    @Override
    protected void initializeScore (int i) 
    {
        winning.put(i, 0.0);
        losing.put(i, 0.0);
        other.put(i, 0.0);
    }

    /** add the current trial to the overall scores */
    @Override
    protected void addScore(GameState gs, int i) 
    {
        if (gs.winner == number) 
            winning.put(i, winning.get(i)+q);
        else if (gs.winner > 0) 
            losing.put(i, losing.get(i)+q);
        else other.put(i, other.get(i)+q);
    }

    /** calculate the resulting scores after all trials completed */
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
