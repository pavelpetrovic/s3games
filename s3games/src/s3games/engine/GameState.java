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
public class GameState
{
    /** for each element name, the state number */
    public Map<String,Integer> elementStates; // 1..numStates
    /** for each element name, location name where it currently is placed */
    public Map<String,String> elementLocations;
    /** for each element name, the number of player */
    public Map<String,Integer> elementOwners;
    /** the player number on move 1..N */
    public int currentPlayer;    
    /** the game has finished */
    public boolean gameFinished;
    
    public GameState(GameSpecification specs)
    {
        elementStates = new TreeMap<String, Integer>();
        elementLocations = new TreeMap<String, String>();
        elementOwners = new TreeMap<String, Integer>();
        currentPlayer = 1;
        gameFinished = false;
        
        for (Map.Entry<String, Element> element: specs.elements.entrySet())
        {
            elementStates.put(element.getKey(), element.getValue().initialState);
            elementLocations.put(element.getKey(), element.getValue().initialLocation);
            specs.locations.get(element.getValue().initialLocation).content = element.getValue();
            elementOwners.put(element.getKey(), element.getValue().initialOwner);
        }        
    }
}
