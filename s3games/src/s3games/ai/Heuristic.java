package s3games.ai;

import s3games.engine.GameSpecification;
import s3games.engine.GameState;

/** A heuristic always returns a number close to zero, suitable for MINIMAX, not suitable for A* */
class ZeroHeuristic extends Heuristic
{
        @Override
        public double heuristic(GameState gameState, int forPlayer) 
        {
            return 0;            
        }    
}

/** An abstract heuristic class, also contains a constructor method given the heuristic name */
public abstract class Heuristic 
{
    /** a heuristic receives the current state and player number - from whose viewpoint the game situation is to be evaluated. 
     * A heuristic should return a number that estimates how good the sitation is for the player: in A* this is a lower bound 
     * of distance to a goal state, for A* it is a value between -1 (worst=always losing state) and +1 (good=always winning state)
     * @param gameState current game state
     * @param forPlayer current player
     * @return estimated value of the current game state */
    public abstract double heuristic(GameState gameState, int forPlayer);
    
    /** lists all the heuristics available in the system */
    public static String[] availableHeuristics(String gameName) 
    {
         return new String[] {"Zero", "MoreStones", "DistanceFromGoal","Puzzle8Heuristic"};
    }
    
    /** constructor method that creates a heuristic of the specified type
     * @param name heuristic name
     * @param specs game specification that is passed to the heuristic constructor if it is needed
     * @return a new instance of the specified heuristic */
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
