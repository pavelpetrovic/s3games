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
        String movedFrom, movedTo = null, elem;
        
        HashSet<String> formerlyOccupiedLocations = new HashSet<String>();
        for(Map.Entry<String,String> loel: state.locationElements.entrySet())
            if (loel.getValue() != null) 
                formerlyOccupiedLocations.add(loel.getKey());
        
        for(Camera.DetectedObject obj: objs)
        {
            Location loc = specs.findClosestCameraLocation(obj.x, obj.y);
            if (state.locationElements.get(loc.name.fullName) == null)
                movedTo = loc.name.fullName;
            else formerlyOccupiedLocations.remove(loc.name.fullName);
        }
        if (movedTo == null) 
        {
            camera.msgToUser("I did not find any formerly free location having a stone now. You can only move exactly one stone at a time. Try again.");        
            return null;
        }
        
        if (formerlyOccupiedLocations.size() != 1)
        {
            camera.msgToUser("I did not find any formerly occupied location being free now. You can only move exactly one stone at a time. Try again.");        
            return null;
        }
        movedFrom = formerlyOccupiedLocations.iterator().next();
        
        elem = state.locationElements.get(movedFrom);       
        
        return new Move(movedFrom, movedTo, elem, specs);
    }

    @Override
    public void otherMoved(Move move, GameState newState)
    {
        
    }

}
