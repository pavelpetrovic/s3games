/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine.expr;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author petrovic
 */
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
    public boolean matches(int i, Context context)
    {
        return num == i;
    }
    
    @Override
    public int getInt()
    {
        return num;
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
    public boolean matches(String str, Context context)
    {
        return this.str.equals(str);
    }
    
    @Override
    public String getStr()
    {
        return str;
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
    public boolean isTrue()
    {
        return b;
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
    
    @Override
    public boolean matches(String str, Context context)
    {
        context.vars.put(varName, new Expr_STR_CONSTANT(str));
        return true;
    }
    
    @Override
    public boolean matches(int i, Context context)
    {
        context.vars.put(varName, new Expr_NUM_CONSTANT(i));
        return true;
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
    public Expr eval(Context context) throws Exception
    {
        StringBuilder s = new StringBuilder("");
        int pos = 0;
        for(Map.Entry<Integer, String> var: varRefs.entrySet())
        {
            s.append(str.substring(pos, var.getKey()));
            s.append(context.getVar(var.getValue()).getInt());
            pos = var.getKey();
        }
        s.append(str.substring(pos));
        return new Expr_STR_CONSTANT(s.toString());
    }
    
    @Override
    public boolean matches(String s, Context context)
    {
        String[] base = s.split("[0-9]+");
        String[] numbers = s.split("[^0-9]+");
        StringBuilder glued = new StringBuilder();
        if (numbers.length - 1 != varRefs.size()) return false;
        for(String b:base) glued.append(b);        
        if (!glued.toString().equals(str)) return false;
        int pos = 0;
        int[] positions = new int[numbers.length - 1];
        for(int i = 0; i < numbers.length - 1; i++)
        {
            pos += base[i].length();
            positions[i] = pos;
        }
        int i = 0;
        for (Integer p: varRefs.keySet())
            if (p != positions[i++]) return false;
        i = 0;
        for (String v: varRefs.values())
            context.vars.put(v, new Expr_NUM_CONSTANT(Integer.parseInt(numbers[++i])));
        return true;
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
        if (expr == null) throw new Exception("Attempt to call a non-existing function " + exprName);
        evalArgs(context, expr.argNames);
        return expr.expr.eval(context);
    }
}

class Expr_INTERNAL_FN extends Expr
{
    Expr.internalFunction fn;
    Expr[] args;

    public Expr_INTERNAL_FN(Expr.internalFunction fn, Expr[] args)
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
    Expr.operatorType op;
    Expr[] args;

    public Expr_OPERATOR(Expr.operatorType op, Expr[] args)
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
        {
            result = expr.eval(context);
            if (!result.isTrue()) return new Expr_LOG_CONSTANT(false);
        }
        return result;
    }
}
