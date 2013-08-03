/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author Nastavnik
 */
public abstract class AbstractMonteCarloPlayer extends Player{
        
    @Override
    public void otherMoved(Move move, GameState newState) 
    {
    }

    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        startMove();
        ArrayList<Move> moves = new ArrayList<Move>(state.possibleMoves()); 

        GameState[] gss = new GameState[moves.size()];
        
        for(int i = 0; i < moves.size(); ++i)             
        {
             initializeScore(i);
             gss[i] = state.getCopy();
             gss[i].performMove(moves.get(i));
        }

        calculateRatio(gss);

        double bestRatio = Double.NEGATIVE_INFINITY;
        Move bestMove = null ;

        for(int i = 0; i < moves.size(); ++i)             
        {
            double score = calculateScore(i);
            System.out.println(bestMove + ": " + score);
            
            if (score > bestRatio)
            {
                bestRatio = score;
                bestMove = moves.get(i);
            }
        }

        return bestMove;
    }
    
    protected abstract void initializeRatio();
    protected abstract void addScore(GameState gs, int i);
    protected abstract double calculateScore(int i);
    protected abstract void initializeScore (int i);
    protected abstract void updateRatio(GameState gs, Set<Move> moves);    
    
    private void calculateRatio(GameState[] ogs) throws Exception
    {
        Random random = new Random();
//        for (int trial = 0; trial < 200; trial++)
        while (ratioTimeLeft() > 0)
        {
            //if (trial % 10 == 0) System.out.println("trisl " + trial);
            int firstMove = random.nextInt(ogs.length);
            
            GameState gs = ogs[firstMove].getCopy();
            initializeRatio();
            HashSet <GameState> visited = new HashSet <GameState>();
            visited.add(gs);
            int nodes = 1;
            boolean debug=false;
            while (nodes < maxNodes && ((ratioTimeLeft() > 0) || debug))
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
                //System.out.println(i + " of " + moves.size());
                it = moves.iterator();
                while (i-- > 0) it.next();
                gs.performMove(it.next());
                nodes++;
                visited.add(gs);
            }
            
            //System.out.println("adding " + gs.winner + " to " + firstMove);
            addScore(gs, firstMove);
        }
    }
}
