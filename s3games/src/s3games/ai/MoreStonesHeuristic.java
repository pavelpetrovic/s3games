package s3games.ai;

import java.util.Map;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;

/** a heuristic class that counts the number of elements of both players on the board. the more elements, the better value */
public class MoreStonesHeuristic extends Heuristic
{
    private GameSpecification specs;
    
    /** constructor needs the game specification */
    public MoreStonesHeuristic(GameSpecification specs)
    {
        this.specs = specs;
    }

    /** returns a value -1..1 depending on the ratio of the player's and opponent's elements on the relevant locations on the board */
    @Override
    public double heuristic(GameState gameState, int forPlayer) 
    {
        int myStones = 0;
        int otherStones = 0; 
        
        for (Map.Entry<String, String> loel: gameState.locationElements.entrySet())
        {
            if (loel.getValue() == null) continue;
            if (specs.locations.get(loel.getKey()).relevant)
            {
                int owner = gameState.elementOwners.get(loel.getValue());
                if (owner == forPlayer) myStones++;
                else otherStones++;
            }
        }
        if (myStones > otherStones) return myStones / (myStones + otherStones);
        else if (myStones < otherStones) return - otherStones / (myStones + otherStones);
        else return 0.0;
    }
    
}
