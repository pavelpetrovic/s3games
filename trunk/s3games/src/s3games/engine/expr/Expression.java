package s3games.engine.expr;

/** Represent a named expression with named formal arguments. 
 * If it is single-line, it contains directly the respective 
 * expression class, otherwise it contains Expr_LIST. The 
 * name of this expression is stored as key in the map that
 * holds all named expressions, it is not needed here. */
public class Expression 
{
    /** the list of expressions one for each line (or single expression for one-line expressions) */
    public Expr expr;
    
    /** the names of the arguments without $ sign - used as variable names when this expression is called */
    public String[] argNames;  
    
    /** construct a new empty expression with the specified argument names */
    public Expression(String[] argNames)
    {        
        this.argNames = argNames;
    }
    
    /** append a new line at the end of this named expression */
    public void addLine(String ln) throws Exception
    {
        Expr ex = Expr.parseExpr(ln);
        if (expr == null) expr = ex;
        else expr = expr.append(ex);
    }
}
