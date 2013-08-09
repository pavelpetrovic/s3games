package s3games.player;

import java.util.*;
import s3games.engine.GameState;
import s3games.engine.Move;

/** A general super-class for all player types. They should all provide move() method. */
public abstract class Player
{
    /** the player number (1,2,...) */
    protected int number;
    /** how many nodes at most are allowed to be opened */
    protected long maxNodes;
    
    /** the types of players */
    public enum playerType { HUMAN, COMPUTER };
    /** the type of game board where the game can be played */
    public enum boardType { REALWORLD, SIMULATED };

    /** make a single move */
    public abstract Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception;
    
    /** information about the move made by another player - override if needed */
    public void otherMoved(Move move, GameState newState)
    {
    }
    
    /** recording the time of this move - starting time */
    private long startTime;
    /** recording the time of this move - maximum time */
    private long maxTime;
    
    /** record the time when the move is starting */
    public void startMove() 
    {
        startTime = new Date().getTime();
    }
    
    /** configure the maximum time available for one move */
    public void setMaxTime(long maxTime) 
    {
        this.maxTime = maxTime;
    }
    
    /** how much time has been used since the start of this move */
    public long timeUsed() 
    {
        long res = new Date().getTime() - startTime;
        return res;
    }
    
    /** what is the ratio of the remaining time, i.e. 0.2 if only 1/5th time is remaining */
    public double ratioTimeLeft() 
    {
        double res = 1 - (double) timeUsed() / (double) maxTime;
        return res;
    }
        
    /** those players that are not computer players should override this */
    public boolean isComputer()
    {
        return true;
    }
    
    /** store the player number */
    public void setPlayerNumber(int pn) 
    {
        number =  pn;
    }

    /** the number of nodes that can be opened in one move */
    public void setMaximumNumberOfNodes(long maxNodes)
    {
        this.maxNodes = maxNodes;
    }    
}
