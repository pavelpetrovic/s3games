/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3games.ai.Heuristic;
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
import s3games.robot.Robot;
import s3games.util.Switch;
import s3games.util.SwitchListener;

/**
 *
 * @author petrovic16
 */
public class Controller extends Thread implements SwitchListener
{
    ControllerWindow cw;
    GameWindow gw;
    GameLogger logger;
    Config config;
    Game game;
    Switch gameRunning;
    Camera camera;
    Robot robot;
    final Object notifier;
    
    public Controller()
    {
        notifier = new Object();
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
            synchronized(notifier)
            {
                notifier.notify();
            }            
        }
    }

    public String[] getGameNames()
    {
        return new String[] {"Connect4","Puzzle8","Reversi","RiverCrossing","Alquerque","Mill","Nim", "Frogs", "TicTacToe"};
    }
    
    //todo
    public String[] getStrategiesForGame(String gameName)
    {
        return Strategy.availableStrategies(gameName);
    }

    //todo
    public String[] getHeuristicsForGame(String strategyName) {
        return Heuristic.availableHeuristics(strategyName);
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
    public void play(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, 
                     String[] playerStrategies, String[] strategyHeuristics, int numberOfRuns) 
    {   
        if (gameRunning.isOn()) return;
        
        gw.setVisible(true);
        
        gameSpecification = new GameSpecification(config, logger);
        try {
            gameSpecification.load(gameName);                        
        } catch (Exception e) { gw.showException(e); }

        this.numberOfRuns = numberOfRuns;
        this.boardType = boardType;
        this.playerTypes = playerTypes;
        this.playerStrategies = playerStrategies;
        this.strategyHeuristics = strategyHeuristics;
        this.start();
    }

    int numberOfRuns;
    GameSpecification gameSpecification;
    Player.boardType boardType;    
    Player.playerType[] playerTypes;
    String[] playerStrategies;
    String[] strategyHeuristics;
    String gameName;
    
    @Override
    public void run()
    {
        FileWriter fw = null;
        try { fw = new FileWriter(cw.getStatisticFileName()); }
        catch (IOException ex) { gw.showException(ex); return; }
        
        while (numberOfRuns-- > 0)
        {
            gameRunning.on();
            game = new Game(config, logger, gw, gameRunning, robot);

            if (boardType == Player.boardType.REALWORLD)
            {
                camera = new Camera(gameSpecification);
                boolean robotNeeded = false;
                for (int player = 0; player < playerTypes.length; player++)
                    if (playerTypes[player] == Player.playerType.COMPUTER)
                        robotNeeded = true;
                try {
                    if (robotNeeded) robot = new Robot("COM3", gameSpecification);
                } catch (Exception e) { gw.showException(e); }
            }

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
                else 
                {
                    Heuristic h = Heuristic.getHeuristic(strategyHeuristics[player], gameSpecification);
                    p = Strategy.getStrategy(playerStrategies[player], h).getPlayer(gameSpecification);

                }
                p.setPlayerNumber(player+1);
                // TODO setup from controller window
                p.setMaximumNumberOfNodes(cw.getNumberOfNodesToExpand());
                p.setMaximumCacheSize(4 * cw.getNumberOfNodesToExpand());
                players.add(p);
            }

            Player[] pls = new Player[players.size()];
            game.setGameAndPlayers(gameSpecification, players.toArray(pls));               
            synchronized(notifier)
            {
                game.start();
                try { notifier.wait(); } catch (InterruptedException ex) {}
            }
            
            try { fw.append(Integer.toString(game.state.winner) + System.getProperty("line.separator")); fw.flush(); }
            catch (IOException ex) { gw.showException(ex); }
        }
        
        try { fw.close(); } catch (IOException ex) { gw.showException(ex); }
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
    public int learn(String gameName, Player.boardType boardType, Player.playerType[] playerTypes, 
                     String[] playerStrategies, String[] strategyHeuristics, String learnStrategyType, String strategyFileName, int numberOfRuns)
    {
        System.out.println(gameName);
        System.out.println(boardType);
        for (int i=0; i<playerTypes.length ; i++) {
           System.out.print(playerTypes[i]+" ");
           System.out.print(playerStrategies[i]+" ");
           System.out.println(strategyHeuristics[i]+" ");
         }
         System.out.println(learnStrategyType+" "+strategyFileName+" "+numberOfRuns);
        gw.setVisible(true);
        return 0;
    }    
}
