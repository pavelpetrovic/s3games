package s3games.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Represents an Image and a relative-displacement of where it should be displayed in the board canvas */
public class ImageWithHotSpot
{
    /** the actual image */
    Image image;
    /** relative displacement of the image location */
    Point hotSpot;

    /** construct the object by loading the image from file and setting the relative displacement */
    public ImageWithHotSpot(String fileName, int x, int y) throws IOException
    {
        image = ImageIO.read(new File(fileName));
        this.hotSpot = new Point(x,y);
    }
}
