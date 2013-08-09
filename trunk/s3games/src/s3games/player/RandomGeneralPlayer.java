package s3games.player;

import java.util.*;
import s3games.engine.GameState;
import s3games.engine.Move;

/** Always plays a random move - can be used in any game */
public class RandomGeneralPlayer extends Player
{
    /** a random generator used by this player */
    Random rnd;
    
    /** construct a random player */
    public RandomGeneralPlayer()
    {
        rnd = new Random();
    }        
    
    /** perform a random move */
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) 
    {
        try { Thread.sleep(1000); } catch (Exception e) {}
        return allowedMoves.get(rnd.nextInt(allowedMoves.size()));
    }
}
