/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.io;

import java.awt.Point;
import java.io.*;
import s3games.engine.*;
import s3games.gui.Circular;
import s3games.gui.ImageWithHotSpot;
import s3games.gui.Rectangular;
import s3games.robot.RobotLocation;
import s3games.util.IndexedName;

/**
 *
 * @author petrovic16
 */
public class GameSpecificationParser
{
    Config config;
    GameLogger logger;
    GameSpecification specs;

    enum sections { BOARD, ELEMENT_TYPES, LOCATION_TYPES, LOCATIONS,
                        PLAYER_NAMES, MOVABLE_ELEMENTS, EXPRESSIONS,
                        SCORINGS, GAME_RULES };

    sections getSection(String sectionName) throws Exception
    {
        sectionName=sectionName.toUpperCase();
        if (sectionName.equals("BOARD")) return sections.BOARD;
        if (sectionName.equals("ELEMENT_TYPES")) return sections.ELEMENT_TYPES;
        if (sectionName.equals("LOCATION_TYPES")) return sections.LOCATION_TYPES;
        if (sectionName.equals("LOCATIONS")) return sections.LOCATIONS;
        if (sectionName.equals("PLAYER_NAMES")) return sections.PLAYER_NAMES;
        if (sectionName.equals("MOVABLE_ELEMENTS")) return sections.MOVABLE_ELEMENTS;
        if (sectionName.equals("EXPRESSIONS")) return sections.EXPRESSIONS;
        if (sectionName.equals("SCORINGS")) return sections.SCORINGS;
        if (sectionName.equals("GAME_RULES")) return sections.GAME_RULES;
        logger.error("unrecognized section name '" + sectionName + "'");
        throw new Exception("unrecognized section name");
    }

    public GameSpecificationParser(Config config, GameLogger logger)
    {
        this.config = config;
    }

    private void addExpressionLine(String ln)
    {

    }

    private void boardSetting(String var, String val)
    {
        if (var.equals("image")) specs.boardBackgroundFileName = val;
    }

    ElementType et;
    int state;
    String imageName;
    private void elementTypeSetting(String var, String val)
    {
        var = var.toLowerCase();
        if (var.equals("name"))
        {
            et = new ElementType(val);
            specs.elementTypes.put(val, et);
        }
        else if (var.equals("states"))
        {
            et.numStates = Integer.parseInt(val);
            et.images = new ImageWithHotSpot[et.numStates];
            state = 0;
        }
        else if (var.equals("image"))
            imageName = val;
        else if (var.equals("point"))
        {
            String[] hotspot = val.split(",");
            try {
                et.images[state] = new ImageWithHotSpot(config.imagePath + "/" + imageName, Integer.parseInt(hotspot[0]), Integer.parseInt(hotspot[1]));
            } catch (Exception e)
            {
                logger.error("could not load image from file '" + imageName + "': " + e);
            }
            state++;
        }
    }

    String locationName;
    private void locationTypeSetting(String var, String val)
    {
        var = var.toLowerCase();
        if (var.equals("name"))
            locationName = val;
        else if (var.equals("image"))
            imageName = val;
        else if (var.equals("point"))
        {
            String[] hotspot = val.split(",");

            try {
                ImageWithHotSpot img = new ImageWithHotSpot(config.imagePath + "/" + imageName, Integer.parseInt(hotspot[0].trim()), Integer.parseInt(hotspot[1].trim()));
                specs.locationTypes.put(locationName, img);
            } catch (Exception e)
            {
                logger.error("could not load image from file '" + imageName + "': " + e);
            }
        }
    }

    Location location;
    private void locationSetting(String var, String val) throws Exception
    {
        var = var.toLowerCase();
        if (var.equals("name"))
        {
            location = new Location(val);
            specs.locations.put(val, location);
        }
        else if (var.equals("type"))
            location.type = val;
        else if (var.equals("point"))
        {
            String[] coord = val.split(",");
            location.point = new Point(Integer.parseInt(coord[0].trim()), Integer.parseInt(coord[1].trim()));
        }
        else if (var.equals("shape"))
        {
            IndexedName shapeName = new IndexedName(val);
            if (shapeName.baseName.equals("circle"))
                location.shape = new Circular(shapeName.index[0]);
            else if (shapeName.baseName.equals("rectangualr"))
                location.shape = new Rectangular(shapeName.index[0], shapeName.index[1]);
        }
        else if (var.equals("angles"))
            try { location.robot = new RobotLocation(val); }
            catch (Exception e)
            {
                logger.error(e.toString());
                throw e;
            }
    }

    private void playerNameSetting(String var, String val)
    {
        int n = Integer.parseInt(var) - 1;
        int max = Math.max(n, specs.playerNames.length + 1);
        String[] newNames = new String[max];
        for (int i = 0; i < specs.playerNames.length; i++)
            newNames[i] = specs.playerNames[i];
        newNames[n] = val;
        specs.playerNames = newNames;
    }

    private void movableElementSetting(String var, String val)
    {
        
    }

    private void scoringsSetting(String var, String val)
    {

    }

    private void rulesSetting(String var, String val)
    {

    }

    private void storeSetting(sections section, String var, String val) throws Exception
    {
        switch (section)
        {
            case BOARD: boardSetting(var, val); break;
            case ELEMENT_TYPES: elementTypeSetting(var, val); break;
            case LOCATION_TYPES: locationTypeSetting(var, val); break;
            case LOCATIONS: locationSetting(var, val); break;
            case PLAYER_NAMES: playerNameSetting(var, val); break;
            case MOVABLE_ELEMENTS: movableElementSetting(var, val); break;
            case SCORINGS: scoringsSetting(var, val); break;
            case GAME_RULES: rulesSetting(var, val); break;
        }
    }

    public boolean load(String gameName, GameSpecification specs)
    {
        this.specs = specs;
        specs.playerNames = new String[0];
        String fileName = config.gamesFolder + "/" + gameName;
        try {
            BufferedReader r = new BufferedReader(new FileReader (fileName));
            sections section = sections.BOARD;

            while (r.ready())
            {
                String ln = r.readLine().trim();
                if (ln.length() < 2) continue;
                if ((ln.charAt(0) == '/') && (ln.charAt(1)=='/')) continue;
                if (ln.charAt(0) == '[')
                {
                    section = getSection(ln.substring(1, ln.length() - 1));
                }
                else if (section == sections.EXPRESSIONS)
                {
                    addExpressionLine(ln);
                }
                else
                {
                    String[] setting = ln.split("=", 2);
                    if (setting.length != 2)
                        logger.error("urecognized line in game specification: '" + ln + "'");
                    storeSetting(section, setting[0], setting[1]);
                }
            }
            r.close();

        } catch (Exception e)
        {
            logger.error("could not load game specification from file " + fileName);
            return false;
        }
        return true;
    }
}
