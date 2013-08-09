package s3games.ai;

import java.util.Map;
import s3games.engine.GameState;
import s3games.util.IndexedName;

/** A heuristic class for the puzzle8 game. It calculates the sum of the 
 * Manhattan distances for all elements from their target locations. 
 * Use it with A* algorithm. */
public class Puzzle8Heuristic extends Heuristic 
{    
    /** calculate the row number from the element index */
    private int getRow(int position)
    {
        return (position + 2) / 3;
    }
    
    /** calculate the column number from the element index */
    private int getCol(int position)
    {
        return (position - 1) % 3 + 1;
    }
    
    /** return a sum of distances to target locations of all elements */
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
