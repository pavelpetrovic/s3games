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
    public Map<String,Integer> elementStates;
    /** for each element name, location name where it currently is placed */
    public Map<String,String> elementLocations;
    /** for each lement name, the number of player */
    public Map<String,Integer> elementOwners;
    /** the player number on move 1..N */
    public int currentPlayer;
}
