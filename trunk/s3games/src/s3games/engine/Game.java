/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.io.GameLogger;
import java.util.*;
import s3games.player.Player;
import s3games.gui.GameWindow;
import s3games.io.Config;

/**
 *
 * @author petrovic16
 */
public class Game
{
    ExtendedGameState state;
    Dictionary<String,Player> players;

    GameSpecification gameSpecification;
    GameWindow window;
    
    Config config;
    GameLogger logger;

    public Game(Config config, GameLogger logger)
    {
        this.config = config;
        this.logger = logger;
    }

    public int play(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, String[] playerStrategies)
    {
        gameSpecification = new GameSpecification(config, logger);
        gameSpecification.load(gameName);

        return 0;
    }
}
