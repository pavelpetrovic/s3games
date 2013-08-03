/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author Nastavnik
 */
public class MonteCarloClassicPlayer extends AbstractMonteCarloPlayer {
    
    private Map<Integer, Integer> win;
    private Map<Integer, Integer> other;
    
    public MonteCarloClassicPlayer() {
        win = new HashMap<Integer, Integer>();
        other = new HashMap<Integer, Integer>();
    }
    
    @Override
    protected void initializeRatio() {
    }
    
    @Override
    protected void addScore(GameState gs, int i) {
        if (gs.winner == number) win.put(i, win.get(i)+1);
        else other.put(i, other.get(i)+1);
    }
    
    @Override
    protected double calculateScore(int i) {
        return (double)win.get(i)/(double)(other.get(i) + win.get(i));
    }

    @Override
    protected void initializeScore (int i) {
        win.put(i, 0);
        other.put(i, 0);
    }
        
    @Override
    protected void updateRatio(GameState gs, Set<Move> moves) {
    }    
}
