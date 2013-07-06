/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.gui.ImageWithHotSpot;
import java.util.*;
import java.util.Map;
import s3games.io.GameSpecificationParser;

/**
 *
 * @author petrovic16
 */
public class GameSpecification
{
    String boardBackgroundFileName;

    Map<String,ImageWithHotSpot> locationTypes;
    Map<String,ElementType> elementTypes;
    Map<String,Element> elements;
    Map<String,Location> locations;

    String playerNames[];

    public GameSpecification()
    {
        locationTypes = new HashMap<String, ImageWithHotSpot>();
        elementTypes = new HashMap<String, ElementType>();
        elements = new HashMap<String, Element>();
        locations = new HashMap<String, Location>();
    }

    public void load(String fileName)
    {        
        GameSpecificationParser parser = new GameSpecificationParser();
        parser.load(fileName, this);
    }
}
