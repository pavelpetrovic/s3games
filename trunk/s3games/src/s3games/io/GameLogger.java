package s3games.io;

import java.io.*;

/** Logger is used to save information about the game progress to a file */
public class GameLogger
{
    /** the name of the log file */
    private String generalLogFile = "s3games.log";

    /** append the specified message to the specified  log file */
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

    /** append a warning to the log file */
    public void warning(String message)
    {
        appendToLog(generalLogFile, "warn: " + message);
    }

    /** append an error message to the log file */
    public void error(String message)
    {
        appendToLog(generalLogFile, "err: " + message);
    }
}
