package s3games.ai;

import s3games.engine.GameState;

/** a heuristic that compares the number of different elements from the target situation */
public class DistanceFromGoalHeuristic extends Heuristic
{
    /** takes a current state and compares it with a goal state element by element, and sums the number of differences - not implemented yet */
    @Override
    public double heuristic(GameState gameState, int forPlayer) 
    {
        return 0;
    }
}
