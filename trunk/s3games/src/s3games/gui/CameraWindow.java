/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import s3games.robot.Camera;

/**
 *
 * @author petrovic
 */
public class CameraWindow implements ActionListener
{
    Camera camera;
    
    JFrame win;
    JTextArea out;
    JButton goButton;
    
    boolean ourTurn;
    
    public CameraWindow(Camera camera)
    {
        this.camera = camera;
        ourTurn = false;
        
        win = new JFrame("Camera control panel");
        win.setLayout(new BorderLayout());
        out = new JTextArea("Connecting to camera...", 15, 60);
        out.setEditable(false);
        win.add(out, BorderLayout.CENTER);
        JPanel panel = new JPanel();        
        panel.setLayout(new FlowLayout());        
        goButton = new JButton("Go next player");
        panel.add(goButton); 
        win.add(panel, BorderLayout.SOUTH);                
        win.pack();
        win.setVisible(true);
        goButton.addActionListener(this);        
    }
    
    public void addMessage(String msg)
    {
        out.append(msg);        
    }
    
    public void moving(boolean b)
    {
        ourTurn = b;
        goButton.setEnabled(ourTurn);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        camera.detectSituation();
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
