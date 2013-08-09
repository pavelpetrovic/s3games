package s3games.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/** A general control-panel window used both by camera and robot control panel */
public abstract class ControlWindow implements ActionListener
{
    /** the window */
    JFrame win;
    
    /** the informative text area */
    JTextArea out;
    
    /** scroll bar for the text area */
    JScrollPane scroll;
        
    /** construct a new control window with the specified title */
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
    
    /** sublclasses should add their buttons to the control panel here */
    abstract protected void addButtonsToPanel(JPanel panel);

    /** appends a new line to the informative text area */
    public void addMessage(String msg)
    {
        out.append(msg);
        out.append(System.getProperty("line.separator"));        
    }
        
    /** closes this window */
    public void close()            
    {
       win.dispose();
       win = null;
    }
}
