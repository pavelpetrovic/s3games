/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.*;
import s3games.engine.expr.Context;
import s3games.gui.GameWindow;
import s3games.io.Config;
import s3games.io.GameLogger;
import s3games.player.CameraPlayer;
import s3games.player.MousePlayer;
import s3games.player.Player;

/**
 *
 * @author petrovic16
 */
public class Game
{
    public ExtendedGameState state;
    
    public GameSpecification gameSpecification;
    public GameWindow window;
    
    public Config config;
    public GameLogger logger;

    public Context context;

    public Game(Config config, GameLogger logger, GameWindow window)
    {
        this.config = config;
        this.logger = logger;
        this.window = window;
    }

    public int play(GameSpecification gameSpecification, Player[] players)
    {        
        this.gameSpecification = gameSpecification;
        window.setGame(gameSpecification);
        context = new Context(this);
        state = new ExtendedGameState(gameSpecification);
        int numberOfPlayers = gameSpecification.playerNames.length;
        int whoWon;
        
        do 
        {
            window.setState(state);
            Player playerOnMove = players[state.basicGameState.currentPlayer - 1];        
            boolean retryMove;
            boolean approved;
            Move nextMove;
            do 
            {
                retryMove = false;
                nextMove = playerOnMove.move(state.basicGameState);
                approved = moveAllowed(nextMove);                
                if (!approved) retryMove = playerOnMove.retryMoveNotAllowed();
            } while (retryMove);
            
            if (!approved) 
                return (state.basicGameState.currentPlayer % numberOfPlayers) + 1;
    
            performMove(nextMove);                   
            
            for (int p = 0; p < numberOfPlayers; p++)
                players[p].otherMoved(nextMove, state.basicGameState);
            
            whoWon = gameOver();
        } while (whoWon == -1);

        return whoWon;
    }
    
    /** verifies all game over conditions
     * @return the number of player who won 1..N, or 0 if end of game with draw, or -1 if not end of game */
    public int gameOver()
    {
        return -1;
    }
    
    /** verifies all rules, returns true, if the move is allowed, or false if not,
     * does not modify the game state, does not make any followup actions, however
     * executes all conditions of tested rules with all the consequences */
    public boolean moveAllowed(Move move)
    {
        if (!state.basicGameState.elementLocations.get(move.element).equals(move.from))
            return false;
        if (gameSpecification.locations.get(move.to).content != null)
            return false;
        return true;
    }
    
    /* performs a move after it has been verified, executes all follow-up actions
     * of the rule that maximizes the score */
    private void performMove(Move move)
    {
        moveElement(move);
    }
    
    /** only updates the game state by moving element between two locations, does not test anything, does not apply any rules */
    public void moveElement(Move move)
    {
        state.basicGameState.elementLocations.put(move.element, move.to);
        gameSpecification.locations.get(move.from).content = null;
        gameSpecification.locations.get(move.to).content = gameSpecification.elements.get(move.element);        
    }
}
