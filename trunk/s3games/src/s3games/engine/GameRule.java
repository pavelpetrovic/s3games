/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.ArrayList;
import s3games.engine.expr.Context;
import s3games.engine.expr.Expr;

/**
 *
 * @author petrovic16
 */
public class GameRule
{
    public String name;
    public Expr element;
    public Expr state;
    public Expr currentPlayer;
    public Expr from;
    public Expr to;
    public Expr condition;
    public ArrayList<Expr> scorePlayer;
    public ArrayList<Expr> scoreAmount;
    public Expr action;

    public GameRule(String name)
    {
        this.name = name;
        scorePlayer = new ArrayList<Expr>();
        scoreAmount = new ArrayList<Expr>();        
        condition = Expr.booleanExpr(true);
        
        moves = new ArrayList<Move>();
    }

    public boolean matches(Move move, ExtendedGameState st, Context context) throws Exception
    {        
        if (element.matches(move.element, context))
            if ((state == null) || (state.matches(st.basicGameState.elementStates.get(move.element), context)))
                if ((currentPlayer == null) || (currentPlayer.matches(st.basicGameState.currentPlayer, context)))
                    if (from.matches(move.from, context))
                        if (to.matches(move.to, context))                    
                            return condition.eval(context).isTrue();                    
        return false;
    }
    
    private ArrayList<Move> moves;
    /** list of moves that can be performed now with the element specified in the first argument
     * @return the list or null, if no such moves exist */
    public ArrayList<Move> getMatchingMoves(Element el, ExtendedGameState st, GameSpecification specs, Context context) throws Exception
    {
        moves.clear();
        if (element.matches(el.name.fullName, context))
            if ((state == null) || (state.matches(st.basicGameState.elementStates.get(el.name.fullName), context)))
                if ((currentPlayer == null) || (currentPlayer.matches(st.basicGameState.currentPlayer, context)))
                {
                    String tryFrom = st.basicGameState.elementLocations.get(el.name.fullName);
                    if (from.matches(tryFrom, context))
                        for (Location tryTo:specs.locations.values())
                            if (tryTo.content == null)
                                if (to.matches(tryTo.name.fullName, context))
                                    if (condition.eval(context).isTrue())
                                        moves.add(new Move(tryFrom, tryTo.name.fullName, el.name.fullName));
                }
        if (moves.size() > 0) return moves;
        return null;
    }
    
    public void addScores(Context context, ExtendedGameState state) throws Exception
    {
        for(int i = 0; i < scorePlayer.size(); i++)
        {
            int player = scorePlayer.get(i).eval(context).getInt();
            int amount = scoreAmount.get(i).eval(context).getInt();
            state.playerScores[player-1] += amount;
        }
    }
    
    public void performAction(Context context) throws Exception
    {
        action.eval(context);
    }
}
