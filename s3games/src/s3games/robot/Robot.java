/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.robot;

import s3games.engine.GameSpecification;
import s3games.engine.Move;
import s3games.gui.RobotWindow;
import java.io.*;
import java.util.Arrays;
import s3games.engine.Location;

/**
 *
 * @author petrovic
 */
public class Robot 
{
    private String port;
    RobotSerialPort link;
    GameSpecification specs;
    RobotWindow win;
    Move requestedMove;
    Object waitForMovePerformed;
    
    public Robot(String serialPort, GameSpecification specs) throws Exception
    {
        waitForMovePerformed = new Object();
        port = serialPort;
        this.specs = specs;
        win = new RobotWindow(this);        
        open(); 
    }
    
    public void open() throws Exception
    {
        win.addMessage("Opening robot...");
        link = new RobotSerialPort(port);
        link.open();       
        link.print(new RobotCmd(RobotCmd.Command.version).getCommand());
        StringBuilder sb = new StringBuilder();
        int ch;
        do {
            ch = link.read();
            sb.append((char)ch);
        } while (ch != 13);
        win.addMessage("Robot Firmware version: " + sb.toString());
        win.addMessage("You can move the robot away from the camera frame and click [Init] twice.");
    }
    
    public void close()
    {
        link.close();        
    }
    
    public void moveRobot(Move move) throws Exception
    {
        requestedMove = move;
        win.addMessage("A new move " + move + " has been requested, click [Perform move] to perform it.");        
        synchronized(waitForMovePerformed)
        {
            waitForMovePerformed.wait();
        }
    }
 
    public void performMove() throws Exception
    {
        goTo(requestedMove.from, true);        
        goTo(requestedMove.to, false);        
        goHome();
        synchronized(waitForMovePerformed)
        {
            waitForMovePerformed.notify();
        }
    }
    
    private boolean moveCompleted() throws Exception
    {
        link.print("Q");
        int stillMoving = link.read();
        if (stillMoving == '.') return true;
        if (stillMoving == '+') return false;
        throw new Exception("Query from robot expected + or . but " + stillMoving + " was received.");
    }
    
    private void goTo(String locationName, boolean grab) throws Exception
    {
        double[] angles = specs.locations.get(locationName).robot.angles;
        double[] place1 = Arrays.copyOfRange(angles, 0, 5);
        double[] place2 = Arrays.copyOfRange(angles, 5, 10);
        goTo(place1);
        Thread.sleep(700);
        goTo(place2);
        Thread.sleep(700);
        if (grab) grab(); else put();
        Thread.sleep(700);
        goTo(place1);
        Thread.sleep(700);
    }
    
    public void doTest() 
    {
        System.out.print("enter angles (5 doubles, init, or home, grab, put): ");
        try {
            double angles[] = new double[5];
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] ln = in.readLine().split(" ");
            if (ln.length == 0) return;
            if (ln[0].equals("init"))
            {
                System.out.println("Moving to initial position");
                initArm();
            }
            else if (ln[0].equals("home"))
            {
                System.out.println("Moving to home position");
                goHome();
            }
            else if (ln[0].equals("grab"))
            {
                System.out.println("Grabbing");
                grab();
            }
            else if (ln[0].equals("put"))
            {
                System.out.println("Putting");
                put();
            } else
            {
                if (ln.length < 5) System.out.println("Did anybody say 5 doubles?");
                else
                {
                    for (int i = 0; i < 5; i++) angles[i] = Double.parseDouble(ln[i]);
                    System.out.println("Moving the robot, watch out!");
                    Thread.sleep(1000);
                    goTo(angles);
                }
            }
        } catch (Exception e) {}
    }
    
    public void allLocationsDemo()
    {
        try {
            for (Location loc: specs.locations.values())
            {            
                goTo(loc.name.fullName, false);
                Thread.sleep(5000);
            }
        } catch (Exception e) { win.addMessage("Problem moving to location: " + e.getMessage()); }
    }
    
    public void goTo(double[] angles) throws Exception
    {
        while (!moveCompleted()) { Thread.sleep(500); }        
        link.print(new RobotCmd(RobotCmd.Command.position, angles).getCommand());
    }

    public void grab() throws Exception
    {
        while (!moveCompleted()) { Thread.sleep(500); }
        link.print(new RobotCmd(RobotCmd.Command.grab).getCommand());
    }
   
    public void put() throws Exception
    {
        while (!moveCompleted()) { Thread.sleep(500); }
        link.print(new RobotCmd(RobotCmd.Command.put).getCommand());        
    } 
   
    public void initArm() 
    {
        try {
            link.print(new RobotCmd(RobotCmd.Command.init).getCommand());
        } catch (Exception e) { win.addMessage("Could not init robot arm: " + e.getMessage()); }
    }
     
    public void goHome() throws Exception
    {
        while (!moveCompleted()) { Thread.sleep(500); }
        link.print(new RobotCmd(RobotCmd.Command.home).getCommand());
    }
}
