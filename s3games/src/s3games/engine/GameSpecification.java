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
import s3games.engine.expr.Expr;
import s3games.engine.expr.Expression;

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
    public Map<Expr,Expr> terminationConditions;
    public ArrayList<GameScoring> scorings;
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
        expressions = new HashMap<String, Expression>();
        terminationConditions = new HashMap<Expr,Expr>();
        scorings = new ArrayList<GameScoring>();
        rules = new HashMap<String, GameRule>();
        this.config = config;
    }

    public boolean load(String gameName) throws Exception
    {        
        GameSpecificationParser parser = new GameSpecificationParser(config, logger);
        return parser.load(gameName, this);
    }
}
