/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import s3games.engine.ExtendedGameState;
import s3games.engine.GameSpecification;

/**
 *
 * @author Boris
 */
public class GameWindow extends javax.swing.JFrame {
    BoardCanvas boardCanvas;
    /**
     * Creates new form Form
     */
    public GameWindow() {
        initComponents();
        boardCanvas = (BoardCanvas) canvas1;     
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

        canvas1.setBackground(new java.awt.Color(255, 255, 255));
        canvas1.setPreferredSize(new java.awt.Dimension(600, 600));
        canvas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                canvas1MousePressed(evt);
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

        System.out.println(evt.getX() +" "+ evt.getY());
    }//GEN-LAST:event_canvas1MousePressed

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
    public void setGame(GameSpecification gs){
        try {
            //resize the window + canvas according to image
            Image bgImage = ImageIO.read(new File(gs.boardBackgroundFileName));
            this.setSize(bgImage.getWidth(this)+132,bgImage.getHeight(this)+30);  //130 takes panel and 30 because top panel also takes a place and his height is counted to total size of jframe...
            //boardCanvas.setPreferredSize(new java.awt.Dimension(bgImage.getWidth(this),bgImage.getHeight(this)));
            boardCanvas.setSize(new java.awt.Dimension(bgImage.getWidth(this),bgImage.getHeight(this)));
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
         boardCanvas.setGame(gs);
    }
    public void setState(ExtendedGameState egs) {         
        //print outputs, listings... - TODO
        int currentPlayer = egs.basicGameState.currentPlayer;
        //g.drawString("On move player " + currentPlayer+" "+gameSpec.playerNames[currentPlayer],10,10); 
        this.repaint();
        
        boardCanvas.setState(egs);
        boardCanvas.repaint();
    }
    
    @Override
    public void paint(Graphics g) {
      // int currentPlayer = egs.basicGameState.currentPlayer;
        g.drawString("Dont touch that in ", 627,50);
        g.drawString("visual designer! sensitive", 627,65);
        //todo - draw from array of strings - generated in setState
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    // End of variables declaration//GEN-END:variables
}
