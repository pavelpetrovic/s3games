/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.Dictionary;

/**
 *
 * @author petrovic16
 */
public class GameState
{
    
    Dictionary<String,Integer> elementStates;
    Dictionary<String,String> elementLocations;
    Dictionary<String,String> elementOwners;
    String currentPlayer;
}
