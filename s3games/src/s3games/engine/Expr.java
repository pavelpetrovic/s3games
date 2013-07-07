/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

/**
 *
 * @author petrovic16
 */
public abstract class Expr
{

    enum operatorType { EQUALS, NOTEQUALS, LOWER, LOWEREQUAL, GREATER, GREATEREQUAL,
                        PLUS, MINUS, TIMES, DIV, MOD, SUBSET, ELEMENT, SETMINUS,
                        UNION, INTERSECTION, AND, OR, NOT, ASSIGNMENT };

    enum internalFunctions { IF, FORALL, LOCTYPE, ELTYPE, STATE, LOCATION,
                             CONTENT, EMPTY, INDEX, INDEXA, UNINDEX, OWNER, PLAYER,
                             NEXT, SCORE, ZINDEX, MOVE, SETOWNER, SETZINDEX }

    int arity;
    Expr[] args;

    abstract Expr eval();
}


class Expr_NUM_CONSTANT extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_STR_CONSTANT extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_LOG_CONSTANT extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_SET_CONSTANT extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}


class Expr_VARIABLE extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_STRING_WITH_VAR_REF extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_EXPRESSION_CALL extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_INTERNAL_FN extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}

class Expr_OPERATOR extends Expr
{
    @Override
    Expr eval()
    {
        return null;
    }
}
