/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import io.GameLogger;
import java.util.*;
import player.Player;
import s3games.gui.GameWindow;

/**
 *
 * @author petrovic16
 */
public class Game
{
    ExtendedGameState state;
    Dictionary<String,Player> players;
    GameLogger gameLog;
    GameSpecification gameSpecification;
    GameWindow window;
}
