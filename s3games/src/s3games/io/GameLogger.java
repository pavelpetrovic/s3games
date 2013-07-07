/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.io;

import java.io.*;

/**
 *
 * @author petrovic16
 */
public class GameLogger
{
    private String generalLogFile = "s3games.log";

    private void appendToLog(String fileName, String message)
    {
        try {
            PrintWriter w = new PrintWriter(new FileWriter(fileName, true));
            w.println(message);
            w.close();
        } catch (Exception e)
        {
            System.out.println("Could not write to log " + fileName);
        }
    }

    public void warning(String message)
    {
        appendToLog(generalLogFile, "warn: " + message);
    }
}
