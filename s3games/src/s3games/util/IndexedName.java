package s3games.util;

/** Represents a string of a form "WHATEVER(a,b,..z)", where a..z are numbers */
public class IndexedName
{
    /** the complete string, i.e. "WHATEVER(1,2,3)" */
    public String fullName;
    /** the base string, i.e. "WHATEVER" */
    public String baseName;
    /** the indexes, i.e. {1,2,3} */
    public int[] index;

    /** construct the indexed name from the string provided */
    public IndexedName(String name)
    {
        fullName = name;
        if (name.indexOf('(') < 0)
        {
            baseName = name;
            index = new int[0];
        }
        else
        {
            String[] nm = name.split("\\(", 2);
            baseName = nm[0];
            String[] args = nm[1].substring(0, nm[1].length() - 1).split(",");
            index = new int[args.length];
            for (int i = 0; i < index.length; i++)
                index[i] = Integer.parseInt(args[i].trim());
        }
    }
}
