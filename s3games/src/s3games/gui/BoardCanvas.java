/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;

/**
 *
 * @author Boris
 */
public class BoardCanvas extends Canvas {
    
    public BoardCanvas() {
      
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawLine(4, 3, 50,48);
    }
}
