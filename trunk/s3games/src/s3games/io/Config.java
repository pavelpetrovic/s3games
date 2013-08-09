package s3games.io;

import java.io.*;

/** Configuration of all kinds that is serialized to a config file is stored here.
 * (not in much use currently) */
public class Config implements Serializable
{
    /** name of the config file */
    public static final String configFileName = ".s3games.cfg";

    /** the default place where the game specifications are located */
    public static final String defaultGamesFolder = "games";
    
    /** the place where the game specifications are located */
    public String gamesFolder;

    /** the default place where the images are located */
    public static final String defaultImagePath = "images";
    
    /** the place where the images are located */
    public String imagePath;

    /** reference to a logger that is not part of this config */
    transient GameLogger logger;

    /** create a new empty config */
    public Config(GameLogger logger)
    {
        this.logger = logger;
    }

    /** serialize the config to a config file */
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

    /** retrieve the config from the file */
    public void load() 
    {
        try {
            ObjectInputStream f = new ObjectInputStream(new FileInputStream(configFileName));
            Config cfg = (Config)f.readObject();
            f.close();
            gamesFolder = cfg.gamesFolder;
            imagePath = cfg.imagePath;
        } catch (Exception e)
        {
            logger.warning("could not load config file from '" + configFileName + "'");
            gamesFolder = defaultGamesFolder;
            imagePath = defaultImagePath;
            save();
        }
    }
}
