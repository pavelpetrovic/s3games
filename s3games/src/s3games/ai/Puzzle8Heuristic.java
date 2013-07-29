/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import java.util.Map;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.util.IndexedName;

/**
 *
 * @author Nastavnik
 */
public class Puzzle8Heuristic extends Heuristic 
{    
    private int getRow(int position)
    {
        return (position + 2) / 3;
    }
    
    private int getCol(int position)
    {
        return (position - 1) % 3 + 1;
    }
    
    @Override
    public double heuristic(GameState gameState, int forPlayer) 
    {
        int sumOfDistances = 0;
        
        for (Map.Entry<String,String> ello: gameState.elementLocations.entrySet())  
        {
            IndexedName elName = new IndexedName(ello.getKey());
            int elementIndex = elName.index[0];
            IndexedName locName = new IndexedName(ello.getValue());
            int locationIndex = locName.index[0];
            
            sumOfDistances += Math.abs(getRow(elementIndex) - getRow(locationIndex))
                            + Math.abs(getCol(elementIndex) - getCol(locationIndex));
        }
        return sumOfDistances;  
    }

    
}
