/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games;

import s3games.gui.ControllerWindow;
import s3games.gui.GameWindow;

/**
 *
 * @author petrovic16
 */
public class S3Games {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GameWindow form =  new GameWindow();
        form.setVisible(true);
        ControllerWindow f  = new ControllerWindow();
        f.setVisible(true);
    }

}
