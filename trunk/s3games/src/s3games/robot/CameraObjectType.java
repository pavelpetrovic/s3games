/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.robot;

import java.io.PrintWriter;

/**
 *
 * @author petrovic
*/

// e.g. 82.0 167.0 0.3 1.0 76.0 230.0 200 5000 1 green

public class CameraObjectType 
{
    public double hueMin, hueMax;
    public double satMin, satMax;
    public double valueMin, valueMax;
    public int sizeMin, sizeMax;	
    public String objectTypeName;
    public int state;
    
    public CameraObjectType(String name)
    {
        objectTypeName = name;
        state = 1;
    }
    
    public void printTo(PrintWriter out)
    {
        out.println(objectTypeName);
        out.printf("%f %f %f %f %f %f %d %d %d", 
                hueMin, hueMax, satMin, satMax, valueMin, valueMax,
                sizeMin, sizeMax, state);
        out.println();
    }
    
}
      
