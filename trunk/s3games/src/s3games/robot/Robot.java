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
    
    public Robot(String serialPort, GameSpecification specs) throws Exception
    {
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
    }
    
    public void close()
    {
        link.close();        
    }
    
    public void moveRobot(Move move)
    {
        requestedMove = move;
        win.addMessage("A new move " + move + " has been requested, click [Perform move] to perform it.");        
    }
 
    public void performMove() throws Exception
    {
        goTo(requestedMove.from);
        grab();
        goTo(requestedMove.to);
        put();
        goHome();
    }
    
    private void goTo(String locationName) throws Exception
    {
        double[] angles = specs.locations.get(locationName).robot.angles;
        double[] place1 = Arrays.copyOfRange(angles, 0, 5);
        double[] place2 = Arrays.copyOfRange(angles, 5, 10);
        goTo(place1);
        Thread.sleep(700);
        goTo(place2);        
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
    
    public void goTo(double[] angles) throws Exception
    {
       link.print(new RobotCmd(RobotCmd.Command.position, angles).getCommand());
    }

    public void grab() throws Exception
    {
       link.print(new RobotCmd(RobotCmd.Command.grab).getCommand());
    }
   
    public void put() throws Exception
    {
       link.print(new RobotCmd(RobotCmd.Command.put).getCommand());        
    } 
   
    public void initArm() throws Exception
    {
        link.print(new RobotCmd(RobotCmd.Command.init).getCommand());
    }
     
    public void goHome() throws Exception
    {
        link.print(new RobotCmd(RobotCmd.Command.home).getCommand());
    }
}