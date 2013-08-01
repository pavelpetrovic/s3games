/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author Nastavnik
 */
public class MonteCarloRatioPlayer extends MonteCarloPlayer {
    private double q = 1;
    private double winning = 0;
    private double losing = 0;
    private double other = 0;
        
    @Override
    protected void initializeRatio() {
        q = 1.0;
    }

    @Override
    protected void updateRatio(GameState gs, Set<Move> moves) {
        if (gs.currentPlayer != number) {                 
            q *= 1.0/(double)moves.size();
        }        
    }    

    @Override
    protected void initializeScore () {
        winning = 0.0;
        losing  = 0.0;
        other   = 0.0;
    }

    @Override
    protected void addScore(GameState gs) {
        if (gs.winner == number) {
            winning+=q;
        }
        else if (gs.winner > 0) {
            losing+=q;
        }
        else {
            other+=q;
        }
    }

    @Override
    protected double calculateScore() {
        return (winning-losing)/(winning+losing+other);
    }
}
