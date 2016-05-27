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

import card_game_24.utilities.EvaluateExpression;
import card_game_24.gui_dialogs.PlayerSelector;
import card_game_24.objects.DeckOfCards;
import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import card_game_24.objects.Player;
import static card_game_24.utilities.EvaluateExpression.insertBlanks;
import card_game_24.utilities.PlayerXMLFileReader;
import card_game_24.utilities.PlayerXMLFileWriter;
import static card_game_24.utilities.SortingAlgorithms.insertionSort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
    private int currentScore;
    
    /**
     * Creates new form CardGame24GUI
     */
    public CardGame24GUI() {
        this.deck = new DeckOfCards();
        this.players = readPlayersFromFile();
        this.player = selectPlayer();
        savePlayers();
        initComponents();
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.expressionButton);
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
                JOptionPane.showMessageDialog(null,
                        "No player selected, exiting application.",
                        "Exiting application.",
                        JOptionPane.INFORMATION_MESSAGE);
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
        if (player == null) {
            writePlayersToFile(this.filePath, this.players);
        } else if (!player.getName().equals("Guest")) {
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
     * Determines whether the numbers entered in our expression matches the
     * numbers which were drawn in the hand.
     * @param express The expression we are validating.
     * @return True if the numbers used in the expression are valid.
     */
    private boolean validExpression(String express) {
        express = insertBlanks(express);
        express = express.replaceAll("\\D+"," ");
        String[] numberStrings = express.split(" ");
        int[] numbers = new int[4];
        int correction = 0;
        for (int i = 0; i < numberStrings.length; i++) {
            if (numberStrings[i].length() > 0 
                    & !numberStrings[i].equals(" ")) {
                numbers[i - correction] = Integer.valueOf(numberStrings[i]);
//                System.out.println(Integer.valueOf(numberStrings[i]) +  " is " + numberStrings[i]);
            } else {
                correction++;
            }
        }
        int[] numbersHand = new int[numbers.length];
        for (int i = 0; i < numbersHand.length; i++) {
            int cardNumber = this.deck.getHand()[i] % 13;
            if (cardNumber == 0) {
                cardNumber = 13;
            }
            numbersHand[i] = cardNumber;
        }
        insertionSort(numbers);
        insertionSort(numbersHand);
//        for (int i = 0; i < numbersHand.length; i++) {
//            System.out.println(numbersHand[i]);
//            System.out.println(numbers[i]);
//        }
        return Arrays.equals(numbers, numbersHand);
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
        cardsMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        printMenuItem = new javax.swing.JMenuItem();
        printStatisticsMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        gameMenu = new javax.swing.JMenu();
        switchUserMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        deleteUserMenuItem = new javax.swing.JMenuItem();
        searchUserMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        statisticsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        shffleMenuItem = new javax.swing.JMenuItem();
        evaulateMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        solutionsMenuItem = new javax.swing.JMenuItem();
        combinationMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(480, 300));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 400));

        upperControlPanel.setLayout(new java.awt.GridLayout(1, 4));

        solutionButton.setMnemonic('s');
        solutionButton.setText("Find Solution(s)");
        solutionButton.setToolTipText("Finds all he solutions to the current hand and displays them");
        upperControlPanel.add(solutionButton);

        solutionTextField.setEditable(false);
        upperControlPanel.add(solutionTextField);

        validLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        validLabel.setToolTipText("Will tell you if your expression is valid, incorrect, of invalid");
        upperControlPanel.add(validLabel);

        shuffleButton.setMnemonic('s');
        shuffleButton.setText("Shuffle");
        shuffleButton.setToolTipText("Shuffle the current hand (display different cards)");
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

        expressionTextField.setToolTipText("Enter a valid infix experssion");
        expressionPanel.add(expressionTextField);

        expressionButton.setMnemonic('v');
        expressionButton.setText("Evaluate");
        expressionButton.setToolTipText("Evalutate an Expression");
        expressionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expressionButtonActionPerformed(evt);
            }
        });
        expressionPanel.add(expressionButton);

        getContentPane().add(expressionPanel, java.awt.BorderLayout.SOUTH);

        displayPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.setLayout(new java.awt.GridLayout(1, 4, 10, 10));

        cardOneLabel.setToolTipText("The first card you many use...");
        displayPanel.add(cardOneLabel);

        cardTwoLabel.setToolTipText("The second card you many use...");
        displayPanel.add(cardTwoLabel);

        cardThreeLabel.setToolTipText("The third card you many use...");
        displayPanel.add(cardThreeLabel);

        cardFourLabel.setToolTipText("The fourth card you many use...");
        displayPanel.add(cardFourLabel);

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setToolTipText("Perform actions regarding this application");

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);
        fileMenu.add(jSeparator5);

        printMenuItem.setText("Print GUI");
        fileMenu.add(printMenuItem);

        printStatisticsMenuItem.setText("Print Statistics");
        fileMenu.add(printStatisticsMenuItem);
        fileMenu.add(jSeparator3);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.setToolTipText("Exit the application");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        cardsMenuBar.add(fileMenu);

        gameMenu.setMnemonic('g');
        gameMenu.setText("Game");
        gameMenu.setToolTipText("Perform actions that are relevant to this game.");

        switchUserMenuItem.setText("Switch User");
        switchUserMenuItem.setToolTipText("Switch which user is currently playing");
        switchUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(switchUserMenuItem);

        jMenuItem1.setText("Edit Username");
        gameMenu.add(jMenuItem1);

        deleteUserMenuItem.setMnemonic('d');
        deleteUserMenuItem.setText("Delete Current User");
        deleteUserMenuItem.setToolTipText("Delete user that is currently player");
        deleteUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(deleteUserMenuItem);

        searchUserMenuItem.setText("Search User");
        gameMenu.add(searchUserMenuItem);
        gameMenu.add(jSeparator4);

        statisticsMenuItem.setMnemonic('i');
        statisticsMenuItem.setText("Display Statistics");
        statisticsMenuItem.setToolTipText("Display the current user's statistics");
        gameMenu.add(statisticsMenuItem);
        gameMenu.add(jSeparator1);

        shffleMenuItem.setMnemonic('s');
        shffleMenuItem.setText("Shuffle");
        shffleMenuItem.setToolTipText("Shuffle the current hand (display different cards)");
        gameMenu.add(shffleMenuItem);

        evaulateMenuItem.setMnemonic('v');
        evaulateMenuItem.setText("Evaluate");
        evaulateMenuItem.setToolTipText("Evalutate an Expression");
        gameMenu.add(evaulateMenuItem);
        gameMenu.add(jSeparator2);

        solutionsMenuItem.setMnemonic('s');
        solutionsMenuItem.setText("Find all solutions");
        solutionsMenuItem.setToolTipText("Finds all he solutions to the current hand and displays them");
        gameMenu.add(solutionsMenuItem);

        combinationMenuItem.setMnemonic('c');
        combinationMenuItem.setText("Enter Combination");
        combinationMenuItem.setToolTipText("Find all solutions to a card combination of your choosing!");
        gameMenu.add(combinationMenuItem);

        cardsMenuBar.add(gameMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");
        helpMenu.setToolTipText("Help has arrived");

        aboutMenuItem.setMnemonic('t');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("About this project");
        helpMenu.add(aboutMenuItem);

        cardsMenuBar.add(helpMenu);

        setJMenuBar(cardsMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void shuffleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleButtonActionPerformed
        this.player.incrementTotalGames();
        this.player.addPossibleScore(this.currentScore);
        this.currentScore = 0;
        this.deck.resetHand();
        displayCards();
        savePlayers();
    }//GEN-LAST:event_shuffleButtonActionPerformed

    private void expressionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expressionButtonActionPerformed
        String expression = this.expressionTextField.getText();
        if (expression != null && expression.length() > 0) {
            try {
                int number = EvaluateExpression.evaluateInfixExpression(expression);
                if (!validExpression(expression)) {
                    JOptionPane.showMessageDialog(null,
                        "You may only use the numbers given on the cards",
                        "Invalid expression.",
                        JOptionPane.INFORMATION_MESSAGE);
                } else if (number == 24) {
                    this.validLabel.setText("Valid Expression");
                    this.currentScore++;
                    savePlayers();
                } else {
                    this.validLabel.setText("Incorrect Expression");
                }
            } catch (EmptyStackException | FullStackException ex) {
                this.validLabel.setText("Invalid Expression");
            } 
        } else {
            JOptionPane.showMessageDialog(null, "No expression given.",
                    "An expression is needed to evaluate one.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_expressionButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        shuffleButtonActionPerformed(evt);
        savePlayers();
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void switchUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchUserMenuItemActionPerformed
        shuffleButtonActionPerformed(evt);
        this.player = selectPlayer();
        savePlayers();
    }//GEN-LAST:event_switchUserMenuItemActionPerformed

    private void deleteUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserMenuItemActionPerformed
        int reply = JOptionPane.showConfirmDialog(this, "Are you sure?",
                "Confirm Parcel deletion...",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (reply == JOptionPane.YES_OPTION) {
            shuffleButtonActionPerformed(evt);
            this.players.remove(this.player);
            this.player = null;
            savePlayers();
            this.player = selectPlayer();
            savePlayers();
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_deleteUserMenuItemActionPerformed

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
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel cardFourLabel;
    private javax.swing.JLabel cardOneLabel;
    private javax.swing.JLabel cardThreeLabel;
    private javax.swing.JLabel cardTwoLabel;
    private javax.swing.JMenuBar cardsMenuBar;
    private javax.swing.JMenuItem combinationMenuItem;
    private javax.swing.JMenuItem deleteUserMenuItem;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JMenuItem evaulateMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton expressionButton;
    private javax.swing.JLabel expressionLabel;
    private javax.swing.JPanel expressionPanel;
    private javax.swing.JTextField expressionTextField;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem printStatisticsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem searchUserMenuItem;
    private javax.swing.JMenuItem shffleMenuItem;
    private javax.swing.JButton shuffleButton;
    private javax.swing.JButton solutionButton;
    private javax.swing.JTextField solutionTextField;
    private javax.swing.JMenuItem solutionsMenuItem;
    private javax.swing.JMenuItem statisticsMenuItem;
    private javax.swing.JMenuItem switchUserMenuItem;
    private javax.swing.JPanel upperControlPanel;
    private javax.swing.JLabel validLabel;
    // End of variables declaration//GEN-END:variables
}
