/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games;

import java.util.*;
import s3games.ai.Strategy;
import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.gui.ControllerWindow;
import s3games.gui.GameWindow;
import s3games.io.*;
import s3games.player.CameraPlayer;
import s3games.player.MousePlayer;
import s3games.player.Player;
import s3games.robot.Camera;
import s3games.util.Switch;
import s3games.util.SwitchListener;

/**
 *
 * @author petrovic16
 */
public class Controller implements SwitchListener
{
    ControllerWindow cw;
    GameWindow gw;
    GameLogger logger;
    Config config;
    Game game;
    Switch gameRunning;
    Camera camera;
    
    public Controller()
    {
        gameRunning = new Switch();
        gameRunning.addSwitchListener(this);
        
        gw =  new GameWindow();
        cw  = new ControllerWindow(this);
        cw.setVisible(true);
        logger = new GameLogger();
        config = new Config(logger);
        config.load();
    }
    
    @Override
    public void switchChanged(boolean newState)
    {
        if (newState == false)
        {
            cw.gameFinished(game.state.winner, game.state.playerScores);
            if (camera != null)
            {
                camera.close();
                camera = null;
            }
        }
    }

    public String[] getGameNames()
    {
        return new String[] {"Alquerque","Mill","Nim", "Frogs", "TicTacToe"};
    }
    
    //todo
    public String[] getStrategiesForGame(String gameName)
    {
        return Strategy.availableStrategies(gameName);
        //return new String[] { "Random", "Intelligent123" };
    }

    //todo
    public String[] getLearnableStrategyTypesForGame(String gameName)
    {
        ArrayList<String> learnable = new ArrayList<String>();
        for(String s:getStrategiesForGame(gameName))
            if (Strategy.learnable(s))
                learnable.add(s);
        
        String[] result = new String[learnable.size()];
        return learnable.toArray(result);
    }

    //todo
    public int getNumberOfPlayersForGame(String gameName)
    {
        return 2;
    }

    /** starts a single game
     *
     * @param gameName name of the game to play
     * @param boardType real or simulated
     * @param playerTypes for each player whether it is a human or a computer
     * @param playerStrategies only for computer players, name of strategy to use
     */
    public void play(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, String[] playerStrategies) 
    {   
        if (gameRunning.isOn()) return;
        gameRunning.on();
        
        System.out.println(gameName);
        System.out.println(boardType);
        for (int i=0; i<playerTypes.length ; i++) {
           System.out.print(playerTypes[i]+" ");
           System.out.println(playerStrategies[i]);
         }
        gw.setVisible(true);
        game = new Game(config, logger, gw, gameRunning);
        
        GameSpecification gameSpecification = new GameSpecification(config, logger);
        try {
            gameSpecification.load(gameName);
        } catch (Exception e) { gw.showException(e); }
        
        if (boardType == Player.boardType.REALWORLD)
            camera = new Camera(gameSpecification);
        
        ArrayList<Player> players = new ArrayList<Player>();                
        for(int player = 0; player < gameSpecification.playerNames.length; player++)
        {
            Player p;
            if (playerTypes[player] == Player.playerType.HUMAN)
            {
                if (boardType == Player.boardType.REALWORLD)
                    p = new CameraPlayer(gameSpecification, camera);
                else p = new MousePlayer(gameSpecification, gw);
            }
            else p = Strategy.getStrategy(playerStrategies[player]).getPlayer(gameSpecification);
            p.setPlayerNumber(player+1);
            // TODO setup from controller window
            p.setMaximumNumberOfNodes(50000);
            players.add(p);
        }
        
        Player[] pls = new Player[players.size()];
        game.setGameAndPlayers(gameSpecification, players.toArray(pls));
        game.start();        
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
        System.out.println(gameName);
        System.out.println(boardType);
        for (int i=0; i<playerTypes.length ; i++) {
           System.out.print(playerTypes[i]+" ");
           System.out.println(playerStrategies[i]);
         }
         System.out.println(learnStrategyType+" "+strategyFileName+" "+numberOfRuns);
        gw.setVisible(true);
        return 0;
    }    
}
