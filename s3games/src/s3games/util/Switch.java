/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.util;

import java.util.ArrayList;

/**
 *
 * @author petrovic
 */
public class Switch 
{
    private boolean state;
    
    private ArrayList<SwitchListener> listeners;
    
    public Switch()
    {
        state = false;
        listeners = new ArrayList<SwitchListener>();
    }
    
    public void addSwitchListener(SwitchListener l)
    {
        listeners.add(l);
    }
    
    private void notifyListeners()
    {
        for(SwitchListener l: listeners)
            l.switchChanged(state);
    }
            
    public void on()
    {
        if (state == true) return;
        state = true;
        notifyListeners();
    }
    
    public void off()
    {
        if (state == false) return;
        state = false;
        notifyListeners();
    }
    
    public boolean isOn()
    {
        return state;
    }
    
    public boolean isOff()
    {
        return !state;
    }
    
}
