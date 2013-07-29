/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.robot;

/**
 *
 * @author petrovic
 */
public class RobotCmd 
{

    public enum Command { init, version, position, home, grab, put };
    private String cmdString;

    public RobotCmd(Command type, double[] angles) 
    {
        switch (type) 
        {
            case position: buildPositionCommand(angles); break;
        }
    }

    public RobotCmd(Command type) 
    {
        switch (type) 
        {
            case version: cmdString = "VER"; break;
            case grab: cmdString = "#5 P1800 T2000"; break;
            case put: cmdString = "#5 P1300 T2000"; break;
            case init: cmdString = "#0 P1500 #1 P1500 #2 P1500 #3 P1500 #4 P1500 T9000"; break;
            case home: cmdString = "#0 P1500 #1 P1500 #2 P1500 #3 P1500 T9000"; break;
        }
    }

    public String getCommand() 
    {
        return cmdString;
    }

    private void buildPositionCommand(double[] angles) 
    {
        StringBuilder result = new StringBuilder();
        result.append("#0 P");
        result.append(angleToPulseBase(angles[0]));
        result.append("#1 P");
        result.append(angleToPulseWrist(angles[1]));
        result.append(" #2 P");
        result.append(angleToPulseElbow(angles[2]));
        result.append(" #3 P");
        result.append(angleToPulseShoulder(angles[3]));
        result.append(" #4 P");
        result.append(angleToPulseHand(angles[4]));
        result.append(" T3000");
        cmdString = result.toString();
    }
    
    private int angleToPulseBase(double deg)
    {
        int r = (int) (1500 + (10 * deg));
        if (r > 2400) 
        {
            System.out.println("Pulse too long");
            r = 2400;
        }
        if (r < 600) 
        {
            System.out.println("Pulse too long");
            r = 600;
        }
        return r;        
    }

    private int angleToPulseWrist(double deg) 
    {
        int r = (int) (1500 + (10 * deg));
        if (r > 2400) 
        {
            System.out.println("Pulse too long");
            r = 2400;
        }
        if (r < 600) 
        {
            System.out.println("Pulse too long");
            r = 600;
        }
        return r;
    }

    private int angleToPulseElbow(double deg) 
    {
        int r = (int) (1800 - (10 * deg));
        if (r > 2400) 
        {
            System.out.println("Pulse too long");
            r = 2400;
        }
        if (r < 900) 
        {
            System.out.println("Pulse too long");
            r = 900;
        }
        return r;
    }

    private int angleToPulseShoulder(double deg) 
    {
        int r = (int) (1500 + (10 * deg));
        if (r > 2400) 
        {
            System.out.println("Pulse too long");
            r = 2400;
        }
        if (r < 600) 
        {
            System.out.println("Pulse too long");
            r = 600;
        }
        return r;
    }
    
    private int angleToPulseHand(double deg)
    {
        int r = (int) (1500 + (10 * deg));
        if (r > 2400) 
        {
            System.out.println("Pulse too long");
            r = 2400;
        }
        if (r < 600) 
        {
            System.out.println("Pulse too long");
            r = 600;
        }
        return r;        
    }
}
