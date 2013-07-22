/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;


import java.util.ArrayList;
import java.io.*;

import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.gui.CameraWindow;
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
    public Move move(GameState state, ArrayList<Move> allowedMoves)
    {
        Move userMove = camera.waitForUserMove();
        return allowedMoves.get(0);
    }

    @Override
    public void otherMoved(Move move, GameState newState)
    {
        
    }

}
