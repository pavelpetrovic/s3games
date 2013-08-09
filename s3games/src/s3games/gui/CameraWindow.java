package s3games.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import s3games.robot.Camera;

/** A simple control window for letting the user control when to snap the 
 * current game state on the real board using the camera. */
public class CameraWindow extends ControlWindow implements ActionListener
{
    /** reference to the camera interface object */
    private Camera camera;
    
    /** the SNAP button */
    private JButton goButton;
    
    /** clear the contents of the informational text area */
    private JButton clearButton;
    
    /** indicates if we are waiting for the human to make a move on the real board */
    private boolean ourTurn;
    
    /** construct and show the camera control window */
    public CameraWindow(Camera camera)
    {
        super("Camera control panel");
        this.camera = camera;
        ourTurn = false;
    }
    
    /** configure the buttons on the panel of the window - camera panel 
     * is simple containing only the SNAP and CLEAR actions */
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
       
    /** request the camera snapping to be allowed or not */
    public void moving(boolean b)
    {
        ourTurn = b;
        goButton.setEnabled(ourTurn);
    }
    
    /** buttons action listener */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == goButton)
            camera.requestObjectsFromCamera();        
        else if (e.getSource() == clearButton)
            out.setText("");
    }
    
    /** closing camera control panel window */
    @Override
    public void close()
    {
        super.close();
        camera = null;
    }
}
