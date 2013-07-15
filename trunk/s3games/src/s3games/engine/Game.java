/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.*;
import s3games.engine.expr.Context;
import s3games.engine.expr.Expr;
import s3games.gui.GameWindow;
import s3games.io.Config;
import s3games.io.GameLogger;
import s3games.player.Player;

/**
 *
 * @author petrovic16
 */
public class Game extends Thread
{
    public ExtendedGameState state;
    
    public GameSpecification gameSpecification;
    public GameWindow window;
    
    public Config config;
    public GameLogger logger;

    public Context context;
    
    public Player[] players;
    private int winner;

    public Game(Config config, GameLogger logger, GameWindow window)
    {
        this.config = config;
        this.logger = logger;
        this.window = window;
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
        
        state = new ExtendedGameState(gameSpecification);
        context = new Context(this);
        int numberOfPlayers = gameSpecification.playerNames.length;
        int whoWon;
        window.setGame(gameSpecification);
        
        do 
        {
            window.setState(state);
            Player playerOnMove = players[state.basicGameState.currentPlayer - 1];        
            ArrayList<Move> allowedMoves = possibleMoves();            
            Move nextMove;
            
            //dbg
            System.out.println("Possible moves:");
            System.out.println(allowedMoves);
            
            if (allowedMoves.isEmpty())
            {
                whoWon = 0;
                break;
            }
            
            nextMove = playerOnMove.move(state.basicGameState, allowedMoves);
            boolean approved = moveAllowed(nextMove);                
            if (!approved) 
            {
                whoWon = (state.basicGameState.currentPlayer % numberOfPlayers) + 1;
                break;
            }
    
            performMove(nextMove);   
            
            for (int p = 0; p < numberOfPlayers; p++)
                if (playerOnMove != players[p])
                    players[p].otherMoved(nextMove, state.basicGameState);
            
            whoWon = gameOver();
        } while (whoWon == -1);

        winner = whoWon;
        state.basicGameState.gameFinished = true;
        window.setState(state);
        
        } catch (Exception e)
        {
            window.showException(e);
        }
    }
    
    public int whoWon()
    {
        return winner;
    }
    
    public ArrayList<Move> possibleMoves() throws Exception
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        
        for (GameRule rule: gameSpecification.rules.values())        
            for (Element element: gameSpecification.elements.values())
            {
                ArrayList<Move> moreMoves = rule.getMatchingMoves(element, state, gameSpecification, context);
                if (moreMoves != null) moves.addAll(moreMoves);
            }        
        return moves;
    }

    /** verifies all game over conditions
     * @return the number of player who won 1..N, or 0 if end of game with draw, or -1 if not end of game */
    public int gameOver() throws Exception
    {
        for (Map.Entry<Expr,Expr> cond: gameSpecification.terminationConditions.entrySet())
            if (cond.getKey().eval(context).isTrue())
                return cond.getValue().eval(context).getInt();
        return -1;
    }
    
    /** verifies all rules, returns true, if the move is allowed, or false if not,
     * does not modify the game state, does not make any followup actions, however
     * executes all conditions of tested rules with all the consequences */
    public boolean moveAllowed(Move move) throws Exception
    {
        if (!state.basicGameState.elementLocations.get(move.element).equals(move.from))
            return false;
        if (gameSpecification.locations.get(move.to).content != null)
            return false;
        for (GameRule rule: gameSpecification.rules.values())        
            if (rule.matches(move, state, context)) return true;        
        return false;
    }

    private GameRule findBestRule(Move move) throws Exception
    {
        int maximumScoreGained = Integer.MIN_VALUE;
        GameRule bestRule = null;
        for (GameRule rule: gameSpecification.rules.values())        
            if (rule.matches(move, state, context))
            {
                if (bestRule == null) bestRule = rule;            
                for(int i = 0; i < rule.scorePlayer.size(); i++)
                    if (rule.scorePlayer.get(i).eval(context).getInt() == state.basicGameState.currentPlayer)
                    {
                        int score = rule.scoreAmount.get(i).eval(context).getInt();
                        if (score > maximumScoreGained)
                        {
                            maximumScoreGained = score;
                            bestRule = rule;
                        }
                    }                  
            }
        return bestRule;
    }
    /* performs a move after it has been verified, executes follow-up action
     * of the rule that maximizes the score, adds the score */
    private void performMove(Move move) throws Exception
    {
        GameRule bestRule = findBestRule(move);
        bestRule.addScores(context, state);
        moveElement(move);
        bestRule.performAction(context);        
    }
    
    /** only updates the game state by moving element between two locations, does not test anything, does not apply any rules */
    public void moveElement(Move move)
    {
        state.basicGameState.elementLocations.put(move.element, move.to);
        gameSpecification.locations.get(move.from).content = null;
        gameSpecification.locations.get(move.to).content = gameSpecification.elements.get(move.element);        
    }
}
