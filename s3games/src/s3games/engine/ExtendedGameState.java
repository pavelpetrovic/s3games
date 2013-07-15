/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.*;
import s3games.engine.expr.Context;
import s3games.engine.expr.Expr;

/**
 *
 * @author petrovic16
 */
public class ExtendedGameState
{
    public GameState basicGameState;
    public Map<String,Integer> elementzIndexes;
    public int[] playerScores;
    
    private Context context;
    
    public ExtendedGameState(GameSpecification specs)
    {
        elementzIndexes = new TreeMap<String, Integer>();
        for (Map.Entry<String,Element> element: specs.elements.entrySet())        
            elementzIndexes.put(element.getKey(), element.getValue().initialZindex);
        playerScores = new int[specs.playerNames.length];
        basicGameState = new GameState(specs);
        context = new Context(this, specs);
    }
    
    public ArrayList<Move> possibleMoves() throws Exception
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        context.setState(this);
        
        for (GameRule rule: context.specs.rules.values())        
            for (Element element: context.specs.elements.values())
            {
                ArrayList<Move> moreMoves = rule.getMatchingMoves(element, context.specs, context);
                if (moreMoves != null) moves.addAll(moreMoves);
            }
        return moves;
    }

    /** verifies all game over conditions
     * @return the number of player who won 1..N, or 0 if end of game with draw, or -1 if not end of game */
    private int gameOver() throws Exception
    {
        context.setState(this);
        for (Map.Entry<Expr,Expr> cond: context.specs.terminationConditions.entrySet())
            if (cond.getKey().eval(context).isTrue())
                return cond.getValue().eval(context).getInt();        
        return -1;
    }
    
    /** verifies all rules, returns true, if the move is allowed, or false if not,
     * does not modify the game state, does not make any followup actions, however
     * executes all conditions of tested rules with all the consequences */
    public boolean moveAllowed(Move move) throws Exception
    {
        if (!basicGameState.elementLocations.get(move.element).equals(move.from))
            return false;
        if (context.specs.locations.get(move.to).content != null)
            return false;
        for (GameRule rule: context.specs.rules.values())        
            if (rule.matches(move, context)) return true;        
        return false;
    }

    /* performs a move after it has been verified, executes follow-up action
     * of the rule that maximizes the score, adds the score */
    public void performMove(Move move) throws Exception
    {      
        GameRule bestRule = findBestRule(move);
        bestRule.addScores(context);
        moveElement(move, context.specs);
        bestRule.performAction(context);
        basicGameState.winner = gameOver();
    }
    
    private GameRule findBestRule(Move move) throws Exception
    {
        int maximumScoreGained = Integer.MIN_VALUE;
        GameRule bestRule = null;
        for (GameRule rule: context.specs.rules.values())        
            if (rule.matches(move, context))
            {
                if (bestRule == null) bestRule = rule;            
                for(int i = 0; i < rule.scorePlayer.size(); i++)
                    if (rule.scorePlayer.get(i).eval(context).getInt() == basicGameState.currentPlayer)
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
    
    /** only updates the game state by moving element between two locations, does not test anything, does not apply any rules */
    public void moveElement(Move move, GameSpecification specs)
    {
        basicGameState.elementLocations.put(move.element, move.to);
        specs.locations.get(move.from).content = null;
        specs.locations.get(move.to).content = specs.elements.get(move.element);
    }    
    
}
