/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.util.*;
import s3games.engine.Element;
import s3games.engine.ElementType;
import s3games.engine.ExtendedGameState;
import s3games.engine.GameSpecification;
import s3games.engine.Location;

/**
 *
 * @author Boris
 */
public class BoardCanvas extends Canvas {
    GameSpecification gameSpec = null;
    ExtendedGameState egameState = null;
    Image bgImage;
    ImageWithHotSpot img;
    
    public BoardCanvas() {
      
    }
    
    public void setGame(GameSpecification gs) {
        gameSpec = gs;
        bgImage = Toolkit.getDefaultToolkit().getImage(gs.boardBackgroundFileName);
        egameState = null;
    }
        
    public void setState(ExtendedGameState egs) {
        egameState = egs;
        
    }
    
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());  //clear window
        if (gameSpec!=null) {
            g.drawImage(bgImage,0,0,this.getWidth(),this.getHeight(),this); //background
            if (egameState!=null) {
               Map<String,String> elements = egameState.basicGameState.elementLocations;
               for (Map.Entry<String, String> entry : elements.entrySet()) {
                    String elementName=entry.getKey();
                    String actualLocName = entry.getValue(); //location name where it is currently placed
               //     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    
                    //first - paint location because it represents a part of gameboard
                    Location loc = gameSpec.locations.get(actualLocName);
                    img = gameSpec.locationTypes.get(loc.name.fullName);
                    g.drawImage(img.image,loc.point.x-img.hotSpot.x, loc.point.y-img.hotSpot.y,this);

                    //second - paint stone
                    ElementType elType = gameSpec.elementTypes.get(elementName);
                    Integer actualState = egameState.basicGameState.elementStates.get(elementName);
                    img = elType.images[actualState];
                    g.drawImage(img.image,loc.point.x - img.hotSpot.x, loc.point.y - img.hotSpot.y,this );
                }   
               
               //print outputs, listings... - mozem to dat aj k setState bud priamo do window alebo tu do funkcie kde poslem hned aj panel
               int currentPlayer = egameState.basicGameState.currentPlayer;
               g.drawString("On move player " + currentPlayer+" "+gameSpec.playerNames[currentPlayer],10,10);
               
            }
        }
    }
}
