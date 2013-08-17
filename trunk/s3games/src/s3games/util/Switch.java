package s3games.util;

import java.util.ArrayList;

/** A switch is used to hold a logical value on/off, and notify all its registered
 * listeners whenever it changes its value */
public class Switch 
{
    /** the current state of the switch */
    private boolean state;
    
    /** the current user value of the switch */
    private int value;
    
    /** the listeners who are registered to receive the switch events */
    private ArrayList<SwitchListener> listeners;
    
    /** construct a new switch in off state */
    public Switch()
    {
        state = false;
        listeners = new ArrayList<SwitchListener>();
    }
    
    /** add a new listener to this switch */
    public void addSwitchListener(SwitchListener l)
    {
        listeners.add(l);
    }
    
    /** notify listeners after switch has changed its value */
    private void notifyListeners()
    {
        for(SwitchListener l: listeners)
            l.switchChanged(state);
    }
            
    /** set the switch to on position */
    public void on()
    {
        if (state == true) return;
        state = true;
        notifyListeners();
    }
    
    /** set the switch to the off position */
    public void off()
    {
        if (state == false) return;
        state = false;
        notifyListeners();
    }
    
    /** determine if the switch is in on position */
    public boolean isOn()
    {
        return state;
    }
    
    /** determine if the switch is in off position */
    public boolean isOff()
    {
        return !state;
    }
    
    /** set new user value of this switch */
    public void setValue(int newValue)
    {
       value = newValue; 
    }
    
    /** retrieve the user value of this switch */
    public int getValue()
    {
        return value;
    }    
}
