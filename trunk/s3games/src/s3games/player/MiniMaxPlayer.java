/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
import java.util.*;
import s3games.ai.*;
import s3games.engine.*;

/**
 *
 * @author petrovic
 */
public class MiniMaxPlayer extends Player
{
    class Node
    {
        GameState gs;
        double value;
        ArrayList<Node> subNodes;
        Node previous;
        Move moveToThisState;
        
        public Node(Move move, Node previous, GameState state)
        {
            subNodes = new ArrayList<Node>();
            this.previous = previous;
            moveToThisState = move;    
            gs = state;
            if (state.currentPlayer == number)
            {
                value = Double.MIN_VALUE;
                if (previous != null)
                    previous.subNodes.add(this);
            }
            else value = Double.MAX_VALUE;
        }
        
        
        void requestUpdate(double subNodeValue, Node subNode)
        {
            if (gs.currentPlayer == whoAmI)
            {
                if (subNodeValue > value)
                {                  
                    value = subNodeValue;
                    subNodes.clear();
                    subNodes.add(subNode);
                    modified.add(this);
                }                
            }
            else 
            {
                if (subNodeValue < value)                
                {
                    value = subNodeValue;                    
                    modified.add(this);
                }
            }                
        }
    }

    LinkedList<Node> open;
    HashSet<GameState> visited;
    LinkedList<Node> modified;
    Node root;
    
    Heuristic heuristic;
    GameSpecification specs;
    
    int whoAmI;
    
    public MiniMaxPlayer(GameSpecification specs, Heuristic heuristic)
    {
        this.specs = specs;
        this.heuristic = heuristic;        
    }
    
    void propagateUp(Node node)
    {
        if (node.gs.winner >= 0)
        {
           if (node.gs.winner == whoAmI)
               node.value = 1;
           else if (node.gs.winner == 0)
               node.value = 0;
           else node.value = -1;            
        }        
        else node.value = heuristic.heuristic(node.gs);
        
        Node previous = node.previous;
        if (previous != null)        
            previous.requestUpdate(node.value, node);        
    }
        
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {        
        if (visited == null)
        {
            visited = new HashSet<GameState>();
            open = new LinkedList<Node>();
            root = new Node(null, null, state);
            open.add(root);
            modified = new LinkedList<Node>();
        }
        else
        {
            
        }
        
        whoAmI = state.currentPlayer; 
        modified.clear();
                
        int nodesOpened = 0;
        while ((nodesOpened < maxNodes) && (!open.isEmpty()))
        {
            Node actualNode = open.poll();
            nodesOpened++;
            GameState activeState = actualNode.gs;
            
            if (activeState.winner >= 0)
            {
                propagateUp(actualNode);
                continue;
            }
                
            ArrayList<Move> possibleMoves = activeState.possibleMoves();
            for (int i=0; i<possibleMoves.size(); i++) 
            {
                Move mv = possibleMoves.get(i);
                GameState gs = activeState.getCopy();
                gs.performMove(mv);
                if (!visited.contains(gs)) 
                {   
                    visited.add(gs);    
                    open.add(new Node(mv, actualNode, gs));                    
                }
            }
        }
                
        Iterator<Node> it = open.descendingIterator();
        while (it.hasNext())
        {
            Node node = it.next();
            propagateUp(node);
        }
            
        while (!modified.isEmpty())
        {
            Node node = modified.poll();
            propagateUp(node);            
        }       
        
        root = root.subNodes.get(0);
        return root.moveToThisState;
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {
        for (Node node: root.subNodes)        
            if (node.moveToThisState.equals(move))
            {
                root = node;
                return;
            }
    }
    
}
