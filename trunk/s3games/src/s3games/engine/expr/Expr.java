/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine.expr;

import java.util.*;
import s3games.util.IndexedName;

/**
 *
 * @author petrovic16
 */
public abstract class Expr
{
    public enum operatorType { EQUALS, NOTEQUALS, LOWER, LOWEREQUAL, GREATER, GREATEREQUAL,
                        PLUS, MINUS, TIMES, DIV, MOD, SUBSET, ELEMENT, SETMINUS,
                        UNION, INTERSECTION, AND, OR, NOT, ASSIGNMENT, UNKNOWN };

    public enum internalFunction { IF, FORALL, FORSOME, LOCTYPE, ELTYPE, STATE, LOCATION,
                             CONTENT, EMPTY, INDEX, INDEXA, UNINDEX, OWNER, PLAYER,
                             SCORE, ZINDEX, MOVE, SETOWNER, SETSTATE, SETZINDEX, NEXTPLAYER, UNKNOWN }

    public Expr append(Expr expr)
    {
        Expr el = this;
        if (!(el instanceof Expr_LIST)) el = new Expr_LIST(this);
        ((Expr_LIST)el).add(expr);
        return el;
    }
    
    public static Expr parseExpr(String ln) throws Exception
    {
        ArrayList<Lexeme> lexs = Lexeme.parseLine(ln);
        return ExprParser.parseExpr(lexs);
    }
                
    public static operatorType getOperatorType(String op)
    {
        if (op.equals("==")) return operatorType.EQUALS;
        else if (op.equals("!=")) return operatorType.NOTEQUALS;
        else if (op.equals("<")) return operatorType.LOWER;
        else if (op.equals("<=")) return operatorType.LOWEREQUAL;
        else if (op.equals(">")) return operatorType.GREATER;
        else if (op.equals("<=")) return operatorType.GREATEREQUAL;
        else if (op.equals("+")) return operatorType.PLUS;
        else if (op.equals("-")) return operatorType.MINUS;
        else if (op.equals("*")) return operatorType.TIMES;
        else if (op.equals("/")) return operatorType.DIV;
        else if (op.equals("%")) return operatorType.MOD;
        else if (op.equals("SUBSET")) return operatorType.SUBSET;
        else if (op.equals("IN")) return operatorType.ELEMENT;
        else if (op.equals("SETMINUS")) return operatorType.SETMINUS;
        else if (op.equals("UNION")) return operatorType.UNION;
        else if (op.equals("INTERSECT")) return operatorType.INTERSECTION;
        else if (op.equals("AND")) return operatorType.AND;
        else if (op.equals("OR")) return operatorType.OR;
        else if (op.equals("NOT")) return operatorType.NOT;
        else if (op.equals("=")) return operatorType.ASSIGNMENT;
        else return operatorType.UNKNOWN;
    }

    public static internalFunction getInternalFunction(String fn)
    {
        if (fn.equals("IF")) return internalFunction.IF;
        else if (fn.equals("FORALL")) return internalFunction.FORALL;
        else if (fn.equals("FORSOME")) return internalFunction.FORSOME;
        else if (fn.equals("LOCTYPE")) return internalFunction.LOCTYPE;
        else if (fn.equals("ELTYPE")) return internalFunction.ELTYPE;
        else if (fn.equals("STATE")) return internalFunction.STATE;
        else if (fn.equals("LOCATION")) return internalFunction.LOCATION;
        else if (fn.equals("CONTENT")) return internalFunction.CONTENT;
        else if (fn.equals("EMPTY")) return internalFunction.EMPTY;
        else if (fn.equals("INDEX")) return internalFunction.INDEX;
        else if (fn.equals("INDEXA")) return internalFunction.INDEXA;
        else if (fn.equals("UNINDEX")) return internalFunction.UNINDEX;
        else if (fn.equals("OWNER")) return internalFunction.OWNER;
        else if (fn.equals("PLAYER")) return internalFunction.PLAYER;
        else if (fn.equals("SCORE")) return internalFunction.SCORE;
        else if (fn.equals("ZINDEX")) return internalFunction.ZINDEX;
        else if (fn.equals("MOVE")) return internalFunction.MOVE;
        else if (fn.equals("SETOWNER")) return internalFunction.SETOWNER;
        else if (fn.equals("SETSTATE")) return internalFunction.SETSTATE;
        else if (fn.equals("SETZINDEX")) return internalFunction.SETZINDEX;
        else if (fn.equals("NEXTPLAYER")) return internalFunction.NEXTPLAYER;
        else return internalFunction.UNKNOWN;
    }

    public static Expr numExpr(int num)
    {
        return new Expr_NUM_CONSTANT(num);
    }
    
    public static Expr strExpr(String str)
    {
        return new Expr_STR_CONSTANT(str);
    }
    
    public static Expr booleanExpr(boolean b)
    {
        return new Expr_LOG_CONSTANT(b);
    }
    
    public int getInt() throws Exception
    {
        throw new Exception("expected numeric expression, but it's " + this + " here");
    }
    
    public String getStr() throws Exception
    {
        throw new Exception("expected string expression, but it's " + this + " here");        
    }
    
    public boolean isTrue()
    {
        return false;
    }
    
    public boolean isFalse()
    {
        return false;
    }
        
    public boolean matches(String s, Context context)
    {
        return false;
    }
    
    public boolean matches(int i, Context context)
    {
        return false;
    }
    
    public boolean matches(IndexedName name, Context context)
    {
        return false;
    }
    
    public abstract Expr eval(Context context) throws Exception;
    public boolean equals(Expr other, Context context) throws Exception
    {
        Expr val = eval(context);        
        return val.equals(other, context);
    }
}

