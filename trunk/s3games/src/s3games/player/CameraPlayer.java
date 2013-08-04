/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;


import java.util.*;
import s3games.engine.*;
import s3games.robot.Camera;

/**
 *
 * @author petrovic
 */


public class CameraPlayer extends Player
{
    GameSpecification specs;
    Camera camera;
    
    public CameraPlayer(GameSpecification specs, Camera camera)
    {        
        this.specs = specs;
        this.camera = camera;        
    }
    
    @Override
    public boolean isComputer()
    {
        return false;
    }
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves)
    {       
        Move move;
        do {
            ArrayList<Camera.DetectedObject> objs = camera.waitForUserMove();        
            move = determineUserMove(state, objs);
        } while (move == null);
        return move;
    }
    
    private Move determineUserMove(GameState state, ArrayList<Camera.DetectedObject> objs) 
    {   
        String movedFrom = null, movedTo = null, elem;
        Camera.DetectedObject objectMovedTo = null;
        HashSet<String> formerlyOccupiedLocations = new HashSet<String>();
        for(Map.Entry<String,String> loel: state.locationElements.entrySet())
            if (!specs.locations.get(loel.getKey()).relevant) continue; 
            else if (loel.getValue() != null) 
                formerlyOccupiedLocations.add(loel.getKey());
        
        for(Camera.DetectedObject obj: objs)
        {
            Location loc = specs.findClosestCameraLocation(obj.x, obj.y);
            String expectedElement = state.locationElements.get(loc.name.fullName);
            if (expectedElement == null)
            {
                if (movedTo != null)
                {
                    camera.msgToUser("It seems you have moved more than one element at a time. You can only move exactly one stone at a time. Try again.");
                    return null;
                }    
                movedTo = loc.name.fullName;
                objectMovedTo = obj;
            }
            else 
            {
                formerlyOccupiedLocations.remove(loc.name.fullName);
                Element expElement = specs.elements.get(expectedElement);
                if ((!expElement.type.equals(obj.type)) ||
                   (state.elementStates.get(expectedElement) != obj.state))
                {
                    camera.msgToUser("At some place the element has changed. You can only move exactly one stone at a time. Try again.");
                    return null;
                }
            }
        }
        if (movedTo == null) 
        {
            camera.msgToUser("I did not find any formerly free location having a stone now. You can only move exactly one stone at a time. Try again.");        
            return null;
        }
        if (formerlyOccupiedLocations.isEmpty())
        {
            for (Location loc : specs.locations.values())
            {
                if (!loc.relevant)
                {
                    String elName = state.locationElements.get(loc.name.fullName);
                    if (elName == null) continue;
                    Element element = specs.elements.get(elName);
                    if (!element.type.equals(objectMovedTo.type)) continue;
                    if (state.elementStates.get(elName) != objectMovedTo.state) continue;
                    movedFrom = loc.name.fullName;
                }
            }
        }
        if ((formerlyOccupiedLocations.size() != 1) && (movedFrom == null))
            
        {
            camera.msgToUser("I did not find any formerly occupied location being free now. You can only move exactly one stone at a time. Try again.");        
            return null;
        }
        if (movedFrom == null)
            movedFrom = formerlyOccupiedLocations.iterator().next();
        
        elem = state.locationElements.get(movedFrom);       
        
        
        return new Move(movedFrom, movedTo, elem, specs);
    }

    @Override
    public void otherMoved(Move move, GameState newState)
    {
        
    }

}
