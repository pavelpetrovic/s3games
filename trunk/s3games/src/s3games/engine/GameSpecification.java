/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.gui.ImageWithHotSpot;
import java.util.Dictionary;

/**
 *
 * @author petrovic16
 */
public class GameSpecification
{
    String boardBackgroundFileName;

    Dictionary<String,ImageWithHotSpot> locationTypes;
    Dictionary<String,ElementType> elementTypes;
    Dictionary<String,Element> elements;
    Dictionary<String,Location> locations;

    String playerNames[];
    
}
