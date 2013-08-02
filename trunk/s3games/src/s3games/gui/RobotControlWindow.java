/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import s3games.robot.Robot;
import s3games.robot.RobotLocation;

/**
 *
 * @author Nastavnik
 */
public class RobotControlWindow extends JFrame
{      
    final Robot robot;
    
    private final char keys[] = { 'q', 'a', 'w', 's', 'e', 'd', 'r', 'f', 't', 'g' };
        
    public RobotControlWindow(Robot robotReference)
    {
        robot = robotReference;
        setTitle("Robot Control");     
        position = new RobotLocation();     
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                char key = evt.getKeyChar();
                for (int i = 0; i < keys.length; i++)
                    if (key == keys[i])
                    {
                        int degreeIndex = i / 2;
                        int direction = ((i % 2) << 1) - 1;
                        position.angles[degreeIndex] += direction;
                        repaint();
                        try { robot.goTo(position.angles); }
                        catch (Exception e) {}                        
                    }
            }
        });
        setVisible(true);
        setSize(400,200);
    }
    
    RobotLocation position;
    
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.magenta);
        g.drawString("Angles: " + position.toString(), 10, 80);
    }
    
}
