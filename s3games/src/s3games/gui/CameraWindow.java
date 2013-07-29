/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import s3games.robot.Camera;

/**
 *
 * @author petrovic
 */
public class CameraWindow extends ControlWindow implements ActionListener
{
    Camera camera;
    
    JButton goButton;
    JButton clearButton;
    
    boolean ourTurn;
    
    public CameraWindow(Camera camera)
    {
        super("Camera control panel");
        this.camera = camera;
        ourTurn = false;
    }
    
    @Override
    protected void addButtonsToPanel(JPanel panel) 
    {
        goButton = new JButton("Go next player");
        panel.add(goButton); 
        clearButton = new JButton("Clear");
        panel.add(clearButton);
        goButton.addActionListener(this);        
        clearButton.addActionListener(this);        
    }
        
    public void moving(boolean b)
    {
        ourTurn = b;
        goButton.setEnabled(ourTurn);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == goButton)
            camera.requestObjectsFromCamera();        
        else if (e.getSource() == clearButton)
            out.setText("");
    }
    
    @Override
    public void close()
    {
        super.close();
        camera = null;
    }
}