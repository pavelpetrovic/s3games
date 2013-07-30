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
public class MonteCarloPlayer extends Player {

    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        double bestRatio = Double.NEGATIVE_INFINITY;
        Move bestMove = null ;
        Set<Move> moves = state.possibleMoves(); 
        for(Move move: moves)             
        {
             GameState gs = state.getCopy();
             gs.performMove(move);
             double ratio = calculateRatio (gs,number);
             if (ratio > bestRatio)
             {
                 bestRatio = ratio;
                 bestMove = move;
             }
        }
        return bestMove;  
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {      
    }

    private double calculateRatio(GameState gs, int number) throws Exception
    {
        int win = 0, other = 0;
        Random random = new Random();
        for (int trial = 0; trial < 5000; trial++)
        {
            HashSet <GameState> visited = new HashSet <GameState>();
            visited.add(gs);
            int nodes = 1;
            while (nodes < maxNodes)
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
                int i = random.nextInt(moves.size());
                it = moves.iterator();
                while (i-- > 0) it.next();
                gs.performMove(it.next());
                nodes++;
                visited.add(gs);
            }
            if (gs.winner == number) win++;
            else other++;
        }
        return (double)win/(double)(other + win);
    }
}
