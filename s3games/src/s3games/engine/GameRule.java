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
    public Expr from;
    public Expr to;
    public Expr condition;
    public ArrayList<Expr> scorePlayer;
    public ArrayList<Expr> scoreAmount;
    public ArrayList<String> actions;

    public GameRule(String name)
    {
        this.name = name;
        scorePlayer = new ArrayList<Expr>();
        scoreAmount = new ArrayList<Expr>();
        actions = new ArrayList<String>();
    }

    public boolean matches(ExtendedGameState st, GameSpecification specs, Context context)
    {
        for(Element elem:specs.elements.values())        
            if (element.matches(elem.name, context))
                if (state.matches(st.basicGameState.elementStates.get(elem.name.fullName), context))
                    if (from.matches(st.basicGameState.elementLocations.get(elem.name.fullName), context))                    
                        for (Location toLoc:specs.locations.values())                        
                            if (to.matches(toLoc.name, context))
                                return true;
        return false;
    }
}
