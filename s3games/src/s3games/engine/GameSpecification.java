/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.gui.ImageWithHotSpot;
import java.util.*;
import java.util.Map;
import s3games.io.Config;
import s3games.io.GameLogger;
import s3games.io.GameSpecificationParser;

/**
 *
 * @author petrovic16
 */
public class GameSpecification
{
    public String gameName;
    public String boardBackgroundFileName;

    public Map<String,ImageWithHotSpot> locationTypes;
    public Map<String,ElementType> elementTypes;
    public Map<String,Element> elements;
    public Map<String,Location> locations;

    public Map<String,Expression> expressions;
    public Map<String,String> terminationConditions;
    public Map<String,String> scorings;
    public Map<String,GameRule> rules;

    public String playerNames[];

    Config config;
    GameLogger logger;

    public GameSpecification(Config config, GameLogger logger)
    {
        locationTypes = new HashMap<String, ImageWithHotSpot>();
        elementTypes = new HashMap<String, ElementType>();
        elements = new HashMap<String, Element>();
        locations = new HashMap<String, Location>();
        this.config = config;
    }

    public boolean load(String gameName)
    {        
        GameSpecificationParser parser = new GameSpecificationParser(config, logger);
        return parser.load(gameName, this);
    }
}
