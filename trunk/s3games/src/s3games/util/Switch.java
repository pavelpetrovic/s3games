/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.util;

/**
 *
 * @author petrovic
 */
public class Switch 
{
    private boolean state;
    
    public Switch()
    {
        state = false;
    }
    
    public void on()
    {
        state = true;
    }
    
    public void off()
    {
        state = false;
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
