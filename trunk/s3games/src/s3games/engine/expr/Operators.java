/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine.expr;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author petrovic
 */
public class Operators 
{
    static Expr eval(Expr.operatorType op, Expr[] args, Context context) throws Exception
    {
        switch (op)
        {
            case EQUALS: return new Expr_LOG_CONSTANT(args[0].equals(args[1], context));
            case NOTEQUALS: return new Expr_LOG_CONSTANT(!(args[0].equals(args[1], context)));
        }
        if (op == Expr.operatorType.ABS)
        {
            Expr a0 = args[0].eval(context);
            if (!(a0 instanceof Expr_NUM_CONSTANT)) throw new Exception("operator ABS requires number");
            return Expr.numExpr(Math.abs(((Expr_NUM_CONSTANT)a0).num));
        }
        if ((op == Expr.operatorType.LOWER) || 
            (op == Expr.operatorType.LOWEREQUAL) ||
            (op == Expr.operatorType.GREATER) ||
            (op == Expr.operatorType.GREATEREQUAL) ||
            (op == Expr.operatorType.PLUS) ||
            (op == Expr.operatorType.MINUS) ||    
            (op == Expr.operatorType.TIMES) ||
            (op == Expr.operatorType.DIV) ||
            (op == Expr.operatorType.MOD))
        {
            Expr a0 = args[0].eval(context);
            if (!(a0 instanceof Expr_NUM_CONSTANT)) throw new Exception("numeric operator with first arg non-numeric type");
            Expr a1 = args[1].eval(context);
            if (!(a1 instanceof Expr_NUM_CONSTANT)) 
                throw new Exception("operator " + op + " with second arg non-numeric type");
            int arg0 = ((Expr_NUM_CONSTANT)a0).num;
            int arg1 = ((Expr_NUM_CONSTANT)a1).num;
            switch (op)
            {
                case LOWER: return Expr.booleanExpr(arg0 < arg1);
                case LOWEREQUAL: return Expr.booleanExpr(arg0 <= arg1);
                case GREATER: return Expr.booleanExpr(arg0 > arg1);
                case GREATEREQUAL: return Expr.booleanExpr(arg0 >= arg1);
                case PLUS: return Expr.numExpr(arg0 + arg1);
                case MINUS: return Expr.numExpr(arg0 - arg1);
                case TIMES: return Expr.numExpr(arg0 * arg1);
                case DIV: return Expr.numExpr(arg0 / arg1);
                case MOD: return Expr.numExpr(arg0 % arg1);
            }
        }
        if ((op == Expr.operatorType.AND) ||
            (op == Expr.operatorType.OR) ||
            (op == Expr.operatorType.NOT))
        {
            Expr a0 = args[0].eval(context);
            if  (!(a0 instanceof Expr_LOG_CONSTANT)) throw new Exception("logic operator with first argument non-logic type");
            boolean arg0 = ((Expr_LOG_CONSTANT)a0).b;
            if (op == Expr.operatorType.NOT)
                return Expr.booleanExpr(!arg0);
            if ((op == Expr.operatorType.AND) && (!arg0)) 
                return Expr.booleanExpr(false);
            if ((op == Expr.operatorType.OR) && arg0)
                return Expr.booleanExpr(true);
            Expr a1 = args[1].eval(context);
            if (!(a1 instanceof  Expr_LOG_CONSTANT)) throw new Exception("logic operator with second argument non-logic type");
            boolean arg1 = ((Expr_LOG_CONSTANT)a1).b;
            return Expr.booleanExpr(arg1);
        }
        if (op == Expr.operatorType.ASSIGNMENT)
        {
            if (!(args[0] instanceof Expr_VARIABLE))
                throw new Exception("attempting to assign to a non-variable");
            String varName = ((Expr_VARIABLE)args[0]).varName;
            Expr value = args[1].eval(context);
            context.setVar(varName, value);
            return Expr.booleanExpr(true);
        }
        if (op == Expr.operatorType.ELEMENT)
        {
            Expr a0 = args[0].eval(context);
            Expr a1 = args[1].eval(context);
            if (!(a1 instanceof Expr_SET))
                throw new Exception("ELEMENT operator with non-set on the right");
            for(Expr el:((Expr_SET)a1).set)
                if (a0.equals(el, context)) 
                    return Expr.booleanExpr(true);
            return Expr.booleanExpr(false);
        }

        if ((op == Expr.operatorType.SUBSET) ||
            (op == Expr.operatorType.SETMINUS) ||
            (op == Expr.operatorType.UNION) ||
            (op == Expr.operatorType.INTERSECTION))
        {
            Expr a0 = args[0].eval(context);
            if (!(a0 instanceof Expr_SET))
                throw new Exception("set operator with non-set on the left");
            Expr a1 = args[1].eval(context);
            if (!(a1 instanceof Expr_SET))
                throw new Exception("set operator with non-set on the right");
            Expr_SET arg0 = (Expr_SET)a0;
            Expr_SET arg1 = (Expr_SET)a1;
            if (op == Expr.operatorType.SUBSET)
            {
                for(Expr el0:arg0.set)
                {
                    boolean found = false;
                    for (Expr el1:arg1.set)
                        if (el0.equals(el1, context))
                        {
                            found = true;
                            break;
                        }
                    if (!found) return Expr.booleanExpr(false);
                }
                return Expr.booleanExpr(true);                     
            }
            if (op == Expr.operatorType.SETMINUS)
            {
                HashSet<Integer> toRemove = new HashSet<Integer>();
                int i = 0;
                for (Expr el0:arg0.set)
                {                    
                    boolean found = false;
                    for (Expr el1:arg1.set)
                        if (el0.equals(el1, context))
                        {
                            found = true;
                            break;
                        }
                    if (found) toRemove.add(i);
                    i++;
                }
                ArrayList<Expr> result = new ArrayList<Expr>();
                for (int j = 0; j < arg0.set.size(); j++)
                    if (!toRemove.contains(new Integer(j)))
                        result.add(arg0.set.get(j));                
                return new Expr_SET(result);
            }
            if (op == Expr.operatorType.UNION)
            {
                ArrayList<Expr> result = new ArrayList<Expr>(arg1.set);
                for (Expr el0:arg0.set)
                {                    
                    boolean found = false;
                    for (Expr el1:arg1.set)
                        if (el0.equals(el1, context))
                        {
                            found = true;
                            break;
                        }
                    if (!found) result.add(el0);                    
                }
                return new Expr_SET(result);
            }
            if (op == Expr.operatorType.INTERSECTION)
            {
                ArrayList<Expr> result = new ArrayList<Expr>();
                for (Expr el0:arg0.set)
                {                    
                    boolean found = false;
                    for (Expr el1:arg1.set)
                        if (el0.equals(el1, context))
                        {
                            found = true;
                            break;
                        }
                    if (found) result.add(el0);                    
                }
                return new Expr_SET(result);
            }
        }
        throw new Exception("evaluating unknown operator");
    }    
}
