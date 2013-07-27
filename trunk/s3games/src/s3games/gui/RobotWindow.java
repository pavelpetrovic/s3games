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
import s3games.robot.Robot;

/**
 *
 * @author petrovic
 */
public class RobotWindow extends ControlWindow implements ActionListener
{
    Robot robot;
    
    JButton goButton;
    JButton clearButton;
    JButton testButton;
    
    boolean ourTurn;
    
    public RobotWindow(Robot robot)
    {
        super("Robot control panel");
        this.robot = robot;
        ourTurn = false;
    }
    
    @Override
    protected void addButtonsToPanel(JPanel panel) 
    {
        goButton = new JButton("Perform move");
        panel.add(goButton); 
        clearButton = new JButton("Clear");
        panel.add(clearButton);
        testButton = new JButton("Test");
        panel.add(testButton);
        
        goButton.addActionListener(this);            
        clearButton.addActionListener(this); 
        testButton.addActionListener(this);
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
            robot.performMove();        
        else if (e.getSource() == clearButton)
            out.setText("");
        else if (e.getSource() == testButton)
            robot.doTest();
    }
    
    @Override
    public void close()
    {
        super.close();
        robot = null;
    }
}
