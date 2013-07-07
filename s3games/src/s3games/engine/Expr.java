/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.HashSet;

/**
 *
 * @author petrovic16
 */
public abstract class Expr
{

    public enum operatorType { EQUALS, NOTEQUALS, LOWER, LOWEREQUAL, GREATER, GREATEREQUAL,
                        PLUS, MINUS, TIMES, DIV, MOD, SUBSET, ELEMENT, SETMINUS,
                        UNION, INTERSECTION, AND, OR, NOT, ASSIGNMENT, UNKNOWN };

    public enum internalFunction { IF, FORALL, LOCTYPE, ELTYPE, STATE, LOCATION,
                             CONTENT, EMPTY, INDEX, INDEXA, UNINDEX, OWNER, PLAYER,
                             NEXT, SCORE, ZINDEX, MOVE, SETOWNER, SETZINDEX, UNKNOWN }

    public Expr[] args;

    public Expr()
    {
        args = new Expr[0];
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
        else if (fn.equals("NEXT")) return internalFunction.NEXT;
        else if (fn.equals("SCORE")) return internalFunction.SCORE;
        else if (fn.equals("ZINDEX")) return internalFunction.ZINDEX;
        else if (fn.equals("MOVE")) return internalFunction.MOVE;
        else if (fn.equals("SETOWNER")) return internalFunction.SETOWNER;
        else if (fn.equals("SETZINDEX")) return internalFunction.SETZINDEX;
        else return internalFunction.UNKNOWN;
    }

    public abstract Expr eval();
}


class Expr_NUM_CONSTANT extends Expr
{
    public int num;

    public Expr_NUM_CONSTANT(int num)
    {
        this.num = num;
    }

    @Override
    public Expr eval()
    {
        return this;
    }
}

class Expr_STR_CONSTANT extends Expr
{
    public String str;

    public Expr_STR_CONSTANT(String str)
    {
        this.str = str;
    }

    @Override
    public Expr eval()
    {
        return this;
    }
}

class Expr_LOG_CONSTANT extends Expr
{
    public boolean b;

    public Expr_LOG_CONSTANT(boolean b)
    {
        this.b = b;
    }

    @Override
    public Expr eval()
    {
        return this;
    }
}

class Expr_SET_CONSTANT extends Expr
{
    public HashSet<Expr> set;

    public Expr_SET_CONSTANT()
    {
        set = new HashSet<Expr>();
    }

    @Override
    public Expr eval()
    {
        return this;
    }
}

class Expr_VARIABLE extends Expr
{
    String varName;
    Variables vars;

    public Expr_VARIABLE(String var, Variables vars)
    {
        varName = var;
        this.vars = vars;
    }

    @Override
    public Expr eval()
    {
        return null;
    }
}

class Expr_STRING_WITH_VAR_REF extends Expr
{
    String str;
    Variables vars;

    public Expr_STRING_WITH_VAR_REF(String str, Variables vars)
    {
        this.str = str;
        this.vars = vars;
    }

    @Override
    public Expr eval()
    {
        return null;
    }
}

class Expr_EXPRESSION_CALL extends Expr
{
    String exprName;
    Variables vars;

    public Expr_EXPRESSION_CALL(String exprName, Variables vars, Expr[] args)
    {
        this.exprName = exprName;
        this.vars = vars;
        this.args = args;
    }

    @Override
    public Expr eval()
    {
        return null;
    }
}

class Expr_INTERNAL_FN extends Expr
{
    internalFunction fn;
    Variables vars;

    public Expr_INTERNAL_FN(internalFunction fn, Variables vars, Expr[] args)
    {
        this.fn = fn;
        this.vars = vars;
        this.args = args;
    }

    @Override
    public Expr eval()
    {
        return null;
    }
}

class Expr_OPERATOR extends Expr
{
    operatorType op;
    Variables vars;

    public Expr_OPERATOR(operatorType op, Variables vars, Expr[] args)
    {
        this.op = op;
        this.vars = vars;
        this.args = args;
    }
    
    @Override
    public Expr eval()
    {
        return null;
    }
}
