/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author petrovic
 */
public abstract class ControlWindow implements ActionListener
{
    JFrame win;
    JTextArea out;
    JScrollPane scroll;
        
    public ControlWindow(String title)
    {
        win = new JFrame(title);
        win.setLayout(new BorderLayout());
        out = new JTextArea(15, 60);
        out.setEditable(false);
        out.setAutoscrolls(true);
        DefaultCaret caret = (DefaultCaret)out.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scroll = new JScrollPane (out);        
        win.add(scroll, BorderLayout.CENTER);
        JPanel panel = new JPanel();        
        panel.setLayout(new FlowLayout());        
        addButtonsToPanel(panel);
        win.add(panel, BorderLayout.SOUTH);                
        win.pack();
        win.setVisible(true);
    }
    
    abstract protected void addButtonsToPanel(JPanel panel);

    public void addMessage(String msg)
    {
        out.append(msg);
        out.append(System.getProperty("line.separator"));        
    }
        
    public void close()
    {
       win.dispose();
       win = null;
    }
}
