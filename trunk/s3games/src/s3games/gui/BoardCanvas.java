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
    
    String selectedElementName = null;
    
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
                    int xCorner = loc.point.x-img.hotSpot.x-img.image.getWidth(this)/2;
                    int yCorner = loc.point.y-img.hotSpot.y-img.image.getHeight(this)/2;
                    g.drawImage(img.image,xCorner, yCorner,this);   //drawing from upper left corner, not center => offset according to widht and height and hotspot
               }
               
               //paint stones
               for (Map.Entry<String, String> entry : elements.entrySet()) 
               {
                    String elementName=entry.getKey();                    
                    Element element = gameSpec.elements.get(elementName);
                    ElementType elType = gameSpec.elementTypes.get(element.type);                    
                    Integer actualState = egameState.basicGameState.elementStates.get(elementName);
                    img = elType.images[actualState];
                    String elementLoc = entry.getValue();
                    Location loc = gameSpec.locations.get(elementLoc);
                    int xCorner = loc.point.x-img.hotSpot.x-img.image.getWidth(this)/2;
                    int yCorner = loc.point.y-img.hotSpot.y-img.image.getHeight(this)/2;
                    g.drawImage(img.image,xCorner,yCorner,this );
                }                
            }
            highlightSelected(g);
        }
    }
    
    private void highlightSelected(Graphics g) {
        if (selectedElementName != null) {
            String elementLoc = egameState.basicGameState.elementLocations.get(selectedElementName);
            gameSpec.locations.get(elementLoc).shape.paintShape(g);
        }
    }
    
    public void setSelectedElement(String name) {
        selectedElementName = name;
        this.repaint();
    }
}
