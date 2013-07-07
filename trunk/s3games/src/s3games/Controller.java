/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games;

import s3games.gui.ControllerWindow;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import s3games.io.*;
import s3games.player.Player;

/**
 *
 * @author petrovic16
 */
public class Controller   
{
    ControllerWindow cw;
    GameLogger logger;
    Config config;

    
    public Controller() {
        //GameWindow form =  new GameWindow();
        //form.setVisible(true);
        cw  = new ControllerWindow(this);
        cw.setVisible(true);
        logger = new GameLogger();
        config = new Config(logger);
        config.load();
    }

    //todo
    public String[] getStrategiesForGame(String gameName)
    {
        return new String[] { "Random", "Intelligent123" };
    }

    //todo
    public String[] getLearnableStrategyTypesForGame(String gameName)
    {
        return new String[] { "NN 7", "RL 5" };
    }

    //todo
    public int getNumberOfPlayersForGame(String gameName)
    {
        return 1;
    }

    /** starts a single game
     *
     * @param gameName name of the game to play
     * @param boardType real or simulated
     * @param playerTypes for each player whether it is a human or a computer
     * @param playerStrategies only for computer players, name of strategy to use
     * @return the number of player who won, 0 for draw/nobody, -1 if game was interrupted
     */
    public int play(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, String[] playerStrategies)
    {
        return 0;
    }

        /** starts a single game
     *
     * @param gameName name of the game to play
     * @param boardType real or simulated
     * @param playerTypes for each player whether it is a human or a computer
     * @param playerStrategies only for computer players, name of strategy to use
     * @param learnStrategy strategy that will be learned
     * @return the number of player who won, 0 for draw/nobody, -1 if game was interrupted
     */
    public int learn(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, String[] playerStrategies, String learnStrategyType, String strategyFileName, int numberOfRuns)
    {
        return 0;
    }

    
    public boolean readFile(BufferedReader r)
    {  //or can return a number of players after parsing...
        try {
           //todo - use parser 
           Scanner in=new Scanner(r);
           while (in.hasNext()) {
                System.out.println(in.next());
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cw, "bad file format");
            return false;
        }
       return true;
    }
    
}
