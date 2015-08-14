## Information relevant for the S3 Games project ##

( [return to main wiki page](S3Games.md) )

opening presentation:
https://s3games.googlecode.com/svn/wiki/s3openingpresentation.ppt

group work pictures:
http://kempelen.ii.fmph.uniba.sk/S3GroupWork/

**Artificial intelligence (AI)** is a field of computer science that studies and develops intelligent machines and software. Central goals of AI research include reasoning, planning,  learning, knowledge,  problem solving

Aims are:  to describe human’s intelligence that can be simulated by a machine, create a system that acts logically/reasonably.

**Environment** -  Area in where agent operates and takes his actions. It is an abstraction over  the real world. Environment is simplified according to the problem, and doesn’t  contain irrelevant information.
-	can be fully observable or partially observable  for agent (accessible or inaccessible). In games we work with fully observable environment.

**Agent** (robot) – system which perceives its environment and takes the action that maximizes its success rate.  Agent has preceptors (sensors) to see his surrounding and  effectors (actuators) to impact on the environment.  Actuators – set of devices/functions that the agent can use to perform actions.

**Action** – one move/step which agent decides to perform and has effect to the environment

**State** – it is actual configuration of an environment – in our case a game. It is implied by a previous state and by an action which was performed as the last move.

**State space** – it is a set of all possible game states
Initial state – first starting state, in which agent is placed, also initial configuration.

**Final state** – state (or configuration) that satisfies given goals (in our case winning a game)

**State neighborhood** – a function that returns a set of neighboring states – one for each possible move

**Perception** – what the agent can see at the moment. It can be represented by a data vector.

**Perception size** – is the part of state, usually agent’s surroundings, which agent is able to see. In games the environment is fully observable, so agent sees the whole game state / configuration.

**Reasoning agent** – is planning his steps to accomplish goal of his task or maximize performance measure.

Game can contain multiple of agents or one agent (so its environment is single-agent or multi-agent), which impact on the environment. Agents can cooperate or they can be competitive ().
Solution -> Sequence of actions – all actions from initial state to final state – path to final state

**Goal Test** – a function which test if actual state is a finish/goal state

**Utility/evaluating  function** – maps a state onto a real number. It evaluates a quality of actual state, also called Heuristic. Another use of evaluation function: it can evaluate an agent itself – how good it is at playing a game.

Searching the state space:

agent searches the state space for the solution, the goal is to find path from the current state to the goal state. It has a list of states that were reached and need to be considered further (open), list of states that it already saw (visited). It always takes and removes one state from open list, and expands it – i.e. retrieves the set of states that are directly reachable from the expanded state. These are then added to the list of open states for later investigation. As soon as a final state is found, the path from the current state to the goal state is reconstructed. To be able to do that, we store a reference to the previous state and the move that was taken to arrive to the current state.

Depending on the ordering – i.e. how do we add and how we remove the nodes to/from open list, we get different flavors of search:

**Breadth-first search**:  adding nodes to the END of open, retrieving nodes from the BEGINNING. This kind of structure is FIFO – first in first out, also called a queue. This type of search is searching in layers – first we search all nodes in the distance 1, then all nodes in distance 2, then all nodes in distance 3, etc. BFS will always find the shortest path to the goal state (i.e. path with the minimum number of moves).

**Depth-first search**: adding nodes to the END of open, retrieving nodes from the END. This kind of structure is LIFO – last in first out (also called STACK – similar to a stack of plates in the school canteens). This kind of search is searching as much to the depth as possible until no new (unvisited) state can be found, and then continues further with all states that remain in the stack, eventually searching through the whole state space. DFS will find a path to the goal state that is not necessarily the shortest.

**AStar search**: the nodes are stored in priority queue, i.e. when retrieving the next node, we always take the node with the highest priority (= lowest number). The priority depends on the function

> f(state) = h(state) + g(state)

where h(state) is a heuristic evaluation of the state of that node, i.e. the expected distance to the goal state, however it must comply with the restriction h(state) <= real distance to the goal state, h(state) > 0. And g(state) is the distance from the root state, i.e. the number of moves we would have to make from the state where we started searching. In this way, the algorithm will always find the optimal (shortest) path to the goal state, and depending on the quality of the heuristic,  it is likely to search much smaller space than the BFS. If heuristic always returns the same number, it is equivalent to BFS.

An alternative way to select move in a state is to avoid searching the state space completely, and just select one of the possible moves. This can either be a random move (resulting in completely random player), or a greedy algorithm that uses a heuristic and always selects the move with the best heuristic value.

In games for two or more players, it is not sufficient to search the state space. We need to take into   account that each it is the oponent ‘s turn, he or she can take any of the possible moves, and we cannot influence it. Therefore we cannot plan the complete path to the goal state, the opponent is not obliged to follow it!

**Minimax Search**

Minimax is a decision rule for minimizing the possible loss for a worst case (maximum loss) scenario. A minimax algorithm is a recursive algorithm for choosing the next move in an n-player game, usually a two player game.

In two player game we have player called MAX (current player who is choosing his next move) and player called MIN.  Player MAX is maximizing his score but opposite player MIN is minimizing MAX’s score.
A value is associated with each position of the game. It indicates how good it would be for a player to reach that position. MAX will choose the option in which he obtains a maximum possible score but in MIN’s move he will choose the option with the minimum possible score.

![https://s3games.googlecode.com/svn/wiki/minimax.png](https://s3games.googlecode.com/svn/wiki/minimax.png)

This can be extended if we can supply a heuristic evaluation function which gives values to non-final game states without considering all possible following complete sequences. We can then limit the minimax algorithm to look only at a certain number of moves ahead.

Minimax with **Alfa-beta prunning**

http://www.cs.ucla.edu/~rosen/161/notes/alphabeta.html

**Color representation/models**

http://en.wikipedia.org/wiki/Color_vision <br />
http://en.wikipedia.org/wiki/RGB_color_model <br />
https://en.wikipedia.org/wiki/HSL_and_HSV

Time and space complexity

Deterministic and stochastic algorithms

Deterministic = result is always the same (finds the best solution, for instance)

Stochastic = results is different each time you run it, because it is based on random search (although it can search in a smart way)

Example of stochastic algoritm:

calculating the sum of the area:

![https://s3games.googlecode.com/svn/wiki/montecarlo.png](https://s3games.googlecode.com/svn/wiki/montecarlo.png)