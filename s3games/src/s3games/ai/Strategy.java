/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.player.Player;

/**
 *
 * @author petrovic16
 */
public abstract class Strategy
{
    protected Heuristic heuristic;
    
    class ZeroHeuristic extends Heuristic
    {
        @Override
        public double heuristic(GameState gameState, int forPlayer) 
        {
            return 0;
        }    
    }
    
    public Strategy()
    {
        heuristic = new ZeroHeuristic();
    }
    
    public abstract Player getPlayer(GameSpecification specs);
    public abstract void learn(Game game);

    public static String[] availableStrategies(String gameName)
    {
        return new String[] {"Random","DFS","BFS","MiniMax"};
    }
    
    public static String[] heuristicsForGame(String strategyName) {
         return new String[] {"zero","heuristika1", "heuristika2"};
    }
    public static boolean learnable(String strategyName)
    {
        return true;
    }
        
    public void setHeuristic(Heuristic heuristic)
    {
        this.heuristic = heuristic;
    }
    
    public static Strategy getStrategy(String name)
    {
        if (name.equals("Random"))
            return new RandomGeneralStrategy();
        if (name.equals("DFS"))
            return new DepthFirstSearchStrategy();
        if (name.equals("BFS"))
            return new BreadthFirstSearchStrategy();
        if (name.equals("MiniMax"))
            return new MiniMaxStrategy();
        return null;
    }
}
