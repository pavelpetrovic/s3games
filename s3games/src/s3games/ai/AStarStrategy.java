package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.AStarPlayer;
import s3games.player.Player;

/** A strategy class for the A* player */
public class AStarStrategy extends Strategy 
{

    /** construct an instance of the strategy
     * @param h a game-specific admissible heuristic for A* */     
    public AStarStrategy(Heuristic h)
    {
        setHeuristic(h);        
    }
    
    /** get a A* player object for the specified game
     * @param specs specification of the game to be played
     * @return a new instance of A* player */
    @Override
    public Player getPlayer(GameSpecification specs) 
    {
       return new AStarPlayer(specs,heuristic);
    }

    @Override
    /** no learning for A* yet - could find a path to goal state and then play() would just follow it - future work... */
    public void learn(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
