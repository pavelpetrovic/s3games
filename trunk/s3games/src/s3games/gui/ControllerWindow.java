/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.gui;

import java.util.ArrayList;
import javax.swing.*;
import s3games.Controller;
import s3games.player.Player;
/**
 *
 * @author Boris
 */
public class ControllerWindow extends javax.swing.JFrame 
{
    Controller controller = null;
    Player.playerType[] playerTypes;
    String[] playerStrategies;
    String[][] strategyHeuristics;
     /**
     * Creates new form ControllerWindow
     */  
    public ControllerWindow() 
    {
        initComponents();
    }  
    
    public ControllerWindow(Controller c) 
    {
         initComponents();
         controller = c;
         jComboBox5.setModel(new DefaultComboBoxModel(c.getGameNames()));
         setItems();
    }
    
    public void gameFinished(int winner, int[] playerScores)
    {    
        String info = "<html>";
        info = info + "Winner player: "+winner+" <br> Scores: <br>";
        for (int p = 0; p < playerScores.length; p++)
            info = info+"Player "+(p+1)+": "+playerScores[p] + "<br>";
        info += "</html>";       
        jLabel2.setText(info);       
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Settings");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("PLAY");
        jButton1.setToolTipText("");
        jButton1.setPreferredSize(new java.awt.Dimension(65, 31));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("LEARN");
        jButton2.setToolTipText("");
        jButton2.setActionCommand("LEARN! ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setText("statistic.txt");
        jTextField1.setMaximumSize(new java.awt.Dimension(134, 20));
        jTextField1.setPreferredSize(new java.awt.Dimension(134, 20));

        jLabel6.setText("Save game statistic to:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simulated game", "Real world game" }));

        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel9.setText("Select a game:");

        jTextField2.setText("StrategyXY.txt");
        jTextField2.setMaximumSize(new java.awt.Dimension(133, 20));
        jTextField2.setPreferredSize(new java.awt.Dimension(133, 20));

        jLabel10.setText("OR");

        jLabel11.setText("Save to:");

        jTextField3.setText("3");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("Number of runs:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel13.setText("Learn strategy type:");

        jTextField5.setText("1");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel14.setText("Number of runs:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)))
                            .addComponent(jLabel13)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(229, 237, 255));

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Strategy for computer:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Computer player", "Human player" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Player number:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTextField4.setText("500");

        jLabel15.setText("Number of expanded nodes:");
        jLabel15.setToolTipText("Num of expanded nodes after each move (in search algorithms)");

        jPanel3.setBackground(new java.awt.Color(255, 238, 216));

        jLabel5.setText("0");

        jLabel2.setText("no game played yet");

        jLabel1.setText("Previous game result:");

        jLabel4.setText("Num of games to run:");
        jLabel4.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jButton3.setLabel("Control Robot");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addGap(45, 45, 45))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
        setItems();
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        int selectedPlayer = jComboBox3.getSelectedIndex();
        if (jComboBox2.getSelectedIndex()==0) {  //computer player selected
            jComboBox4.setEnabled(true);
            jComboBox4.setSelectedItem((Object) playerStrategies[selectedPlayer]);
            jComboBox7.setEnabled(true);
            playerTypes[selectedPlayer]=Player.playerType.COMPUTER;
        } else {  //human player selected
            jComboBox4.setEnabled(false);
            jComboBox7.setEnabled(false);
            playerTypes[selectedPlayer]=Player.playerType.HUMAN;
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
         int selectedPlayer = jComboBox3.getSelectedIndex();      
         if (playerTypes[selectedPlayer]==Player.playerType.COMPUTER) {     //show actual settings 
             jComboBox2.setSelectedIndex(0);                                                 // -0 for computer, 1 for human
             jComboBox4.setSelectedItem((Object) playerStrategies[selectedPlayer]);            
           //  String heuristic = strategyHeuristics[selectedPlayer][jComboBox4.getSelectedIndex()];  //I think it is actually updated from jcombobox4.action performed because it is calling after modification
           //  jComboBox7.setSelectedItem((Object)heuristic);   
         } else {
             jComboBox2.setSelectedIndex(1);
         }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private int getSelectedIndexStrategyForPlayer(String strategy) {
         String[] strategies = controller.getStrategiesForGame((String)jComboBox5.getSelectedItem());
         for(int i=0; i<strategies.length;i++)
         {    
             if(strategies[i].equals(strategy))
                 return i;    
         }
         return 0;
    }
    
    public String getStatisticFileName() {     
      return jTextField1.getText();
    }
    
    public void setNumberOfRunsToGo(int n) {
       jLabel5.setText(n+""); 
    }
    
    private String[] getSelectedHeuristics() {
        int numOfPlayers = playerTypes.length;
        
        String[] selectedHeuristics = new String[numOfPlayers];
        for (int i=0; i<numOfPlayers; i++) {
             selectedHeuristics[i] = strategyHeuristics[i][getSelectedIndexStrategyForPlayer(playerStrategies[i])];
        }
        return selectedHeuristics;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:  
        controller.play((String)jComboBox5.getSelectedItem(), 
                         ((jComboBox1.getSelectedIndex()==0)?Player.boardType.SIMULATED:Player.boardType.REALWORLD),        // 0 simulated game, 1 real game
                         playerTypes, 
                         playerStrategies,
                         getSelectedHeuristics(),
                         Integer.parseInt(jTextField5.getText()) );                 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        int selectedPlayer = jComboBox3.getSelectedIndex();
        String strategy = (String)jComboBox4.getSelectedItem();
        playerStrategies[selectedPlayer] = strategy;
        
        String heuristic = strategyHeuristics[selectedPlayer][jComboBox4.getSelectedIndex()];
        jComboBox7.setSelectedItem((Object)heuristic);
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        controller.learn((String)jComboBox5.getSelectedItem(), 
                        ((jComboBox1.getSelectedIndex()==0)?Player.boardType.SIMULATED:Player.boardType.REALWORLD),        // 0 simulated game, 1 real game
                        playerTypes, 
                        playerStrategies, 
                        getSelectedHeuristics(),
                        (String)jComboBox6.getSelectedItem(), 
                        jTextField2.getText(), 
                        Integer.parseInt(jTextField3.getText()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
        int selectedPlayer = jComboBox3.getSelectedIndex();
        int selectedStrategy = jComboBox4.getSelectedIndex();
        strategyHeuristics[selectedPlayer][selectedStrategy] = (String)jComboBox7.getSelectedItem();
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        controller.controlRobot();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    public int getNumberOfNodesToExpand() {
        try {
            return Integer.parseInt(jTextField4.getText());
        } catch (NumberFormatException e) {
             System.out.println("Bad number format entered");
             return 100;
        }
    }
    private void setItems() {
        String gameName = (String)jComboBox5.getSelectedItem();
        jComboBox4.setModel(new DefaultComboBoxModel(controller.getStrategiesForGame(gameName)));
        jComboBox6.setModel(new DefaultComboBoxModel(controller.getLearnableStrategyTypesForGame(gameName))); 
        jComboBox7.setModel(new DefaultComboBoxModel(controller.getHeuristicsForGame(gameName)));
        
        int numOfPlayers = controller.getNumberOfPlayersForGame(gameName);
        String [] players = new String[numOfPlayers];
        for (int i=1; i<=numOfPlayers; i++) {
           players[i-1]=Integer.toString(i);
        }
        jComboBox3.setModel(new DefaultComboBoxModel(players));
        
        //pre-definition of players and strategies for all players
        playerTypes = new Player.playerType[numOfPlayers];
        playerStrategies = new String[numOfPlayers];
        strategyHeuristics = new String[numOfPlayers][];
        Player.playerType pt = (jComboBox2.getSelectedIndex()==0)?Player.playerType.COMPUTER:Player.playerType.HUMAN;   //0 for computer, 1 for human
        String ps = (String)jComboBox4.getSelectedItem();
        String sh = (String)jComboBox7.getSelectedItem();
        for (int i=0; i<numOfPlayers; i++) {
             playerTypes[i]=pt;
             playerStrategies[i]=ps;
             strategyHeuristics[i]=new String[jComboBox4.getItemCount()];   //settings for each player - possible strategies, for every possible strategy can be selected heuristic
            for (int j=0; j<strategyHeuristics[i].length; j++) {               //init values for each strategy
               strategyHeuristics[i][j]=sh;             
            }
        }

    }
    
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
            java.util.logging.Logger.getLogger(ControllerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControllerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControllerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControllerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControllerWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
