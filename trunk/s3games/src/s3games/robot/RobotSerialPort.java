/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.robot;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort; 

import java.io.*;
import java.io.BufferedReader;

public class RobotSerialPort implements Runnable
{    
    String port;
    
    InputStream in;
    OutputStream out;
    BufferedReader reader;
    
    public RobotSerialPort(String portName)
    {
        port = portName;
    }
    
    public void open() throws Exception 
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        if (portIdentifier.isCurrentlyOwned())
            throw new Exception("RobotSerialPort: " + port + " is currently in use");
        
        CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
        if (commPort instanceof SerialPort) 
        {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.FLOWCONTROL_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();                
                if (in == null) throw new Exception("RobotSerialPort: cannot obtain input stream");
                if (out == null) throw new Exception("RobotSerialPort: cannot obtain output stream");
                new Thread(this).start();                
        } else throw new Exception("RobotSerialPort: " + port + " is not a serial port.");
    }
    
    public void close() 
    {
        try 
        {
            in.close();
            out.close();
        } catch (Exception e) {}
    }

    @Override
    public void run () 
    {
        System.out.println("reading serial");
        char line;/*
        try{
           while (true) {
               System.out.print( (char)in.read());
           }
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }            */
    }

    void print(String s) throws IOException
    {
        out.write(s.getBytes());
        out.write(0x0d);
        out.flush();
    }
    
    int read() throws IOException
    {
        int ch = -1;
        while ((ch == -1) || (ch == 255)) ch = in.read();
        return ch;
    }
}