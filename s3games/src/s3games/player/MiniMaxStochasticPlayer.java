/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import s3games.ai.Heuristic;
import s3games.engine.GameSpecification;
import s3games.engine.GameState;
import s3games.engine.Move;

/**
 *
 * @author yann
 */
public class MiniMaxStochasticPlayer extends Player {
    public enum NodeType { MIN, MAX };
    public static double mmRatio = 0.3;
    
    public MiniMaxStochasticPlayer.Node newNode(MiniMaxStochasticPlayer.Node previous, GameState gs) throws Exception
    {
        MiniMaxStochasticPlayer.NodeType type = MiniMaxStochasticPlayer.NodeType.MIN;
        if (gs.currentPlayer == number) type = MiniMaxStochasticPlayer.NodeType.MAX;        
        
        double val;
        if (type == MiniMaxStochasticPlayer.NodeType.MIN) val = Double.POSITIVE_INFINITY;
        else val = Double.NEGATIVE_INFINITY;
        
        int depth = 1;
        if (previous != null) depth = previous.depth + 1;
        
        MiniMaxStochasticPlayer.Node node = new MiniMaxStochasticPlayer.Node(previous, val, type, depth);
        node.open(gs);
        
        return node;
    }

    public class Node implements Comparable
    {
        MiniMaxStochasticPlayer.Node previous;
        double value;
        MiniMaxStochasticPlayer.NodeType type;
        int depth;
        
        Node(MiniMaxStochasticPlayer.Node previous, double value, MiniMaxStochasticPlayer.NodeType type, int depth)
        {
            this.previous = previous;
            this.value = value;
            this.type = type;
            this.depth = depth;
        }
        
        void open(GameState state) throws Exception
        {
            if (state.winner >= 0)
                value = valueOfWinner(state.winner);
            else
            {
                HashMap<Move, GameState> newStates = expand(state, state.possibleMoves(), mmRatio); 
                if (newStates.isEmpty())
                    value = 0;
                else
                {
                    for(GameState gs: newStates.values())
                        leaves.add(new MiniMaxStochasticPlayer.Leaf(gs, this));
                    nodesOpened += newStates.size();
                    return;
                }
            }          
            if (previous != null)
                previous.update(value);
        }
        
        void update(double val)
        {
            if (type == MiniMaxStochasticPlayer.NodeType.MAX)
            {
                if (val > value)
                    value = val;
                else return;
            }
            else if (val < value)
                value = val;
            else return;
            modified.add(this);
        }

        @Override
        public int compareTo(Object o) 
        {
            MiniMaxStochasticPlayer.Node other = (MiniMaxStochasticPlayer.Node)o;
            if (depth > other.depth) return -1;
            else if (depth < other.depth) return 1;
            else return 0;
        }
    }
    
    public class Leaf
    {
        MiniMaxStochasticPlayer.Node previous;
        GameState gs;        
        
        public Leaf(GameState gs, MiniMaxStochasticPlayer.Node previous)
        {
            this.gs = gs;
            this.previous = previous;
        }
    }
    
    Heuristic heuristic;
    GameSpecification specs;
    LinkedList<MiniMaxStochasticPlayer.Leaf> leaves;
    PriorityQueue<MiniMaxStochasticPlayer.Node> modified;
    long nodesOpened;
    
    public MiniMaxStochasticPlayer(GameSpecification specs, Heuristic heuristic)
    {
        this.specs = specs;
        this.heuristic = heuristic; 
    }

    double valueOfWinner(int winner)
    {
        double val = Double.NaN;
        if (winner == number)
            val = 1;
        else if (winner == 0)
            val = 0;
        else if (winner > 0)
            val = -1;    
        return val;
    }

    public HashMap<Move, GameState> expand(GameState state, HashSet<Move> moves, double ratio) throws Exception
    {
        Random randomGenerator = new Random();
        
        HashMap<Move, GameState> expanded = null;
        expanded = new HashMap<Move, GameState>();
        
        for (Move mv: moves)
        {
            if (randomGenerator.nextDouble() < ratio) {                
                GameState newState = state.getCopy();
                newState.performMove(mv);
                expanded.put(mv, newState);
            }
        }
        
        return expanded;
    }
    
    @Override
    public Move move(GameState state, ArrayList<Move> allowedMoves) throws Exception 
    {
        startMove();
        nodesOpened = 0;
        
        HashSet<Move> possibleMoves = new HashSet<Move>(allowedMoves);
        if (possibleMoves.size() == 1) return allowedMoves.get(0);
        
        HashMap<Move, MiniMaxStochasticPlayer.Node> topMoves = new HashMap<Move, MiniMaxStochasticPlayer.Node>();
        HashMap<Move, GameState> newStates = expand(state, possibleMoves, 1.0);
        modified = new PriorityQueue<MiniMaxStochasticPlayer.Node>();
        leaves = new LinkedList<MiniMaxStochasticPlayer.Leaf>();
        
        for(Map.Entry<Move, GameState> mv: newStates.entrySet())
            topMoves.put(mv.getKey(), newNode(null, mv.getValue()));
        
//        while ((nodesOpened < maxNodes) && (!leaves.isEmpty()))
        while (ratioTimeLeft() > 0 && !leaves.isEmpty())
        {
            MiniMaxStochasticPlayer.Leaf lf = leaves.poll();
            if (lf.gs.winner >= 0)
                lf.previous.update(valueOfWinner(lf.gs.winner));
            else
                newNode(lf.previous, lf.gs);
        }
        
        while (!leaves.isEmpty())
        {
            MiniMaxStochasticPlayer.Leaf lf = leaves.poll();
            double val = valueOfWinner(lf.gs.winner);
            if (Double.isNaN(val))
                val = heuristic.heuristic(lf.gs, number);
            lf.previous.update(val);
        }
        
        while (!modified.isEmpty())
        {
            MiniMaxStochasticPlayer.Node node = modified.poll();
            if (node.previous != null) node.previous.update(node.value);
        }
        
        double max = Double.NEGATIVE_INFINITY;
        Move bestMove = null;
        for (Map.Entry<Move, MiniMaxStochasticPlayer.Node> mv: topMoves.entrySet())
        {
            double val = mv.getValue().value;
            if (val > max)
            {
                max = val;
                bestMove = mv.getKey();
            }
        }
        System.out.println("Minimax - total nodes: " + nodesOpened);                  
        return bestMove;
    }

    @Override
    public void otherMoved(Move move, GameState newState) 
    {
    }
}
