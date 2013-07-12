/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine.expr;

import java.util.*;

public abstract class Lexeme
{    
    public static ArrayList<Lexeme> parseLine(String ln) throws Exception
    {
        LexemeParser lp = new LexemeParser();
        return lp.parseLine(ln);
    }
}

class NumberLexeme extends Lexeme
{   
    int val;
    NumberLexeme(int val)
    {
        this.val = val;
    }
}

class BooleanLexeme extends Lexeme
{
    boolean val;
    BooleanLexeme(boolean val)
    {
        this.val = val;
    }
}

class StringLexeme extends Lexeme
{
    String val;
    StringLexeme(String str)
    {
        val = str;
    }
}

class SetLexeme extends Lexeme
{
    ArrayList<Lexeme> elems;
    SetLexeme(ArrayList<Lexeme> elements)
    {
        elems = elements;
    }
}

class WordLexeme extends Lexeme
{
    String val;
    WordLexeme(String val)
    {
        this.val = val;
    }
}

class ParenthesesLexeme extends Lexeme
{
    ArrayList<Lexeme> lexs;
        
    ParenthesesLexeme(ArrayList<Lexeme> lexemes)
    {
        lexs = lexemes;
    }
}

class OperatorLexeme extends Lexeme
{
    Expr.operatorType op;
    OperatorLexeme(Expr.operatorType operator)
    {
        op = operator;
    }
}

class InternalFunctionLexeme extends Lexeme
{
    Expr.internalFunction fn;
    InternalFunctionLexeme(Expr.internalFunction funct)
    {
        fn = funct;
    }
}

class VariableLexeme extends Lexeme
{
    String name;  // without leading $
    VariableLexeme(String name)
    {
        this.name = name;
    }
}

class StringWithReferencesLexeme extends Lexeme
{
    String strWithoutVars; 
    TreeMap<Integer, String> vars;
    
    StringWithReferencesLexeme(String strWithoutVars, TreeMap<Integer, String> vars)
    {
        this.strWithoutVars = strWithoutVars;
        this.vars = vars;
    }
}