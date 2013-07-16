/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.DepthFirstSearchPlayer;
import s3games.player.Player;

/**
 *
 * @author zuzycka
 */
public class DepthFirstSearchStrategy extends Strategy{
    
    

    @Override
    public Player getPlayer(GameSpecification specs) {
        return new DepthFirstSearchPlayer(specs);
    }

    @Override
    public void learn(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
