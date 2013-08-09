package s3games.engine;

import java.util.ArrayList;
import s3games.engine.expr.Expr;

/** represents the rules for updating the scores of players based on the
 * states they reach - as contrasted to the moves they make (those score
 * updates are contained directly in the game rules */
public class GameScoring 
{
    /** a situation that should evaluate to true if the score is to be updated */
    public Expr situation;
    /** expressions telling the player numbers who should have the score updated */
    public ArrayList<Expr> players;
    /** the actual scores to be added to the players listed in players list at the same index */
    public ArrayList<Expr> amounts;
    
    /** construct an empty game scoring set of rules */
    public GameScoring(Expr situation)
    {
        this.situation = situation;
        players = new ArrayList<Expr>();
        amounts = new ArrayList<Expr>();
    }
}
