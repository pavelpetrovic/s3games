/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games;

import s3games.gui.ControllerWindow;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import s3games.io.*;

/**
 *
 * @author petrovic16
 */
public class Controller   
{
    ControllerWindow cw;
    GameLogger logger;
    Config config;
    
    public Controller() {
        //GameWindow form =  new GameWindow();
        //form.setVisible(true);
        cw  = new ControllerWindow(this);
        cw.setVisible(true);
        logger = new GameLogger();
        config = new Config(logger);
        config.load();
    }
    
    public boolean readFile(BufferedReader r)
    {  //or can return a number of players after parsing...
        try {
           //todo - use parser 
           Scanner in=new Scanner(r);
           while (in.hasNext()) {
                System.out.println(in.next());
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cw, "bad file format");
            return false;
        }
       return true;
    }
    
}
