/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;
import s3games.gui.CameraWindow;

/**
 *
 * @author petrovic
 */


/**
 *
 * @author petrovic
 */
public class Camera implements Runnable
{
    public class DetectedObject
    {
        public String type;
        public int state;
        public int x;
        public int y;

        public DetectedObject(String type, int x, int y)
        {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString()
        {        
            return type + "(" + state + ") " + x + " " + y;
        }
    }
    
    GameSpecification specs;    
    CameraWindow win;
    Thread commThread;
    boolean showDebuggingMessages;
    ArrayList<DetectedObject> objects;
    BufferedReader in;
    PrintWriter out;
    boolean terminating;
    final Object notificator;

    private static final String CAMERA_PROGRAM = "cameraBoard.exe";
    
    public Camera(GameSpecification specs)
    {
        notificator = new Object();
        objects = new ArrayList<DetectedObject>();
        showDebuggingMessages = false;
        terminating = false;
        this.specs = specs;
        win = new CameraWindow(this);
        commThread = new Thread(this);
        init();        
    }
        
    private void sendObjectParametersToCamera()
    {
        ArrayList<CameraObjectType> params = specs.cameraObjectTypes;
        out.println("2");
        out.println(params.size());
        for (CameraObjectType o: params)                    
            o.printTo(out);        
        out.flush();
    }
    
    private void init()
    {
        try 
        {
            win.addMessage("Connecting to camera...");
            ProcessBuilder pb = new ProcessBuilder(CAMERA_PROGRAM);
            Process p = pb.start();
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            out = new PrintWriter(p.getOutputStream());
            int terminated = 0;
            boolean hasTerminated = true;
            try { terminated = p.exitValue(); } 
            catch (IllegalThreadStateException e) { hasTerminated = false; }
            if (hasTerminated) throw new Exception("The camera program has terminated with exit code " + terminated);
            String header = in.readLine();
            if (header == null) throw new Exception("Could not start talking with the camera program");
            if (!header.equals("S:S3 Games Camera"))
                throw new Exception("Camera program did not respond properly");
            win.addMessage("Camera there. Sending object types...");
            sendObjectParametersToCamera();
            commThread.start();
            Thread.sleep(700);
        } 
        catch (Exception e) { win.addMessage(e.getMessage()); }
        
    }
    
    @Override
    public void run()
    {
        try {
            do {
                String ln = in.readLine();
                System.out.println("cam:" + ln);
                if (ln == null) throw new Exception("Camera disconnected");
                if (ln.charAt(0) == 'F') throw new Exception("Camera program stoped with error " + ln.substring(2));
                else if (ln.charAt(0) == 'D') 
                {
                    if (showDebuggingMessages) win.addMessage(ln.substring(2));
                    else continue;
                }
                else if (ln.charAt(0) == 'I') win.addMessage(ln.substring(2));
                else if (ln.charAt(0) == 'O') addObject(ln.substring(2));
                else if (ln.charAt(0) == '=') objectsTransmitted();
            } while (!terminating);
            
            win.close();            
        } catch (Exception e)
        {
            if (!terminating) win.addMessage(e.getMessage());
            e.printStackTrace();
        }
        win = null;
    }
    
    private void addObject(String objDesc) throws Exception
    {
        String obj[] = objDesc.split("\t");
        if (obj.length != 3) throw new Exception("Camera program output error");
        objects.add(new DetectedObject(obj[0], Integer.parseInt(obj[1]), Integer.parseInt(obj[2])));
    }
    
    private void objectsTransmitted()
    {
        //win.addMessage(objects.toString());
        win.addMessage("Received " + objects.size() + " objects from camera.");
        synchronized(notificator) { notificator.notify(); }
    }
    
    public void requestObjectsFromCamera()
    {
        objects.clear();
        out.println("1");
        out.flush();
    }
    
    public ArrayList<DetectedObject> waitForUserMove() 
    {
        win.addMessage("It is your turn: move one stone and click the button below.");
        try { synchronized(notificator) {             
            notificator.wait(); 
        } } catch (Exception e) {}
        return objects;
    }    
    
    public void msgToUser(String msg)
    {
        win.addMessage(msg);
    }

    public void close()
    {
        terminating = true;
        if (out != null) 
        {
            out.println("0");
            out.flush();
        }        
        out.close();
        try { 
            Thread.sleep(2000);
            in.close(); 
        } catch (Exception e) {}
    }
}
