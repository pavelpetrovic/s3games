/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.*;
import s3games.gui.GameWindow;
import s3games.io.Config;
import s3games.io.GameLogger;
import s3games.player.Player;
import s3games.robot.Robot;
import s3games.util.Switch;

/**
 *
 * @author petrovic16
 */
public class Game extends Thread
{
    public GameState state;
    
    public GameSpecification gameSpecification;
    public GameWindow window;
    
    public Config config;
    public GameLogger logger;
    
    public Player[] players;
    
    private Robot robot;
    
    private Switch gameRuns;

    public Game(Config config, GameLogger logger, GameWindow window, Switch gameRuns, Robot robot)
    {
        this.config = config;
        this.logger = logger;
        this.window = window;
        this.gameRuns = gameRuns;
        this.robot = robot;
    }

    public void setGameAndPlayers(GameSpecification gameSpecification, Player[] players)
    {        
        this.gameSpecification = gameSpecification;
        this.players = players;
    }
    
    @Override
    public void run()  
    {
        try { 
        
        state = new GameState(gameSpecification, robot);
        int numberOfPlayers = gameSpecification.playerNames.length;
        window.setGame(this, gameRuns);
        
        do 
        {
            window.setState(state);
            Player playerOnMove = players[state.currentPlayer - 1];        
            ArrayList<Move> allowedMoves = state.allPossibleMoves();
            Move nextMove;
            
            //dbg
            System.out.println("Possible moves:");
            System.out.println(allowedMoves);
            
            if (allowedMoves.isEmpty())
            {
                state.winner = 0;
                state.touch();
                break;
            }
            
            nextMove = playerOnMove.move(state, allowedMoves);
            if (gameRuns.isOff()) 
            {
                state.winner = 0;
                break;
            }
            System.out.println("Player moves: " + nextMove.toString());
            boolean approved = state.moveAllowed(nextMove);
            if (!approved)
            {
                window.showException(new Exception("Player performed illegal move " + nextMove));
                state.winner = (state.currentPlayer % numberOfPlayers) + 1;
                state.touch();
                break;
            }
    
            if (robot != null)
                if (playerOnMove.isComputer())
                    robot.moveRobot(nextMove);
            
            state.performMove(nextMove);
            
            for (int p = 0; p < numberOfPlayers; p++)
                if (playerOnMove != players[p])
                    players[p].otherMoved(nextMove, state);            
            
        } while (state.winner == -1);
        
        for (int p = 0; p < numberOfPlayers; p++)
            players[p].gameOver();
        
        window.setState(state);
        
        } catch (Exception e)
        {            
            e.printStackTrace();
            window.showException(e);
        }
        finally { gameRuns.off(); }
    }
}
