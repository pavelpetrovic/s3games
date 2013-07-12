/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.util;

/**
 *
 * @author petrovic16
 */
public class NameWithArgs
{
    public String fullName;
    public String baseName;
    public String[] args;

    public NameWithArgs(String name) throws Exception
    {
        fullName = name;
        if (name.indexOf('(') < 0)
        {
            baseName = name;
            args = new String[0];
        }
        else
        {
            String[] nm = name.split("\\(", 2);
            baseName = nm[0];
            args = nm[1].substring(0, nm[1].length() - 1).split(",");
            for (int i = 0; i < args.length; i++)
            {
                args[i] = args[i].trim();
                if (args[i].charAt(0) != '$')
                {
                    System.out.println(name);                
                    throw new Exception("argument names should start with $");
                }
                args[i] = args[i].substring(1);
            }
        }
    }

}