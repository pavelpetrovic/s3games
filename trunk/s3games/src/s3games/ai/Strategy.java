package s3games.ai;

import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.player.Player;

/** An abstract class for all strategies, lists all available strategies and contains a constructor method for all strategy types. */
public abstract class Strategy
{
    /** if the strategy needs a heuristic, it is stored here */
    protected Heuristic heuristic;
    
    /** default constructor uses zero heuristic */
    public Strategy()
    {
        heuristic = new ZeroHeuristic();
    }
    
    /** constructor method for the player of the respective strategy subclass */
    public abstract Player getPlayer(GameSpecification specs);
    
    /** learn a strategy for the specified game */
    public abstract void learn(Game game);

    /** list of all strategies that can be used with the specified game - place for improvement...? */
    public static String[] availableStrategies(String gameName)
    {
        return new String[] {"Random","DFS","BFS","AStar", "MiniMax", "MonteCarlo", "MonteCarloRatio", "MonteCarloRatio2", "MiniMaxStochastic"};
    }

    /** determines if the specified strategy is learnable - place for improvement... */
    public static boolean learnable(String strategyName)
    {
        return true;
    }
        
    /** set a heuristic that this strategy should be using */
    public void setHeuristic(Heuristic heuristic)
    {
        this.heuristic = heuristic;
    }
    
    /** get a strategy instance of the specified name */
    public static Strategy getStrategy(String name, Heuristic h)
    {
        if (name.equals("Random"))
            return new RandomGeneralStrategy();
        if (name.equals("DFS"))
            return new DepthFirstSearchStrategy();
        if (name.equals("BFS"))
            return new BreadthFirstSearchStrategy();
        if (name.equals("MiniMax"))
            return new MiniMaxStrategy(h);
        if (name.equals("AStar"))
            return new AStarStrategy(h);
        if (name.equals("MonteCarlo"))
            return new MonteCarloStrategy();
        if (name.equals("MonteCarloRatio"))
            return new MonteCarloRatioStrategy();
        if (name.equals("MonteCarloRatio2"))
            return new MonteCarloRatioStrategy2();
        if (name.equals("MiniMaxStochastic"))
            return new MiniMaxStochasticStrategy(h);

        return null;
    }
}
