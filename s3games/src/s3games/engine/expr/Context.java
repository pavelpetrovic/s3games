/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine.expr;

import java.util.*;
import s3games.engine.*;

/**
 *
 * @author petrovic16
 */
public class Context
{
    Map<String, Expr> vars;
    ExtendedGameState gameState;
    public GameSpecification specs;
    
    public Context(ExtendedGameState state, GameSpecification specs)
    {
        this.gameState = state;
        this.specs = specs;
        vars = new TreeMap<String, Expr>();        
    }
    
    public void setState(ExtendedGameState state)
    {
        gameState = state;
    }
    
    public ExtendedGameState getState()
    {
        return gameState;
    }
    
    public void setVar(String var, Expr val)
    {
        vars.put(var, val);
    }

    /** the value to which the specified variable name is mapped, or null if variables do not contain var */
    public Expr getVar(String var)
    {
        return vars.get(var);
    }
    
    /** the expression to which the specified expression name is mapped, or null if expressions do not contain exprName */
    public Expression getExpr(String exprName)
    {
        return specs.expressions.get(exprName);
    }    
}
