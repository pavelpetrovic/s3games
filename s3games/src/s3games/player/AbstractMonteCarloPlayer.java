package s3games.player;

import java.util.*;

import s3games.engine.GameState;
import s3games.engine.Move;

/** Holds common code shared by all monte-carlo players */
public abstract class AbstractMonteCarloPlayer extends Player
{
    /** make a move. run many random game developments from the current state, 
     * and look at which move has a higher success rate */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        startMove();
        
        ArrayList<Move> moves = new ArrayList<Move>(state.possibleMoves()); 
        if (moves.size() == 1) return allowedMoves.get(0);

        GameState[] gss = new GameState[moves.size()];
        
        for(int i = 0; i < moves.size(); ++i)             
        {
             initializeScore(i);
             gss[i] = state.getCopy();
             gss[i].performMove(moves.get(i));
        }
        
        performTrials(gss);

        double bestRatio = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for(int i = 0; i < moves.size(); ++i)
        {
            double score = calculateScore(i);
            if (score > bestRatio)
            {
                bestRatio = score;
                bestMove = moves.get(i);
            }
        }
        return bestMove;
    }
       
    /** add to the scores depending on the game result of this trial */
    protected abstract void addScore(GameState gs, int i);
    /** calculate the score of the player after all trials ended */
    protected abstract double calculateScore(int i);
    /** initialize the scores of all players before starting the trials */
    protected abstract void initializeScore (int i);
    
    /** initialize ratio that is specific for one trial */
    protected abstract void initializeRatio();
    /** update the ratio depending on the branching in the current state */
    protected abstract void updateRatio(GameState gs, Set<Move> moves);    

    /** do the actual work - start many trials of how the game will develop
     * from all the specified states and calculate the resulting scores for
     * each of the specified states. this part is the same for all monte-carlo
     * flavours. */
    private void performTrials(GameState[] ogs) throws Exception
    {
        Random random = new Random();
        while (ratioTimeLeft() > 0)
        {
            int firstMove = random.nextInt(ogs.length);
            
            GameState gs = ogs[firstMove].getCopy();
            initializeRatio();
            HashSet <GameState> visited = new HashSet <GameState>();
            visited.add(gs);
            int nodes = 1;            
            while ((nodes < maxNodes) && (ratioTimeLeft() > 0))
            {
                if (gs.winner != -1) break;
                Set<Move> moves = gs.possibleMoves();
                Iterator<Move>it = moves.iterator();
                while(it.hasNext())
                {
                    Move m = it.next();
                    GameState gs2 = gs.getCopy();
                    gs2.performMove(m);
                    if (visited.contains(gs2)) it.remove();
                }
                if (moves.isEmpty())  break;

                updateRatio(gs, moves);
                
                int i = random.nextInt(moves.size());                
                it = moves.iterator();
                while (i-- > 0) it.next();
                gs.performMove(it.next());
                nodes++;
                visited.add(gs);
            }
            addScore(gs, firstMove);
        }
    }
}
