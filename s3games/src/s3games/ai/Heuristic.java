/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.GameSpecification;
import s3games.engine.GameState;

class ZeroHeuristic extends Heuristic
{
        @Override
        public double heuristic(GameState gameState, int forPlayer) 
        {
            return 0;
        }    
}
/**
 *
 * @author Zuzka
 */
public abstract class Heuristic 
{
    
    public abstract double heuristic(GameState gameState, int forPlayer);
    
            
    public static String[] availableHeuristics(String gameName) 
    {
         return new String[] {"Zero", "MoreStones", "DistanceFromGoal","Puzzle8Heuristic"};
    }
    
    public static Heuristic getHeuristic(String name, GameSpecification specs)
    {
        if (name.equals("Zero"))
            return new ZeroHeuristic();
        if (name.equals("MoreStones"))
            return new MoreStonesHeuristic(specs);
        if (name.equals("DistanceFromGoal"))
            return new DistanceFromGoalHeuristic();
        if (name.equals("Puzzle8Heuristic"))
            return new Puzzle8Heuristic();
        return null;
    }
    
}
