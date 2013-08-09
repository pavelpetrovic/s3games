package s3games.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import s3games.engine.Element;
import s3games.engine.ElementType;
import s3games.engine.GameState;
import s3games.engine.GameSpecification;
import s3games.engine.Location;

/** Implements the canvas that shows the game progress in game window */
public class BoardCanvas extends Canvas {

    /** reference to game specification */
    public GameSpecification gameSpec = null;
    
    /** reference to current game state */
    GameState gState = null;
    
    /** background image of the game board */
    Image bgImage;
        
    /** auxiliary variable for holding images with reference points */
    ImageWithHotSpot img;
    
    /** we use double-buffering to avoid flickering */
    private BufferedImage buffImg;
    
    /** remembers the element that was selected by a mouse click */
    String selectedElementName = null;
    
    /** controller sends the game specification to here */
    public void setGame(GameSpecification gs) 
    {        
        bgImage = Toolkit.getDefaultToolkit().getImage(gs.boardBackgroundFileName);
        gState = null;
        buffImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        gameSpec = gs;
    }
        
    /** controller sends the current game state to here */
    public void setState(GameState egs) 
    {
        gState = egs;    
    }
        
    /** we override the update() method to avoid the flickering */
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    /** paints the current game state in the canvas's graphics object */
    @Override
    public void paint(Graphics g) 
    {
        if (gameSpec!=null) {
            Graphics bg = buffImg.getGraphics();
            bg.clearRect(0, 0, this.getWidth(), this.getHeight());  //clear window
            bg.drawImage(bgImage,0,0,this.getWidth(),this.getHeight(),this); //background
            if (gState!=null) {
               Map<String,String> elements = gState.elementLocations;
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
                    Integer actualState = gState.elementStates.get(elementName);
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
    
    /** draws a selection marker around the selected element */
    private void highlightSelected(Graphics g) 
    {
        if (selectedElementName != null) {
            String elementLoc = gState.elementLocations.get(selectedElementName);
            Location loc = gameSpec.locations.get(elementLoc);
            gameSpec.locationTypes.get(loc.type).shape.paintShape(g, loc.point);
        }
    }
    
    /** stores the current selected element - called from the mouse listener */
    public void setSelectedElement(String name) 
    {
        selectedElementName = name;
        this.repaint();
    }
}
