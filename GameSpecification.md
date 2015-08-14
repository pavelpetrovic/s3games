## Description of specification of game format ##

( [return to main wiki page](S3Games.md) )

The game is specified by:

  * image of the playing field (board)
<font color='brown'><pre>
[BOARD]<br>
image=frogsbackground.png<br>
</pre></font>

  * realboard element types
> element type name, state, hueMin, hueMax (0-360), saturationMin, saturationMax (0-1), valueMin, valueMax (0-1), sizeMin, sizeMax (in pixels)
<font color='brown'><pre>
[REALBOARD ELEMENT TYPES]<br>
name=greenFrog<br>
state=1<br>
hueMin=71<br>
hueMax=192<br>
saturationMin=0.27<br>
saturationMax=1.0<br>
valueMin=0.09<br>
valueMax=0.9<br>
sizeMin=200<br>
sizeMax=5000<br>
</pre></font>

  * movable elements types
> type name, number of states for this type, images for all states,	reference points for images of all states
<font color='brown'><pre>
[ELEMENT TYPES]<br>
name=greenFrog<br>
states=1<br>
image=greenFrog.png<br>
point=1,1<br>
</pre>
<pre>
name=anotherElementType<br>
states=2<br>
image=imageForFirstState.png<br>
point=10,10<br>
image=imageForSecondState.png<br>
point=5,-3<br>
</pre></font>

  * location types
> type name, image, reference point,shape (shape=rectangle/circle and size=a,b/radius)

<font color='brown'><pre>
[LOCATION TYPES]<br>
name=stone<br>
image=stone.png<br>
shape=circle(30)<br>
point=1,1<br>
</pre></font>

  * locations
> name (+ optionally one or two indexes, e.g. "place(1)", "place(2,3)" ),	type, coordinates in window, coordinates on the playing field, robot arm servos angles to reach the location on real board (5 angles above, 5 at the location)

<font color='brown'><pre>
[LOCATIONS]<br>
name=s(1)<br>
type=stone<br>
point=40,250<br>
camera=20,135<br>
robot=10,20,30,40,50,10,20,30,40,50<br>
</pre></font>

  * players names
<font color='brown'><pre>
[PLAYER NAMES]<br>
1=p1<br>
</pre></font>

  * movable elements
> name (+ optional index), type, initial player number, initial location name, initial state (optional), initial z-index (optional)

<font color='brown'><pre>
[MOVABLE ELEMENTS]<br>
name=gf(1)<br>
type=greenFrog<br>
player=1<br>
location=s(1)<br>
</pre>
</font>

  * expressions - consisting of name, optional arguments, and multiple lines
> > if a line evaluates to false, the evaluation stops, and the result is false, otherwise, the result of the last line is the result of the expression


> syntax:
<pre>
// comment<br>
// header:<br>
EXPRESSION<br>
// alternate:<br>
EXPRESSION($ARG1, …, $ARGN)<br>
// end of expression:<br>
END<br>
// white space within the line is ignored, parentheses can be used arbitrarily<br>
// string, integer, set, logic<br>
value1 == value2        // result: logic value<br>
value2 != value2	// result: logic value<br>
constant		// result: value of that type: "hello", 3, {1,2,”hello”}, true/false, “”, {}<br>
// integer<br>
value1 < value1	// result: logic value<br>
value1 <= value1	// result: logic value<br>
value1 > value2	// result: logic value<br>
value1 >= value2	// result: logic value<br>
value1 + value2	// result: int value<br>
value1 - value2	// result: int value<br>
value1 * value2	// result: int value<br>
value1 / value2	// result: int value<br>
value1 % value2	// result: int value<br>
ABS value		// result: int value<br>
// set<br>
set1 ISPARTOF set2	// subset, result: logic value<br>
value IN set		// member, result: logic value<br>
set1 EXCEPTOF set2	// set difference, result: set<br>
set1 UNION set2		// set union, result: set<br>
set1 INTERSECT set2	// set intersection, result: set<br>
// logic<br>
value1 AND value2	// result: logic<br>
value1 OR value2	// result: logic<br>
NOT value		// result: logic<br>
IF(value1, value2, value3)   // result: value2 or value3<br>
// same as<br>
// IF value1 THEN value2 ELSE value3<br>
FORALL($VAR,valueFrom,valueTo,value)    // value is true for all values, result: logic<br>
// same as<br>
// FORALL $VAR=valueFrom TO valueTo value<br>
FORSOME($VAR,valueFrom,valueTo,value)   // value is true at least one time, result: logic<br>
// calling other expressions:<br>
EXPRESSION3	// result: value of evaluating the expression<br>
EXPRESSION5(3,5)	// result: value of evaluating the expression<br>
// built-in operators<br>
LOCTYPE(location)	// type of location, result: string / “”<br>
ELTYPE(element)	// type of movable element, result: string / “”<br>
STATE(element)	// state of movable element, result: number / -1<br>
LOCATION(element)	// current location of movable element, result: string / “”<br>
CONTENT(location)	// name of element placed on specified location, result: string/””<br>
EMTPY(location)	// true, if location with no element on it  / false if not location<br>
INDEX(string)		// obtain first index in “name(123)”, result: integer<br>
INDEXA(i, string)	// obtain i-th (1,2,..) index in “name(1,2)”, result:  integer<br>
UNINDEX(string)	// obtain name without indexes, e.g. “name(2,3)” => “name”<br>
OWNER(element)	// current owner of the element, result: string / -1<br>
PLAYER		// player on the move, result: number 1..<br>
SCORE(player)	// current score of some player, result: integer<br>
// only for visualization purposes<br>
ZINDEX(element)	// current ZINDEX of the specified element, result: intenger<br>
//variables (they are all global)<br>
$VAR = value		// assignment, result: true<br>
$VAR			// value, result: value of the variable<br>
"name($VAR)"		// using variable as index, also<br>
"name($VAR1,$VAR2)"<br>
<br>
// side effects (use only if needed)<br>
MOVE(element, location1, location2)   // result: true or false - if occupied, or not present<br>
SETOWNER(element, player)  // result: true<br>
SETSTATE(element, state) // result: true<br>
SETZINDEX(element, value)	// result: true<br>
NEXTPLAYER<br>
</pre>
<font color='brown'><pre>
[EXPRESSIONS]<br>
FrogsAtHome<br>
FORALL($J,1,3,ELTYPE(CONTENT("s($J)")) == "redFrog")<br>
FORALL($J,5,7,ELTYPE(CONTENT("s($J)")) == "greenFrog")<br>
END<br>
</pre></font>

  * scorings

expression, scores to add/subtract for any specified players (player number, score) - can be expressions

<font color='brown'><pre>
[SCORINGS]<br>
<br>
situation=true<br>
player=PLAYER<br>
score=(PLAYER*2)<br>
</pre></font>

  * end of game conditions
expression, winning player number - can be expression

<font color='brown'><pre>
[END OF GAME]<br>
situation=FrogsAtHome<br>
winner=1<br>
</pre></font>

  * game rules
> rule name (can have one index),	element to move (can contain var in index) - unbound vars are bound to values, state of element  (can be var) (optional), current player (optional, if not specified anybody can do that, can be var), location from (can contain vars in indexes) - can contain unbound variables, location to (can contain vars in indexes) - can contain unbound variables, expression that must evaluate to true to allow this move, scores to add/subtract for all players (playerNumber, score) (can be expressions), resulting action - expression to invoke automatically (optional)

<font color='brown'><pre>
[GAME RULES]<br>
name=greenStep<br>
element=gf($J)<br>
from=s($K)<br>
to=s($L)<br>
condition=$L==$K+1<br>
</pre>
<pre>
name=greenJump<br>
element=gf($J)<br>
from=s($K)<br>
to=s($L)<br>
condition=StoneOccupied($K+1) AND ($L==$K+2)<br>
</pre></font>

adding score to player and resulting action example (from _River Crossing_)

<font color='brown'><pre>
[GAME RULES]<br>
name=toRight<br>
element=lg<br>
from=lightLeft<br>
to=lightRight<br>
condition=CheckStackL<br>
awardPlayer=1<br>
withScore=Min(timeOf("leftstack(2)"),timeOf("leftstack(1)"))<br>
followup=crossToRight<br>
</pre></font>