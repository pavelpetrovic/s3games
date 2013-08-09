package s3games.robot;

import java.io.PrintWriter;
import java.util.Locale;

/** Specifies the color (in HSV model) and size range for a particular
 * element type and state as appears in the image frame seen from the
 * camera. An example would be hue:82-167, saturation:0.3-1.0,
 * value:0.5-1.0, size:200-5000, elementType:greenFrog, elState:1 */

public class CameraObjectType 
{
    /** hue interval 0-360 */
    public double hueMin, hueMax;
    /** saturation interval 0-1 */
    public double satMin, satMax;
    /** value interval 0-1 */
    public double valueMin, valueMax;
    /** size interval - number of pixels */
    public int sizeMin, sizeMax;	
    /** name of the corresponding element type */
    public String elementType;
    /** corresponding state of the element */
    public int elState;
    
    /** construct a new empty camera object type */
    public CameraObjectType(String name)
    {
        elementType = name;
        elState = 1;
    }
    
    /** visualize the settings of this camera object type */
    public void printTo(PrintWriter out)
    {
        out.print('@');
        out.println(elementType);
        out.printf(Locale.US, "@%.4f %.4f %.4f %.4f %.4f %.4f %d %d %d", 
                hueMin, hueMax, satMin, satMax, valueMin, valueMax,
                sizeMin, sizeMax, elState);
        out.println();
    }
}
      
