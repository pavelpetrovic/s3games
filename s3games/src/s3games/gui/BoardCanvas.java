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
    public GameSpecification gameSpec = null;
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
               //paint of board
               for (Map.Entry<String, Location> location: gameSpec.locations.entrySet())
               {
                    Location loc = location.getValue();
                    img = gameSpec.locationTypes.get(loc.type);
                    g.drawImage(img.image,loc.point.x-img.hotSpot.x, loc.point.y-img.hotSpot.y,this);
               }

               //paint stones
               for (Map.Entry<String, String> entry : elements.entrySet()) 
               {
                    String elementName=entry.getKey();
                    Element element = gameSpec.elements.get(elementName);
                    ElementType elType = gameSpec.elementTypes.get(element.type);                    
                    Integer actualState = egameState.basicGameState.elementStates.get(elementName);
                    img = elType.images[actualState];
                    String elementLoc = egameState.basicGameState.elementLocations.get(elementName);
                    Location loc = gameSpec.locations.get(elementLoc);
                    g.drawImage(img.image,loc.point.x - img.hotSpot.x, loc.point.y - img.hotSpot.y,this );
                }                
            }
        }
    }
}
