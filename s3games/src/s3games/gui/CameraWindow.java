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
        out = new JTextArea(15, 60);
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
        out.append(System.getProperty("line.separator"));
    }
    
    public void moving(boolean b)
    {
        ourTurn = b;
        goButton.setEnabled(ourTurn);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        camera.requestObjectsFromCamera();        
    }
    
    public void close()
    {
       win.dispose();
       win = null;
       camera = null;
    }
}
