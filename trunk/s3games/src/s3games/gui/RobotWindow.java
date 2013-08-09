package s3games.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import s3games.robot.Robot;

/** A simple control window for letting the user control when to make a new
 * move with the robot arm, and perhaps do some other operations on the robot */
public class RobotWindow extends ControlWindow implements ActionListener
{
    /** handle to the robot interface object */
    Robot robot;
    
    /** request the move to be performed by robot arm */
    JButton goButton;
    
    /** clear the informative text area */
    JButton clearButton;
    
    /** do test movements with the robot, such as init, home, grab, put */
    JButton testButton;
    
    /** make the robot show a demo of moving to all defined locations */
    JButton allLocationsButton;
    
    /** initialize the robot */
    JButton initButton;
    
    /** pause the all locations demo */
    JButton pauseButton;
    
    /** do a manual keyboard-control of the robot */
    JButton controlButton;
    
    /** are we allowed to make a move */
    boolean ourTurn;
    
    /** construct the robot control panel window */
    public RobotWindow(Robot robot)
    {
        super("Robot control panel");
        this.robot = robot;
        ourTurn = false;
    }
    
    /** add the robot control panel buttons to the panel */
    @Override
    protected void addButtonsToPanel(JPanel panel) 
    {
        goButton = new JButton("Perform move");
        panel.add(goButton); 
        clearButton = new JButton("Clear");
        panel.add(clearButton);
        testButton = new JButton("Test");
        panel.add(testButton);
        allLocationsButton = new JButton("Show all locations");
        panel.add(allLocationsButton);
        initButton = new JButton("Init");
        panel.add(initButton);
        pauseButton = new JButton("Pause");
        panel.add(pauseButton);
        controlButton = new JButton("Control");
        panel.add(controlButton);
        
        goButton.addActionListener(this);            
        clearButton.addActionListener(this); 
        testButton.addActionListener(this);
        allLocationsButton.addActionListener(this);
        initButton.addActionListener(this);
        pauseButton.addActionListener(this);
        controlButton.addActionListener(this);
    }
        
    /** allow performing the move now */
    public void moving(boolean b)
    {
        ourTurn = b;
        goButton.setEnabled(ourTurn);
    }
    
    /** action listener for the buttons */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == goButton)
        {
            try { robot.performMove(); }
            catch (Exception ex) { addMessage("Robot could not perform a move: " + ex.getMessage()); }
        }
        else if (e.getSource() == clearButton)
            out.setText("");
        else if (e.getSource() == testButton)
            robot.doTest();
        else if (e.getSource() == allLocationsButton)
            robot.allLocationsDemo();
        else if (e.getSource() == initButton)
            robot.initArm();
        else if (e.getSource() == pauseButton)
            robot.pause();
        else if (e.getSource() == controlButton)
            robot.control();
    }
    
    /** close the robot window */
    @Override
    public void close()
    {
        super.close();
        robot = null;
    }
}
