/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.AStarPlayer;
import s3games.player.Player;

/**
 *
 * @author Zuzka
 */
public class AStarStrategy extends Strategy {

    public AStarStrategy(Heuristic h)
    {
        setHeuristic(h);        
    }
    
    @Override
    public Player getPlayer(GameSpecification specs) {
       return new AStarPlayer(specs);
    }

    @Override
    public void learn(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
