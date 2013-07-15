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
    
    private Switch gameRuns;

    public Game(Config config, GameLogger logger, GameWindow window, Switch gameRuns)
    {
        this.config = config;
        this.logger = logger;
        this.window = window;
        this.gameRuns = gameRuns;
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
        
        state = new GameState(gameSpecification);
        int numberOfPlayers = gameSpecification.playerNames.length;
        window.setGame(this);
        
        do 
        {
            window.setState(state);
            Player playerOnMove = players[state.currentPlayer - 1];        
            ArrayList<Move> allowedMoves = state.possibleMoves();
            Move nextMove;
            
            //dbg
            System.out.println("Possible moves:");
            System.out.println(allowedMoves);
            
            if (allowedMoves.isEmpty())
            {
                state.winner = 0;
                break;
            }
            
            nextMove = playerOnMove.move(state, allowedMoves);
            boolean approved = state.moveAllowed(nextMove);
            if (!approved) 
            {
                state.winner = (state.currentPlayer % numberOfPlayers) + 1;
                break;
            }
    
            state.performMove(nextMove);   
            
            for (int p = 0; p < numberOfPlayers; p++)
                if (playerOnMove != players[p])
                    players[p].otherMoved(nextMove, state);            
            
        } while (state.winner == -1);
        
        window.setState(state);
        
        } catch (Exception e)
        {            
            window.showException(e);
        }
        finally { gameRuns.off(); }
    }
}
