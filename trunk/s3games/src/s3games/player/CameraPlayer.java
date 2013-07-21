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
import s3games.gui.CameraPlayerWindow;

/**
 *
 * @author petrovic
 */
public class CameraPlayer extends Player implements Runnable
{
    GameSpecification specs;
    CameraPlayerWindow win;
    Thread commThread;

    private static final String CAMERA_PROGRAM = "cameraBoard/cameraBoard/OpenCV Release/cameraBoard.exe";
    
    public CameraPlayer(GameSpecification specs)
    {
        this.specs = specs;
        win = new CameraPlayerWindow(this);
        commThread = new Thread(this);
    }
    
    @Override
    public void run()
    {
        try {
            Process p = Runtime.getRuntime().exec(CAMERA_PROGRAM);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            PrintWriter out = new PrintWriter(p.getOutputStream());
            if (!in.readLine().equals("S:S3 Games Camera"))
                throw new Exception("Camera program did not respond");
        } catch (Exception e)
        {
            win.addMessage(e.toString());
        }

    }
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves)
    {
        return allowedMoves.get(0);
    }

    @Override
    public void otherMoved(Move move, GameState newState)
    {
        
    }

}
