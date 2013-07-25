/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

/**
 *
 * @author petrovic16
 */
public class Move
{
    public final String from;
    public final String to;
    public final String element;  
    private final String hashString;
    private final int hash;    
    
    public Move(String from, String to, String element, GameSpecification specs)
    {
        this.from = from;
        this.to = to;
        this.element = element;        
        hashString = computeHashString(specs);
        hash = hashString.hashCode();
    }
    
    private String computeHashString(GameSpecification specs)
    {
        LocationType fromType = specs.locationTypes.get(specs.locations.get(from).type);
        LocationType toType = specs.locationTypes.get(specs.locations.get(to).type);
        String fromHash = from;
        String toHash = to;
        String elementType = specs.elements.get(element).type;        
        if (!fromType.relevant) fromHash = fromType.name;
        if (!toType.relevant) toHash = toType.name;        
        StringBuilder s = new StringBuilder(fromHash.length() + toHash.length() + elementType.length() + 4);
        
        s.append(fromHash);
        s.append('*');
        s.append(toHash);
        s.append('*');
        s.append(elementType);
        return s.toString();
    }
    
    @Override
    public String toString()
    {
        return from + "->" + to + ":" + element;
    }
    
    /** return a hash code for fast hashmap access */
    @Override
    public int hashCode()
    {                
        return hash;
    }
    
    @Override
    public boolean equals(Object other)
    {        
        if (other instanceof Move)
        {            
            Move otherMove = (Move)other;
            if (hash != otherMove.hash) return false;
            if (hashString.equals(otherMove.hashString)) return true;
        }
        return false;
    }
}
