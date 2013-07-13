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

    public enum internalFunction { IF, FORALL, LOCTYPE, ELTYPE, STATE, LOCATION,
                             CONTENT, EMPTY, INDEX, INDEXA, UNINDEX, OWNER, PLAYER,
                             SCORE, ZINDEX, MOVE, SETOWNER, SETSTATE, SETZINDEX, UNKNOWN }

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
        return parseExpr(lexs);
    }
        
    static Expr parseOperator(ArrayList<Lexeme> leftArgument, ArrayList<Lexeme> rightArgument, Expr.operatorType op) throws Exception
    {
        Expr firstArgument = parseExpr(leftArgument);
        Expr secondArgument = parseExpr(rightArgument);
        return new Expr_OPERATOR(op, new Expr[] { firstArgument, secondArgument });            
    }
        
    static Expr[] parseList(ArrayList<Lexeme> argLexs) throws Exception
    {
        Expr[] listOfArgs = new Expr[argLexs.size()];
        ArrayList<Lexeme> oneArg = new ArrayList<Lexeme>();
        for (int i = 0; i < listOfArgs.length; i++)                    
        {
            if (i > 0) oneArg.clear();
            oneArg.add(argLexs.get(i));
            listOfArgs[i++] = parseExpr(oneArg);
        }
        return listOfArgs;
    }
    
    static Expr parseExpr(ArrayList<Lexeme> lexs) throws Exception
    {        
        if (lexs.isEmpty()) throw new Exception("empty expression");
        Lexeme lex = lexs.get(0);
        lexs.remove(0);
        if (lex instanceof OperatorLexeme)
        {
            if (((OperatorLexeme)lex).op == operatorType.NOT)
            {
                if (lexs.isEmpty()) throw new Exception("operator NOT without argument");
                return new Expr_OPERATOR(operatorType.NOT, new Expr[] { parseExpr(lexs) });
            }            
            throw new Exception("misplaced operator");
        }
            
        if (!lexs.isEmpty())
        {
            Lexeme lex2 = lexs.get(0);           
                
            if (lex2 instanceof OperatorLexeme)
            {
                lexs.remove(0);
                ArrayList<Lexeme> first = new ArrayList<Lexeme>();
                first.add(lex);
                return parseOperator(first, lexs, ((OperatorLexeme)lex2).op);
            }
        }
        
        if (lexs.size() > 1)
        {
            Lexeme lex2 = lexs.get(0);
            Lexeme lex3 = lexs.get(1);

            if ( ((lex instanceof WordLexeme) || (lex instanceof InternalFunctionLexeme)) &&
                 (lex2 instanceof ParenthesesLexeme) && (lex3 instanceof OperatorLexeme) )
            {
                lexs.remove(0);
                lexs.remove(1);
                ArrayList<Lexeme> first = new ArrayList<Lexeme>();
                first.add(lex);
                first.add(lex2);
                return parseOperator(first, lexs, ((OperatorLexeme)lex3).op);
            }
        }
        
        if (!lexs.isEmpty())
        {
            Lexeme lex2 = lexs.get(0);
            if (((lex instanceof WordLexeme) || (lex instanceof InternalFunctionLexeme)) &&
                (lex2 instanceof ParenthesesLexeme))
            {
                lexs.remove(0);
                Expr[] listOfArgs = parseList(((ParenthesesLexeme)lex2).lexs);
                if (lex instanceof WordLexeme)
                    return new Expr_EXPRESSION_CALL(((WordLexeme)lex).val, listOfArgs);
                else return new Expr_INTERNAL_FN(((InternalFunctionLexeme)lex).fn, listOfArgs);
            }
        }
                
        if (!lexs.isEmpty()) throw new Exception("unexpected trailing lexemes " + lexs);
        if (lex instanceof NumberLexeme)
            return new Expr_NUM_CONSTANT(((NumberLexeme)lex).val);
        if (lex instanceof BooleanLexeme)
            return new Expr_LOG_CONSTANT(((BooleanLexeme)lex).val);
        if (lex instanceof StringLexeme)
            return new Expr_STR_CONSTANT(((StringLexeme)lex).val);
        if (lex instanceof WordLexeme)
            return new Expr_EXPRESSION_CALL(((WordLexeme)lex).val, new Expr[0]);
        if (lex instanceof InternalFunctionLexeme)
            return new Expr_INTERNAL_FN(((InternalFunctionLexeme)lex).fn, new Expr[0]);
        if (lex instanceof SetLexeme)
            return new Expr_SET(new ArrayList<Expr>(Arrays.asList((parseList(((SetLexeme)lex).elems)))));
        if (lex instanceof VariableLexeme)
            return new Expr_VARIABLE(((VariableLexeme)lex).name);
        if (lex instanceof StringWithReferencesLexeme)
            return new Expr_STRING_WITH_VAR_REF(((StringWithReferencesLexeme)lex).strWithoutVars, 
                                                ((StringWithReferencesLexeme)lex).vars);
        if (lex instanceof ParenthesesLexeme)
            return parseExpr(((ParenthesesLexeme)lex).lexs);
        throw new Exception("unexpected lexeme" + lex);     
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
        else if (fn.equals("SCORE")) return internalFunction.SCORE;
        else if (fn.equals("ZINDEX")) return internalFunction.ZINDEX;
        else if (fn.equals("MOVE")) return internalFunction.MOVE;
        else if (fn.equals("SETOWNER")) return internalFunction.SETOWNER;
        else if (fn.equals("SETSTATE")) return internalFunction.SETSTATE;
        else if (fn.equals("SETZINDEX")) return internalFunction.SETZINDEX;
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
    
    public int getInt()
    {
        return ((Expr_NUM_CONSTANT)this).num;
    }
    
    public String getStr()
    {
        return ((Expr_STR_CONSTANT)this).str;
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

class Expr_NUM_CONSTANT extends Expr
{
    public int num;

    public Expr_NUM_CONSTANT(int num)
    {
        this.num = num;
    }

    @Override
    public Expr eval(Context context)
    {        
        return this;
    }
    
    @Override
    public boolean equals(Expr other, Context context) throws Exception
    {
        other = other.eval(context);
        if (other instanceof Expr_NUM_CONSTANT)        
            return num == ((Expr_NUM_CONSTANT)other).num;
        return false;
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
    public Expr eval(Context context)
    {
        return this;
    }
    
    @Override
    public boolean equals(Expr other, Context context) throws Exception
    {
        other = other.eval(context);
        if (other instanceof Expr_STR_CONSTANT)        
            return str.equals(((Expr_STR_CONSTANT)other).str);
        return false;
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
    public Expr eval(Context context)
    {
        return this;
    }
    
    @Override
    public boolean equals(Expr other, Context context) throws Exception
    {
        other = other.eval(context);
        if (other instanceof Expr_LOG_CONSTANT)        
            return b == ((Expr_LOG_CONSTANT)other).b;
        return false;
    }
}

class Expr_SET extends Expr
{
    public ArrayList<Expr> set;

    public Expr_SET(ArrayList<Expr> contents)
    {
        set = contents;
    }

    @Override
    public Expr eval(Context context) throws Exception
    {
        ArrayList<Expr> newContents = new ArrayList<Expr>();
        for(Expr e: set)
            newContents.add(e.eval(context));
        return new Expr_SET(newContents);
    }
    
    @Override
    public boolean equals(Expr other, Context context) throws Exception
    {
        Expr elems[] = new Expr[set.size()];
        for(int i = 0; i < set.size(); i++)
            elems[i] = set.get(i).eval(context);        
        other = other.eval(context);
        if (other instanceof Expr_SET)
        {
            if (elems.length != ((Expr_SET)other).set.size())
                return false;
            for (int i = 0; i < set.size(); i++)
                if (!elems[i].equals(((Expr_SET)other).set.get(i), context))
                    return false;
        }
        else return false;
        return true;
    }
}

class Expr_VARIABLE extends Expr
{
    String varName;   

    public Expr_VARIABLE(String var)
    {
        varName = var;
    }

    @Override
    public Expr eval(Context context) throws Exception
    {
        Expr val = context.getVar(varName);
        if (val == null) throw new Exception("evaluating unbound variable " + varName);
        return val;
    }
}

class Expr_STRING_WITH_VAR_REF extends Expr
{
    String str;
    TreeMap<Integer, String> varRefs;    

    public Expr_STRING_WITH_VAR_REF(String str, TreeMap<Integer, String> varRefs)
    {
        this.str = str;
        this.varRefs = varRefs;
    }

    @Override
    public Expr eval(Context context)
    {
        StringBuilder s = new StringBuilder(str);
        int pos = 0;
        for(Map.Entry<Integer, String> var: varRefs.entrySet())
        {
            s.append(str.substring(pos, var.getKey()));
            s.append(context.getVar(var.getValue()));
            pos = var.getKey();
        }
        return new Expr_STR_CONSTANT(s.toString());
    }
}

class Expr_EXPRESSION_CALL extends Expr
{
    String exprName;   
    Expr[] args;

    public Expr_EXPRESSION_CALL(String exprName, Expr[] args)
    {
        this.exprName = exprName;
        this.args = args;
    }

    void evalArgs(Context context, String[] argNames) throws Exception
    {        
        if (args.length != argNames.length) 
            throw new Exception("function expects " + argNames.length + " arguments, but " + args.length + " provided");
        for (int i = 0; i < args.length; i++)
        {
            Expr evaluatedArg = args[i].eval(context);
            context.setVar(argNames[i], evaluatedArg);
        }
    }
    
    @Override
    public Expr eval(Context context) throws Exception
    {
        Expression expr = context.getExpr(exprName);
        evalArgs(context, expr.argNames);
        return expr.expr.eval(context);
    }
}

class Expr_INTERNAL_FN extends Expr
{
    internalFunction fn;
    Expr[] args;

    public Expr_INTERNAL_FN(internalFunction fn, Expr[] args)
    {
        this.fn = fn;
        this.args = args;
    }

    @Override
    public Expr eval(Context context) throws Exception
    {
        return InternalFunctions.eval(fn, args, context);
    }
}

class Expr_OPERATOR extends Expr
{
    operatorType op;
    Expr[] args;

    public Expr_OPERATOR(operatorType op, Expr[] args)
    {
        this.op = op;
        this.args = args;
    }
    
    @Override
    public Expr eval(Context context) throws Exception
    {
        return Operators.eval(op, args, context);
    }
}

class Expr_LIST extends Expr
{
    ArrayList<Expr> exprs;
    
    public Expr_LIST(Expr expr)
    {
        exprs = new ArrayList<Expr>();
        exprs.add(expr);
    }

    public void add(Expr expr)
    {
        exprs.add(expr);
    }
    
    @Override 
    public Expr eval(Context context) throws Exception
    {
        Expr result = null;
        for(Expr expr: exprs)
            result = expr.eval(context);
        return result;
    }
}
