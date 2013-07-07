/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import s3games.engine.ExtendedGameState;
import s3games.engine.GameSpecification;

/**
 *
 * @author Boris
 */
public class BoardCanvas extends Canvas {
    GameSpecification gameSpec = null;
    ExtendedGameState egameState = null;
    Image bgImage;
    
    public BoardCanvas() {
      
    }
    
    public void setGame(GameSpecification gs) {
        gameSpec = gs;
        bgImage = Toolkit.getDefaultToolkit().getImage(gs.boardBackgroundFileName);
        egameState = null;
        System.out.println("tu som");
    }
        
    public void setState(ExtendedGameState egs) {
        egameState = egs;
        
        
        System.out.println("tuna");
    }
    
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());  //clear window
        if (gameSpec!=null) {
            g.drawImage(bgImage,0,0,this.getWidth(),this.getHeight(),this); //background
            if (egameState!=null) {
            
            
            }
        }
    }
}
