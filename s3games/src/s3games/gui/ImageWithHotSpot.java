/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author petrovic16
 */
public class ImageWithHotSpot
{
    Image image;
    Point hotSpot;

    public ImageWithHotSpot(String fileName, int x, int y) throws IOException
    {
        image = ImageIO.read(new File(fileName));
        this.hotSpot = new Point(x,y);
    }
}
