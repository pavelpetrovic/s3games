/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.io;

import java.io.*;

/**
 *
 * @author petrovic16
 */
public class Config implements Serializable
{
    public static final String configFileName = ".s3games.cfg";

    public static final String defaultPathGameSpecification = ".";
    String lastPathGameSpecification;

    GameLogger logger;

    public Config(GameLogger logger)
    {
        this.logger = logger;
    }

    public void save()
    {
        try {
            ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream(configFileName));
            f.writeObject(this);
            f.close();
        } catch (Exception e)
        {
            logger.warning("could not save config file to " + configFileName);
        }
    }

    public void load() 
    {
        try {
            ObjectInputStream f = new ObjectInputStream(new FileInputStream(configFileName));
            Config cfg = (Config)f.readObject();
            f.close();
            lastPathGameSpecification = cfg.lastPathGameSpecification;
        } catch (Exception e)
        {
            logger.warning("could not load config file from " + configFileName);
            lastPathGameSpecification = defaultPathGameSpecification;
            save();
        }
    }
}
