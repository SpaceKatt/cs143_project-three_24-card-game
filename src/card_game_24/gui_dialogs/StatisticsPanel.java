/*
 * Copyright (C) 2016 Thomas Kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package card_game_24.gui_dialogs;

import card_game_24.objects.Player;
import java.awt.Toolkit;

/**
 *
 * @author Thomas
 */
public class StatisticsPanel extends javax.swing.JDialog {
    private Player player;
    private int currentScore;
    
    
    /**
     * Creates new form StatisticsPanel
     */
    public StatisticsPanel(Player user, int currentScoreYo) {
        this.player = user;
        this.currentScore = currentScoreYo;
        initComponents();
        this.setModal(true);
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.closeButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/card_game_24/images/cards/king_of_hearts.png"));
        displayUserStats();
    }

    /**
     * Displays the basic user statistics on the right hand side of the main
     * GUI. There are not the comprehensive statistics that may be accessed
     * via the game menu, these are much more basic.
     */
    private void displayUserStats() {
        this.userTextField.setText(this.player.getName());
        this.currentScoreTextField.setText("" + this.currentScore);
        this.totalScoreTextField.setText("" + this.player.getTotalScore());
        this.highScoreTextField.setText("" + this.player.getHighScore());
        this.totalGamesTextField.setText("" + this.player.getTotalGames());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        statisticsPanel = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        currentScoreLabel = new javax.swing.JLabel();
        currentScoreTextField = new javax.swing.JTextField();
        totalScoreLabel = new javax.swing.JLabel();
        totalScoreTextField = new javax.swing.JTextField();
        highScoreLabel = new javax.swing.JLabel();
        highScoreTextField = new javax.swing.JTextField();
        totalGamesLabel = new javax.swing.JLabel();
        totalGamesTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Statistics");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/card_game_24/images/kinglogo-panel.png"))); // NOI18N

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getContentPane().add(logoPanel, java.awt.BorderLayout.NORTH);

        exitPanel.setLayout(new java.awt.BorderLayout());

        closeButton.setText("Close");
        closeButton.setToolTipText("Close this window");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        exitPanel.add(closeButton, java.awt.BorderLayout.CENTER);

        getContentPane().add(exitPanel, java.awt.BorderLayout.SOUTH);

        statisticsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Statistics"));
        statisticsPanel.setLayout(new java.awt.GridLayout(5, 2, 5, 10));

        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        userLabel.setText("Current User:");
        statisticsPanel.add(userLabel);

        userTextField.setEditable(false);
        userTextField.setToolTipText("The current user's name");
        statisticsPanel.add(userTextField);

        currentScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        currentScoreLabel.setText("Current Score:");
        statisticsPanel.add(currentScoreLabel);

        currentScoreTextField.setEditable(false);
        currentScoreTextField.setToolTipText("The current user's current score");
        statisticsPanel.add(currentScoreTextField);

        totalScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        totalScoreLabel.setText("Total Score:");
        statisticsPanel.add(totalScoreLabel);

        totalScoreTextField.setEditable(false);
        totalScoreTextField.setToolTipText("The current user's total score, from all games played");
        statisticsPanel.add(totalScoreTextField);

        highScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        highScoreLabel.setText("High Score:");
        statisticsPanel.add(highScoreLabel);

        highScoreTextField.setEditable(false);
        highScoreTextField.setToolTipText("The current user's high score");
        statisticsPanel.add(highScoreTextField);

        totalGamesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        totalGamesLabel.setText("Total Games Played:");
        totalGamesLabel.setToolTipText("");
        statisticsPanel.add(totalGamesLabel);

        totalGamesTextField.setEditable(false);
        totalGamesTextField.setToolTipText("The current user's total amount of games played");
        statisticsPanel.add(totalGamesTextField);

        getContentPane().add(statisticsPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel currentScoreLabel;
    private javax.swing.JTextField currentScoreTextField;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JLabel highScoreLabel;
    private javax.swing.JTextField highScoreTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JLabel totalGamesLabel;
    private javax.swing.JTextField totalGamesTextField;
    private javax.swing.JLabel totalScoreLabel;
    private javax.swing.JTextField totalScoreTextField;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}