/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.*;

/**
 *
 * @author petrovic16
 */
public class ExtendedGameState
{
    public GameState basicGameState;
    public Map<String,Integer> elementzIndexes;
    public int[] playerScores;
    
    public ExtendedGameState(GameSpecification specs)
    {
        elementzIndexes = new TreeMap<String, Integer>();
        for (Map.Entry<String,Element> element: specs.elements.entrySet())        
            elementzIndexes.put(element.getKey(), element.getValue().initialZindex);
        playerScores = new int[specs.playerNames.length];
        basicGameState = new GameState(specs);
    }
}
