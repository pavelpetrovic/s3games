/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import s3games.engine.Element;
import s3games.engine.ElementType;
import s3games.engine.GameState;
import s3games.engine.GameSpecification;
import s3games.engine.Location;

/**
 *
 * @author Boris
 */
public class BoardCanvas extends Canvas {
    public GameSpecification gameSpec = null;
    GameState egameState = null;
    Image bgImage;
    ImageWithHotSpot img;
    
    private BufferedImage buffImg;
    
    String selectedElementName = null;
    
    public BoardCanvas() {
      
    }
    
    public void setGame(GameSpecification gs) {
        gameSpec = gs;
        bgImage = Toolkit.getDefaultToolkit().getImage(gs.boardBackgroundFileName);
        egameState = null;
        buffImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }
        
    public void setState(GameState egs) {
        egameState = egs;    
    }
     
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g) {
        if (gameSpec!=null) {
            Graphics bg = buffImg.getGraphics();
            bg.clearRect(0, 0, this.getWidth(), this.getHeight());  //clear window
            bg.drawImage(bgImage,0,0,this.getWidth(),this.getHeight(),this); //background
            if (egameState!=null) {
               Map<String,String> elements = egameState.elementLocations;
               //paint of board
               for (Map.Entry<String, Location> location: gameSpec.locations.entrySet())
               {
                    Location loc = location.getValue();
                    img = gameSpec.locationTypes.get(loc.type).image;
                    int xCorner = loc.point.x-img.hotSpot.x-img.image.getWidth(this)/2;
                    int yCorner = loc.point.y-img.hotSpot.y-img.image.getHeight(this)/2;
                    bg.drawImage(img.image,xCorner, yCorner,this);   //drawing from upper left corner, not center => offset according to widht and height and hotspot
               }
               
               //paint stones
               for (Map.Entry<String, String> entry : elements.entrySet()) 
               {
                    String elementName=entry.getKey();                    
                    Element element = gameSpec.elements.get(elementName);
                    ElementType elType = gameSpec.elementTypes.get(element.type);                    
                    Integer actualState = egameState.elementStates.get(elementName);
                    img = elType.images[actualState-1];
                    String elementLoc = entry.getValue();
                    Location loc = gameSpec.locations.get(elementLoc);
                    int xCorner = loc.point.x-img.hotSpot.x-img.image.getWidth(this)/2;
                    int yCorner = loc.point.y-img.hotSpot.y-img.image.getHeight(this)/2;
                    bg.drawImage(img.image,xCorner,yCorner,this );
                }                
            }
            highlightSelected(bg);
            g.drawImage(buffImg, 0, 0, this);
        }
    }
    
    private void highlightSelected(Graphics g) {
        if (selectedElementName != null) {
            String elementLoc = egameState.elementLocations.get(selectedElementName);
            Location loc = gameSpec.locations.get(elementLoc);
            gameSpec.locationTypes.get(loc.type).shape.paintShape(g, loc.point);
        }
    }
    
    public void setSelectedElement(String name) {
        selectedElementName = name;
        this.repaint();
    }
}
