/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import s3games.engine.GameState;
import s3games.engine.Game;
import s3games.engine.GameSpecification;
import s3games.engine.Location;
import s3games.engine.Move;

/**
 *
 * @author Boris
 */
public class GameWindow extends javax.swing.JFrame {
    Game game;
    BoardCanvas boardCanvas;
    boolean repaint;

    ArrayList<String> outputTexts;
    String winner;
    int offsetY;  //for drawing of text = distance of right gap from left side
    
    boolean isSelectedElement;
    String selectedElementName;
    
    public Move lastMove;
    public final Object lastMoveReady;
    public boolean waitingForMove;
    public ArrayList<Move> allowedMoves;
    
    /**
     * Creates new form Form
     */
    public GameWindow() {
        initComponents();
        boardCanvas = (BoardCanvas) canvas1;
        lastMoveReady = new Object();
        waitingForMove = false;
        
        outputTexts = null;
        winner = "";
        repaint = true; 
        isSelectedElement = false;
    }

    public void showException(Exception e)
    {
        //TODO report exception to user
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, e.toString(),"Exception occured", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas1 = new BoardCanvas();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Board");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        canvas1.setBackground(new java.awt.Color(255, 255, 255));
        canvas1.setPreferredSize(new java.awt.Dimension(600, 600));
        canvas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                canvas1MousePressed(evt);
            }
        });
        canvas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                canvas1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void canvas1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas1MousePressed
        if (waitingForMove) {
            GameSpecification gs = boardCanvas.gameSpec;
            GameState egs = boardCanvas.egameState;
            Map<String,String> elements = egs.elementLocations;
            
            int x = evt.getX();
            int y = evt.getY();
 
            if (!isSelectedElement) {     //iterate through each movable element... 
                  for (Map.Entry<String, String> entry : elements.entrySet()) {
                       String elementLoc = entry.getValue();
                       Location loc = gs.locations.get(elementLoc);
                       if (gs.locationTypes.get(loc.type).shape.isInside(x, y, loc.point) && isElementAllowed(entry.getKey())) {
                          isSelectedElement = true;
                          selectedElementName = entry.getKey();
                          boardCanvas.setSelectedElement(selectedElementName);
                          return;
                       }
                  } 
            } else {                      //deselect or identify new location  
                String elementLoc = egs.elementLocations.get(selectedElementName);
                Location loc1 = gs.locations.get(elementLoc);
                if (gs.locationTypes.get(loc1.type).shape.isInside(x, y, loc1.point)) {                          //if was selected actually selected again, selection will be canceled
                    isSelectedElement = false;
                    boardCanvas.setSelectedElement(null);
                } else {       //iterate through each location...  
                    for (Map.Entry<String, Location> entry : gs.locations.entrySet()) {
                       Location loc = entry.getValue();
                       
                       if (gs.locationTypes.get(loc.type).shape.isInside(x, y, loc.point) && isMoveAllowed(selectedElementName, entry.getKey())) { 
                            isSelectedElement = false;
                            boardCanvas.setSelectedElement(null);
                           
                            String fromLoc= egs.elementLocations.get(selectedElementName);
                 
                            lastMove = new Move(fromLoc,entry.getKey(),selectedElementName);
                            System.out.println(fromLoc+" "+entry.getKey()+" "+selectedElementName);
                            synchronized(lastMoveReady)
                            {
                                lastMoveReady.notify();
                                waitingForMove = false;
                            }
                            return;                         
                        }   
                    }
                }
            }
        }
    }//GEN-LAST:event_canvas1MousePressed

    private boolean isElementAllowed(String element) {
        for (Move move: allowedMoves) {
            if (element.equals(move.element)) return true;
        }    
        return false;
    }  
    private boolean isMoveAllowed(String element, String toLocation) {
        for (Move move: allowedMoves) {
            if (element.equals(move.element) && toLocation.equals(move.to)) return true;
        }
        return false;
    }
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
       if (Character.toLowerCase(evt.getKeyChar()) == 'r') {
           repaint = !repaint;
           this.repaint();
        }
    }//GEN-LAST:event_formKeyPressed

    private void canvas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_canvas1KeyPressed
        this.formKeyPressed(evt);   //added because of varied focus on window components...
    }//GEN-LAST:event_canvas1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }
    public void setGame(Game g){ //game
        this.game = g;
        GameSpecification gs = game.gameSpecification;
        try {
            //resize the window + canvas according to image
            Image bgImage = ImageIO.read(new File(gs.boardBackgroundFileName));
            this.setSize(bgImage.getWidth(this)+132,bgImage.getHeight(this)+30);  //130 takes panel and 30 because top panel also takes a place and his height is counted to total size of jframe...
            offsetY=bgImage.getWidth(this)+4;
            //boardCanvas.setPreferredSize(new java.awt.Dimension(bgImage.getWidth(this),bgImage.getHeight(this)));
            boardCanvas.setSize(new java.awt.Dimension(bgImage.getWidth(this),bgImage.getHeight(this)));
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        boardCanvas.setGame(gs);
    }
    public void setState(GameState egs) {         
        //generate array of strings for output text
        int currentPlayer = egs.currentPlayer;
        outputTexts = new ArrayList<String>();
        outputTexts.add("Player on move:"); 
        outputTexts.add("  "+boardCanvas.gameSpec.playerNames[currentPlayer-1]);
        outputTexts.add("Scores: ");
        for(int i=0; i< boardCanvas.gameSpec.playerNames.length; i++) {
            String name = boardCanvas.gameSpec.playerNames[i];
            int score = egs.playerScores[i];
            outputTexts.add((i+1)+" "+name+": "+score);   //for output are players indexing from 1
        }
        
        if (egs.winner >= 0) {   //game finished
           winner = ((egs.winner!=0)?"Player "+boardCanvas.gameSpec.playerNames[egs.winner-1]+" wins!":"Draw!");                  //if someone won
        }
        
        this.repaint();             
        if (repaint) {
            boardCanvas.setState(egs);
            boardCanvas.repaint();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(offsetY, 0, this.getWidth(), this.getHeight());  //clear last text
        //draw output texts from array of strings - generated in setState
        if (outputTexts != null) {
           int x = 70;
           for(String s :outputTexts) {  //foreach item
               x += 17;
               g.drawString(s,offsetY,x);
           }
        }
        Font font = new Font("Arial", Font.PLAIN, 10);
        g.setFont(font);
        g.drawString("Canvas redrawing: "+((repaint)?"ON":"OFF"), offsetY+3, this.getHeight() - 30 );
        g.drawString("-press key R to change", offsetY+5, this.getHeight() - 19 );
        
        if (!winner.equals("")) { 
             g.setFont(new Font("Arial",Font.BOLD,12));
             g.drawString(winner,offsetY,55);
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    // End of variables declaration//GEN-END:variables
}
