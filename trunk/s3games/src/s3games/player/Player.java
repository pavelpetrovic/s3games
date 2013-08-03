/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.player;

import java.util.*;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author petrovic16
 */
public abstract class Player
{
    protected int number;
    protected long maxNodes;
    protected long maxCacheSize;
    
    public enum playerType { HUMAN, COMPUTER };
    public enum boardType { REALWORLD, SIMULATED };

    public abstract Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception;
    public abstract void otherMoved(Move move, GameState newState);
    
    //all time is in milliseconds
    private long startTime;
    private long maxTime;
    
    public void startMove() {
        startTime = new Date().getTime();
    }
    
    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }
    
    public long timeUsed() {
        long res = new Date().getTime() - startTime;
        return res;
    }
    
    public double ratioTimeLeft() {        
        double res = 1 - (double) timeUsed() / (double) maxTime;
        return res;
    }
        
    public boolean isComputer()
    {
        return true;
    }
    
    public void setPlayerNumber(int pn) 
    {
        number =  pn;
    }
    
    public void gameOver()
    {
    }
            
    public void setMaximumNumberOfNodes(long maxNodes)
    {
        this.maxNodes = maxNodes;
    }
    
    public void setMaximumCacheSize(long maxCache)
    {
        this.maxCacheSize = maxCache;
    }
}
