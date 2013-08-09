package s3games.engine.expr;

import java.util.*;
import s3games.engine.*;
import s3games.robot.Robot;

/** Holds a context of evaluation - as the rules are matched against locations
 * and elements, the indexing variables are filled with values. While expressions
 * are evaluated, variables are assigned values. Holds reference to current state
 * and game specification for easier access from internal functions and operators
 * during evaluation */
public class Context
{
    /** values of all variables. variables are global and stay visible while the context is valid */
    private Map<String, Expr> vars;
    
    /** reference to the current game state */
    private GameState gameState;
    
    /** reference to robot - to be able to perform internal MOVE command when required by an expression */
    public Robot robot;
    
    /** reference to game specification */
    public GameSpecification specs;
    
    /** construct a new empty context */
    public Context(GameState state, GameSpecification specs, Robot robot)
    {
        this.gameState = state;
        this.specs = specs;
        vars = new TreeMap<String, Expr>();        
        this.robot = robot;
    }
    
    /** set the game state for this context - the same context is reused when 
     * searching through the game tree, a particular current state always 
     * needs to be set */
    public void setState(GameState state)
    {
        gameState = state;
    }
    
    /** retrieve the game sate in this context */
    public GameState getState()
    {
        return gameState;
    }
    
    /* set a value of the specified variable */
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
