/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author petrovic16
 */
public class Expression
{
    public static final String ANONYMOUS = "ANONYMOUS";

    public String name;
    public ArrayList<Expr> lines;

    public Expression(String name)
    {
        this.name = name;
        lines = new ArrayList<Expr>();
    }

    public Expression(String name, String singleLine)
    {
        this(name);
        addLine(singleLine);
    }

    public final void addLine(String ln)
    {
        lines.add(parseExpr(ln));
    }

    static final String allSeparators = "=!<=>+-*/% \t(),";

    Expr parseExpr(String ln)
    {
        StringTokenizer st = new StringTokenizer(ln, allSeparators);
        String first = st.nextToken();
        Expr.internalFunction fn = Expr.getInternalFunction(first);
        
        return null;
    }
}
