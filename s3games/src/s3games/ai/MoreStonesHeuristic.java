/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import java.util.Map;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;

/**
 *
 * @author petrovic
 */
public class MoreStonesHeuristic extends Heuristic
{
    GameSpecification specs;
    
    public MoreStonesHeuristic(GameSpecification specs)
    {
        this.specs = specs;
    }

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
