package s3games.util;

/** Represents a string of the form WHATEVER(a,b,...,z) where a..z are strings */
public class NameWithArgs
{
    /** full string, e.g. "WHATEVER(car,baloon,3)" */
    public String fullName;
    /** the base string, e.g. "WHATEVER" */
    public String baseName;
    /** the arguments, e.g. {"car", "baloon", "3"} */
    public String[] args;

    /** construct the string with arguments from the specified string */
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
                    throw new Exception("argument names should start with $ (" + name + ")");
                
                args[i] = args[i].substring(1);
            }
        }
    }
}