/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine.expr;

import s3games.engine.expr.Expr;

/**
 *
 * @author petrovic
 */
public class Expression 
{
    public Expr expr;
    public String[] argNames;  // without leading $
    
    public Expression(String[] argNames)
    {        
        this.argNames = argNames;
    }
    
    public void addLine(String ln) throws Exception
    {
        Expr ex = Expr.parseExpr(ln);
        if (expr == null) expr = ex;
        else expr = expr.append(ex);
    }
}
