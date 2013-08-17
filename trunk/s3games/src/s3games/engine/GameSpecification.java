package s3games.engine;

import java.util.*;
import java.util.Map;
import s3games.io.Config;
import s3games.io.GameLogger;
import s3games.io.GameSpecificationParser;
import s3games.engine.expr.Expr;
import s3games.engine.expr.Expression;
import s3games.robot.CameraObjectType;

/** The GameSpecification contains all information loaded from the game 
 * specification file. It is all the information that is needed to now 
 * how to play and visualize a specific game. */
public class GameSpecification
{
    /** name of the game */
    public String gameName;
    /** path to a file that contains an image for the game window background */
    public String boardBackgroundFileName;

    /** list of location types indexed through their name */
    public Map<String,LocationType> locationTypes;
    /** list of element types indexed through their name */
    public Map<String,ElementType> elementTypes;
    /** list of elements indexed through their name */
    public Map<String,Element> elements;
    /** list of locations indexed through their name */
    public Map<String,Location> locations;

    /** list of all expressions. expressions are user "functions" that can
     * be evaluated in current context to get any value. they are used in
     * many different places in game rules, and can have names. This list 
     * contains named expressions and is indexed through the names of 
     * the expressions */
    public Map<String,Expression> expressions;
    
    /** list of conditions expressions that determine the game is over, if 
     * they evaluate to true. the map contains conditions as keys, and 
     * player numbers that win the game in that case as values */     
    public Map<Expr,Expr> terminationConditions;
    
    /** specifies how the players scores change if particular game states are reached */
    public ArrayList<GameScoring> scorings;
    
    /** lists all the game rules, indexed by their name */
    public Map<String,GameRule> rules;
    
    /** lists specifications of objects to be detected by the camera when playing on real-world game board */
    public ArrayList<CameraObjectType> cameraObjectTypes;

    /** names of players - just for visualization */
    public String playerNames[];
    
    /** initial scores for all players */
    public int initialPlayerScore;

    /** private reference to configuration */
    private Config config;
    
    /** private reference to logger */
    private GameLogger logger;

    /** construct a new empty game specification */
    public GameSpecification(Config config, GameLogger logger)
    {
        locationTypes = new HashMap<String, LocationType>();
        elementTypes = new HashMap<String, ElementType>();
        elements = new HashMap<String, Element>();
        locations = new HashMap<String, Location>();
        expressions = new HashMap<String, Expression>();
        terminationConditions = new HashMap<Expr,Expr>();
        scorings = new ArrayList<GameScoring>();
        rules = new HashMap<String, GameRule>();
        cameraObjectTypes = new ArrayList<CameraObjectType>();
        this.config = config;
        initialPlayerScore = 0;
    }

    /** load game specification from a file
     * @param gameName the name of game, and the file where the specification is loaded from (in games/ folder)
     * @return true, if the game specification was successfully parsed and loaded */
    public boolean load(String gameName) throws Exception
    {        
        GameSpecificationParser parser = new GameSpecificationParser(config, logger);
        return parser.load(gameName, this);
    }
    
    /** find a camera location which is closest to a specified pixel
     * @param x x-coordinate of the pixel
     * @param y y-coordinate of the pixel
     * @return a location object that is closest to the specified pixel of the camera image */
    public Location findClosestCameraLocation(int x, int y)
    {
        double minDist = Double.POSITIVE_INFINITY;
        Location minLoc = null;
        for(Location loc: locations.values())
        {
            if (loc.camera == null) continue;
            
            double d1 = loc.camera.distanceSq(x, y);
            if (d1 < minDist)
            {
                minDist = d1;
                minLoc = loc;
            }
        }
        return minLoc;
    }
}
