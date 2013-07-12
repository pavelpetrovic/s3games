/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine;

import java.util.ArrayList;
import s3games.engine.expr.Expr;

/**
 *
 * @author petrovic
 */
public class GameScoring 
{
    public Expr situation;
    public ArrayList<Expr> players;
    public ArrayList<Expr> amounts;
    
    public GameScoring(Expr situation)
    {
        this.situation = situation;
        players = new ArrayList<Expr>();
        amounts = new ArrayList<Expr>();
    }
}
