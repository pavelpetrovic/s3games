package s3games.util;

/** Listeners to switch class should implement this interface. */
public interface SwitchListener 
{
    /** the state of the switch has changed */
    public void switchChanged(boolean newState);    
}
