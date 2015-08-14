( [return to main wiki page](S3Games.md) )

## A family crossing a river ##

A family gets lost and arrives to a river at night, now they need to cross it. They have only 1 flashlight, but the bridge is broken, and thus not more than two people can attempt to use the bridge. The girls do not have very good shoes, and take longer to walk over the river:

  * Mother will use 10 minutes
  * Emily will use 5 minutes
  * Father will use 2 minutes
  * Bob will use only 1 minute

  * When two go together, they will go the speed of the more slowly one.

#### Task ####
What is the shortest time the family can meet again on the other side of the river to continue walking back to the camp?

#### In implementation: ####

  * _Locations:_  4 locations on one side, 4 locations on the other side, 2 locations for persons and 1 for flashlight on the each side next to bridge
  * _Elements:_  Mother, Emily, Father, Bob, flashlight
  * _Scoring:_  negative time used (score starts at 50 after the game starts)

## Squares ##

The goal of the game is to enclose so much small squares as possible.
A player can in one move put his stick onto dotted grid, vertically or horizontally.  Players are changing after each move, which didn’t create a new small square. The game ends when the grid is full and no other move is possible. The winner is a player with a larger score.


## Nim ##

Several light matches are placed on the game board. Players are taking them and placing them into the match box in turns. Player can take only one, two or three light matches in one move. The aim of the game is not to take the last light match. Who takes the last light match is loosing the game.

In implemented solution, player selects a number (e.g. _n_) of matches to remove by clicking on the _n-th_ match from the right side.

## Frogs ##
This is one player logic game. There are green frogs on the left side and brown frogs on the right side. Frog can shift forward or jump over just one another frog to the free stone. Frogs can not go backward.

The goal of the game is to exchange the positions of green frogs and brown frogs. So problem is solved when green frogs are on the right side and brown frogs are on the left side.

## Skipping ##
There are black and white stones placed on the chessboard. This game is played only on the bright locations of the chessboard.

The goal is to get your stones to the opposite side (where opponent’s stones are situated after game starts). A player can in one move jump forward over any other stone(repeatedly with that selected stone if he has an option) or make one step forward.