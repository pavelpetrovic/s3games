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
public class RobotControlWindow extends JFrame implements Runnable
{      
    final Robot robot;
    
    private final char keys[] = { 'q', 'a', 'w', 's', 'e', 'd', 'r', 'f', 't', 'g' };
        
    private char key;
    
    private boolean terminate;
    
    double[] position;
    
    public void respondToKey()
    {
        for (int i = 0; i < keys.length; i++)
           if (key == keys[i])
            {
                int degreeIndex = i / 2;
                int direction = ((i % 2) << 1) - 1;
                position[degreeIndex] += direction;
                repaint();
                try { robot.goToDirect(position); }
                catch (Exception e) {}                        
            }
    }
    
    public void setPosition(double[] pos)
    {
        position = pos;
    }
    
    public RobotControlWindow(Robot robotReference)
    {
        robot = robotReference;
        terminate = false;
        setTitle("Robot Control");     
        position = new double[5];
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                key = evt.getKeyChar();                
                if (key == 'Q') terminate = true;
            }
        });
        setVisible(true);
        setSize(400,200);
        key = ' ';
        new Thread(this).start();
    }
    
    public void run()
    {
        while(!terminate)
        {
            if (key != ' ') 
            {
                respondToKey();
                key = ' ';
                try { Thread.sleep(250); } catch (Exception e) {}
            }
        }        
        dispose();          
    }
    
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.magenta);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 5; i++)
            s.append(" " + position[i]);        
        g.drawString("Angles: " + s.toString(), 10, 80);
    }
    
}
