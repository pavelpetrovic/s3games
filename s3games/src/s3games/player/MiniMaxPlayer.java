/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

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
        boolean isModified;
        
        public Node(Move move, Node previous, GameState state)
        {
            subNodes = new ArrayList<Node>();
            this.previous = previous;
            moveToThisState = move;    
            gs = state;            
            resetValue();
            if (gs.currentPlayer == number)
                if (previous != null)
                    previous.subNodes.add(this);
        }
        
        void resetValue()
        {
            if (gs.currentPlayer == number)            
                value = Double.NEGATIVE_INFINITY;
            else value = Double.POSITIVE_INFINITY;
            isModified = false;
        }
        
        void requestUpdate(double subNodeValue, Node subNode, int phase)
        {
            if (gs.currentPlayer == number)
            {
                if (subNodeValue > value)
                {                  
                    value = subNodeValue;
                    subNodes.clear();
                    subNodes.add(subNode);
                    if (!isModified)
                    {
                        if (phase == 1) modified.addFirst(this);
                        else modified.addLast(this);
                        isModified = true;
                    }
                }                
            }
            else 
            {
                if (subNodeValue < value)
                {
                    value = subNodeValue;
                    if (!isModified)
                    {
                        if (phase == 1) modified.addFirst(this);
                        else modified.addLast(this);
                        isModified = true;
                    }
                }
            }
        }
        
        void computeNodeValue()
        {
            if (gs.winner >= 0)
            {
               if (gs.winner == number)
                   value = 1;
               else if (gs.winner == 0)
                   value = 0;
               else value = -1;            
            }        
            else value = heuristic.heuristic(gs, number);
        }

        void evalAndPropagateUp()
        {
            computeNodeValue();            
            propagateUp(1);
        }

        void propagateUp(int phase)
        {
            if (previous != null)        
                previous.requestUpdate(value, this, phase); 
        }        
    }
    
    private boolean rootReachable(Node node)
    {
        while (node.previous != null)
            if (node.previous == root) return true;
            else node = node.previous;
        return false;
    }

    LinkedList<Node> open;
    HashSet<GameState> visited;
    LinkedList<Node> modified;
    Node root;
    
    Heuristic heuristic;
    GameSpecification specs;
    
    public MiniMaxPlayer(GameSpecification specs, Heuristic heuristic)
    {
        this.specs = specs;
        this.heuristic = heuristic;        
    }
        
    private void removeAbandonedSubtrees()
    {
        Iterator<Node> it = open.listIterator();
        while (it.hasNext())
        {
            Node node = it.next();
            if (!rootReachable(node)) it.remove();    
        }
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
        else removeAbandonedSubtrees();
                 
        modified.clear();
                
        int nodesOpened = 0;
        
        while ((nodesOpened < maxNodes) && (!open.isEmpty()))
        {
            Node actualNode = open.poll();
            nodesOpened++;
            GameState activeState = actualNode.gs;
            actualNode.resetValue();
            
            if (activeState.winner >= 0)
            {
                actualNode.evalAndPropagateUp();
                continue;
            }
            
            ArrayList<Move> possibleMoves = activeState.possibleMoves();
            for (int i=0; i<possibleMoves.size(); i++) 
            {
                Move mv = possibleMoves.get(i);
                GameState gs = activeState.getCopy();
                gs.performMove(mv);
                Node successor = new Node(mv, actualNode, gs);
                if (!visited.contains(gs)) 
                {   
                    visited.add(gs);                    
                    open.add(successor);
                    successor.evalAndPropagateUp();
                }
            }
        }
                            
        while (!modified.isEmpty())
        {
            Node node = modified.poll();         
            node.isModified = false;
            node.propagateUp(2);            
        }
        
        root.previous = null;
        Move bestMove;
        if (root.subNodes.size() > 0) 
        {
            root = root.subNodes.get(0);
            bestMove = root.moveToThisState;
        }
        else // not sure we really need this here        
            bestMove = allowedMoves.get(0);
                
        return bestMove;
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {
        for (Node node: root.subNodes)        
            if (node.moveToThisState.equals(move))
            {
                root = node;
                root.previous = null;
                return;
            }
        root = new Node(move, null, newState);
    }    
}
