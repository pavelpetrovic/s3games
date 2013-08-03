/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author Nastavnik
 */
public class MonteCarloRatioPlayer extends AbstractMonteCarloPlayer {
    private double q;
    private Map<Integer, Double> winning;
    private Map<Integer, Double> losing;
    private Map<Integer, Double> other;
   
    public MonteCarloRatioPlayer () {
        winning = new HashMap<Integer, Double>();
        losing = new HashMap<Integer, Double>();
        other = new HashMap<Integer, Double>();
    }
   
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
    protected void initializeScore (int i) {
        winning.put(i, 0.0);
        losing.put(i, 0.0);
        other.put(i, 0.0);
    }

    @Override
    protected void addScore(GameState gs, int i) {
        if (gs.winner == number) {
            winning.put(i, winning.get(i)+q);
        }
        else if (gs.winner > 0) {
            losing.put(i, losing.get(i)+q);
        }
        else {
            other.put(i, other.get(i)+q);
        }
    }

    @Override
    protected double calculateScore(int i) {
        return (winning.get(i)-losing.get(i))/(winning.get(i)+losing.get(i)+other.get(i));
    }
}
