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
package card_game_24;

import card_game_24.gui_dialogs.PlayerSelector;
import card_game_24.objects.DeckOfCards;
import card_game_24.objects.Player;
import card_game_24.utilities.PlayerXMLFileReader;
import card_game_24.utilities.PlayerXMLFileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * This class is the Main GUI for the 24 Card Game Project, for my Java 143
 * class (Spr2016). The purpose of this project is to create an application
 * which allows users to arrange a draw of four cards into an arithmetic
 * expression which evaluates to 24.
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 23, 2016, 5:10 PM
 * Revised on May 23, 2016,  PM
 * 
 * @author Thomas Kercheval
 */
public class CardGame24GUI extends javax.swing.JFrame {
    /** The deck of cards from which we will play. */
    private DeckOfCards deck;
    /** The current player whose score we are tracking. */
    private Player player;
    /** All the Players in the database. */
    private ArrayList<Player> players;
    /** The path to our database full of users. */
    private String filePath = "src/card_game_24/data/Players.xml";
    
    /**
     * Creates new form CardGame24GUI
     */
    public CardGame24GUI() {
        this.deck = new DeckOfCards();
        this.players = readPlayersFromFile();
        this.player = selectPlayer();
        savePlayers();
        initComponents();
        displayCards();
    }

    /**
     * Reads all of our Players information from an XML File, as arrays of 
     * Strings, then feeds each String[] to the Player's array constructor
     * to create an ArrayList of Player objects.
     * @return An ArrayList of Player objects.
     */
    private ArrayList<Player> readPlayersFromFile() {
        ArrayList<Player> users = new ArrayList<>();
        PlayerXMLFileReader reader = new PlayerXMLFileReader(this.filePath);
        ArrayList<String[]> playersInfo = reader.getPlayerInformation();
        for (String[] playerInfo : playersInfo) {
            users.add(new Player(playerInfo));
        }
        return users;
    }
    
    private void displayCards() {
        int[] hand = this.deck.getHand();
        HashMap<String, ImageIcon> imageMap = this.deck.getDeck();
        cardOneLabel.setIcon(imageMap.get(String.valueOf(hand[0])));
        cardTwoLabel.setIcon(imageMap.get(String.valueOf(hand[1])));
        cardThreeLabel.setIcon(imageMap.get(String.valueOf(hand[2])));
        cardFourLabel.setIcon(imageMap.get(String.valueOf(hand[3])));
    }
    
    /**
     * Spawns a JDialog which allows the user to select a User out of all 
     * existing players so that the application may track their stats
     * under this username.
     * @return Player object which will be the subject of the game.
     */
    private Player selectPlayer() {
        PlayerSelector selector = new PlayerSelector(this.players);
        selector.setVisible(true);
        if (selector.getPlayer() != null) {
            Player selectedPlayer = selector.getPlayer();
            if (!players.contains(selectedPlayer) 
                    && !selectedPlayer.getName().equals("Guest")) {
                players.add(selectedPlayer);
            }
            return selectedPlayer;
        } else {
            if (this.player == null) {
                System.exit(0);
            }
        }
        return this.player;
    }
    
    /**
     * Saves the Statistics of every player as long as the current player
     * is not using the Guest Account.
     */
    private void savePlayers() {
        if (!player.getName().equals("Guest")) {
            writePlayersToFile(this.filePath, this.players);
        }
    }
    
    /**
     * Writes the ArrayList `players` to an XML Database.
     * @param file The file path of the XML Database we will save our Players
     * information to.
     * @param arraylist the ArrayList of the players we wish to save.
     */
    private void writePlayersToFile(String file, ArrayList arraylist) {
        PlayerXMLFileWriter writer = new PlayerXMLFileWriter(arraylist, file);
        writer.createXMLFile();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        upperControlPanel = new javax.swing.JPanel();
        solutionButton = new javax.swing.JButton();
        solutionTextField = new javax.swing.JTextField();
        validLabel = new javax.swing.JLabel();
        shuffleButton = new javax.swing.JButton();
        expressionPanel = new javax.swing.JPanel();
        expressionLabel = new javax.swing.JLabel();
        expressionTextField = new javax.swing.JTextField();
        expressionButton = new javax.swing.JButton();
        displayPanel = new javax.swing.JPanel();
        cardOneLabel = new javax.swing.JLabel();
        cardTwoLabel = new javax.swing.JLabel();
        cardThreeLabel = new javax.swing.JLabel();
        cardFourLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(480, 300));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 400));

        upperControlPanel.setLayout(new java.awt.GridLayout(1, 4));

        solutionButton.setText("Find Solution(s)");
        upperControlPanel.add(solutionButton);

        solutionTextField.setEditable(false);
        upperControlPanel.add(solutionTextField);

        validLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        upperControlPanel.add(validLabel);

        shuffleButton.setText("Shuffle");
        shuffleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shuffleButtonActionPerformed(evt);
            }
        });
        upperControlPanel.add(shuffleButton);

        getContentPane().add(upperControlPanel, java.awt.BorderLayout.NORTH);

        expressionPanel.setLayout(new java.awt.GridLayout(1, 3));

        expressionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        expressionLabel.setText("Enter your expression: ");
        expressionPanel.add(expressionLabel);
        expressionPanel.add(expressionTextField);

        expressionButton.setText("Evaluate");
        expressionPanel.add(expressionButton);

        getContentPane().add(expressionPanel, java.awt.BorderLayout.SOUTH);

        displayPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.setLayout(new java.awt.GridLayout(1, 4, 10, 10));
        displayPanel.add(cardOneLabel);
        displayPanel.add(cardTwoLabel);
        displayPanel.add(cardThreeLabel);
        displayPanel.add(cardFourLabel);

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void shuffleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleButtonActionPerformed
        this.deck.resetHand();
        displayCards();
    }//GEN-LAST:event_shuffleButtonActionPerformed

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
            java.util.logging.Logger.getLogger(CardGame24GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardGame24GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardGame24GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardGame24GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CardGame24GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardFourLabel;
    private javax.swing.JLabel cardOneLabel;
    private javax.swing.JLabel cardThreeLabel;
    private javax.swing.JLabel cardTwoLabel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton expressionButton;
    private javax.swing.JLabel expressionLabel;
    private javax.swing.JPanel expressionPanel;
    private javax.swing.JTextField expressionTextField;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton shuffleButton;
    private javax.swing.JButton solutionButton;
    private javax.swing.JTextField solutionTextField;
    private javax.swing.JPanel upperControlPanel;
    private javax.swing.JLabel validLabel;
    // End of variables declaration//GEN-END:variables
}
