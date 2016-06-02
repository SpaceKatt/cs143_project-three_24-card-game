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

import card_game_24.gui_dialogs.AboutForm;
import card_game_24.utilities.EvaluateExpression;
import card_game_24.gui_dialogs.PlayerSelector;
import static card_game_24.gui_dialogs.PlayerSelector.isValidName;
import card_game_24.gui_dialogs.StatisticsPanel;
import card_game_24.objects.DeckOfCards;
import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import card_game_24.objects.Player;
import static card_game_24.utilities.EvaluateExpression.evaluateInfixExpression;
import static card_game_24.utilities.EvaluateExpression.insertBlanks;
import card_game_24.utilities.PlayerXMLFileReader;
import card_game_24.utilities.PlayerXMLFileWriter;
import card_game_24.utilities.PrintUtilities;
import card_game_24.utilities.RedundancyValidator;
import static card_game_24.utilities.SortingAlgorithms.insertionSort;

import java.awt.Toolkit;
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
    /** The current score of the current Player. */
    private int currentScore;
    /** 
     * The object which will determine whether the user tries to use a 
     * mathematically equivalent expression (e.g, a + b - c, vs, a - (c - b)).
     */
    private RedundancyValidator redundoValido;
    
    /**
     * Creates new form CardGame24GUI
     */
    public CardGame24GUI() {
        this.deck = new DeckOfCards();
        this.players = readPlayersFromFile();
        this.player = selectPlayer();
        savePlayers();
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/card_game_24/images/cards/king_of_hearts.png"));
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
    
    /**
     * Displays the four cards currently in play on the main displayPanel.
     * Resets the redundancy validator with the current had being displayed.
     */
    private void displayCards() {
        int[] hand = this.deck.getHand();
        this.redundoValido = new RedundancyValidator(this.deck.getHand());
        
        HashMap<String, ImageIcon> imageMap = this.deck.getDeck();
        cardOneLabel.setIcon(imageMap.get(String.valueOf(hand[0])));
        cardTwoLabel.setIcon(imageMap.get(String.valueOf(hand[1])));
        cardThreeLabel.setIcon(imageMap.get(String.valueOf(hand[2])));
        cardFourLabel.setIcon(imageMap.get(String.valueOf(hand[3])));
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
    private boolean validNumsInExpression(String express) {
        express = insertBlanks(express);
        express = express.replaceAll("\\D+"," ");
        String[] numberStrings = express.split(" ");
        int[] numbers = new int[4];
        int correction = 0;
        for (int i = 0; i < numberStrings.length; i++) {
            if (numberStrings[i].length() > 0 
                    & !numberStrings[i].equals(" ")) {
                // Correction is so we don't exceed the index bound due to
                // empty strings or spaces being scanned&skipped
                try {
                    numbers[i - correction] = Integer.valueOf(numberStrings[i]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    return false; // In case the user enters more than four
                                  // numbers.
                }
            } else {
                correction++;
            }
        }
        int[] numbersHand = new int[numbers.length];
        for (int i = 0; i < numbersHand.length; i++) {
            int cardNumber = this.deck.getHand()[i] % 13;
            if (cardNumber == 0) {
                cardNumber = 13; // Since 26 % 13 is zero, but card is King
            }
            numbersHand[i] = cardNumber;
        }
        insertionSort(numbers);
        insertionSort(numbersHand);
        return Arrays.equals(numbers, numbersHand);
    }
    
    /**
     * Searches for a player whom contains the given name, then returns
     * the index at which they are found.
     * @param playerName The name of the player which we are searching for.
     * @return The index at which they are found.
     */
    private int linearPlayerSearch(String playerName) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getName().toLowerCase().contains(
                    playerName.toLowerCase())) {
                return i;
            }
        }
        return -1;
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
        validLabel = new javax.swing.JLabel();
        shuffleButton = new javax.swing.JButton();
        statisticsPanel = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        currentScoreLabel = new javax.swing.JLabel();
        currentScoreTextField = new javax.swing.JTextField();
        totalScoreLabel = new javax.swing.JLabel();
        totalScoreTextField = new javax.swing.JTextField();
        highScoreLabel = new javax.swing.JLabel();
        highScoreTextField = new javax.swing.JTextField();
        expressionPanel = new javax.swing.JPanel();
        expressionLabel = new javax.swing.JLabel();
        expressionTextField = new javax.swing.JTextField();
        expressionButton = new javax.swing.JButton();
        logoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        editUserMenuItem = new javax.swing.JMenuItem();
        deleteUserMenuItem = new javax.swing.JMenuItem();
        searchUserMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        statisticsMenuItem = new javax.swing.JMenuItem();
        highScoreMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        shffleMenuItem = new javax.swing.JMenuItem();
        evaulateMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("24 Card Game");
        setPreferredSize(new java.awt.Dimension(760, 300));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 400));

        upperControlPanel.setLayout(new java.awt.GridLayout(1, 4));

        validLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        validLabel.setToolTipText("Will tell you if your expression is valid, incorrect, or invalid");
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

        statisticsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Statistics"));
        statisticsPanel.setLayout(new java.awt.GridLayout(4, 2, 5, 10));

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

        getContentPane().add(statisticsPanel, java.awt.BorderLayout.EAST);

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

        logoPanel.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/card_game_24/images/kinglogo-sidebar.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        logoPanel.add(jLabel1);

        getContentPane().add(logoPanel, java.awt.BorderLayout.WEST);

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

        saveMenuItem.setMnemonic('a');
        saveMenuItem.setText("Save");
        saveMenuItem.setToolTipText("Save all the players in the database");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.add(jSeparator5);

        printMenuItem.setMnemonic('g');
        printMenuItem.setText("Print GUI");
        printMenuItem.setToolTipText("Print this screen");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printMenuItem);

        printStatisticsMenuItem.setMnemonic('c');
        printStatisticsMenuItem.setText("Print Statistics");
        printStatisticsMenuItem.setToolTipText("Print the current user's Statistics");
        printStatisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printStatisticsMenuItemActionPerformed(evt);
            }
        });
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

        switchUserMenuItem.setMnemonic('u');
        switchUserMenuItem.setText("Switch User");
        switchUserMenuItem.setToolTipText("Switch which user is currently playing");
        switchUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(switchUserMenuItem);

        editUserMenuItem.setMnemonic('e');
        editUserMenuItem.setText("Edit Username");
        editUserMenuItem.setToolTipText("Edit the current user's name");
        editUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(editUserMenuItem);

        deleteUserMenuItem.setMnemonic('d');
        deleteUserMenuItem.setText("Delete Current User");
        deleteUserMenuItem.setToolTipText("Delete user that is currently player");
        deleteUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(deleteUserMenuItem);

        searchUserMenuItem.setMnemonic('r');
        searchUserMenuItem.setText("Search User");
        searchUserMenuItem.setToolTipText("Search for a user, and switch to that user if it is found");
        searchUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(searchUserMenuItem);
        gameMenu.add(jSeparator4);

        statisticsMenuItem.setMnemonic('i');
        statisticsMenuItem.setText("Display Statistics");
        statisticsMenuItem.setToolTipText("Display the current user's statistics");
        statisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(statisticsMenuItem);

        highScoreMenuItem.setMnemonic('h');
        highScoreMenuItem.setText("Display High Scores");
        highScoreMenuItem.setToolTipText("Display the high scores");
        highScoreMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highScoreMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(highScoreMenuItem);
        gameMenu.add(jSeparator1);

        shffleMenuItem.setMnemonic('s');
        shffleMenuItem.setText("Shuffle");
        shffleMenuItem.setToolTipText("Shuffle the current hand (display different cards)");
        shffleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shffleMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(shffleMenuItem);

        evaulateMenuItem.setMnemonic('v');
        evaulateMenuItem.setText("Evaluate");
        evaulateMenuItem.setToolTipText("Evalutate an Expression");
        evaulateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaulateMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(evaulateMenuItem);

        cardsMenuBar.add(gameMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");
        helpMenu.setToolTipText("Help has arrived");

        aboutMenuItem.setMnemonic('t');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("About this project");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        cardsMenuBar.add(helpMenu);

        setJMenuBar(cardsMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Shuffles the deck, calls the displayCards method, then saves our
     * players.
     * @param evt 
     */
    private void shuffleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleButtonActionPerformed
        this.player.incrementTotalGames();
//        this.player.addPossibleScore(...); // Implement this after generation
        this.currentScore = 0;
        this.deck.resetHand();
        displayCards();
        this.expressionTextField.setText("");
        this.expressionTextField.requestFocus();
        savePlayers();
    }//GEN-LAST:event_shuffleButtonActionPerformed

    /**
     * Evaluates an expression, determines whether it is valid or redundant.
     * If it is unique and valid then the current score and the player's
     * score is incremented. If the current score is larger that the player's
     * high score, the high score is updated.
     * @param evt 
     */
    private void expressionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expressionButtonActionPerformed
        String expression = this.expressionTextField.getText();
        if (expression != null && expression.length() > 0) {
            try {
                double number = evaluateInfixExpression(expression);
                if (!validNumsInExpression(expression)) {
                    JOptionPane.showMessageDialog(null,
                        "You may only use the numbers given on the cards",
                        "Invalid expression.",
                        JOptionPane.INFORMATION_MESSAGE);
                } else if (!EvaluateExpression.closeEnough(number, 24.0)) {
                    this.validLabel.setText("Incorrect Expression");
                } else if (!redundoValido.validateExpression(expression)) { 
                    JOptionPane.showMessageDialog(null,
                        "You may only use expressions you haven't used before."
                       + "\n\nYou have used: \n\n" 
                       + redundoValido.getGeneralForms(),
                        "Redundant expression.",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // If the double returned by evaluating our expression
                    // is close enough to 24 as to take into consideration 
                    // round off error, and isn't redundant to an expression
                    // which has already been used.
                    this.validLabel.setText("Valid Expression");
                    this.currentScore++;
                    this.player.addTotalScore(1);
                    this.player.checkIfHighScore(currentScore);
                    savePlayers();
                }
            } catch (EmptyStackException | FullStackException ex) {
                this.validLabel.setText("Invalid Expression");
            } 
        } else {
            JOptionPane.showMessageDialog(null, "No expression given.",
                    "An expression is needed to evaluate one.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        displayUserStats();
    }//GEN-LAST:event_expressionButtonActionPerformed

    /**
     * Exits the program.
     * @param evt 
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        shuffleButtonActionPerformed(evt);
        savePlayers();
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     * Allows the player to switch which user is currently being played.
     * @param evt 
     */
    private void switchUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchUserMenuItemActionPerformed
        shuffleButtonActionPerformed(evt);
        this.player = selectPlayer();
        displayUserStats();
        savePlayers();
    }//GEN-LAST:event_switchUserMenuItemActionPerformed

    /**
     * Allows the user to delete the current Player. This action is confirmed
     * before it is performed.
     * @param evt 
     */
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
            displayUserStats();
            savePlayers();
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_deleteUserMenuItemActionPerformed

    /**
     * Shuffles the deck by calling the corresponding button. 
     * @param evt 
     */
    private void shffleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shffleMenuItemActionPerformed
        shuffleButtonActionPerformed(evt);
    }//GEN-LAST:event_shffleMenuItemActionPerformed

    /**
     * Evaluates the current expression by calling the corresponding button.
     * @param evt 
     */
    private void evaulateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaulateMenuItemActionPerformed
        expressionButtonActionPerformed(evt);
    }//GEN-LAST:event_evaulateMenuItemActionPerformed

    /**
     * Allows the user to edit the Current player's name.
     * @param evt 
     */
    private void editUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserMenuItemActionPerformed
        String playerName = JOptionPane.showInputDialog(this, 
                "Edit Player Name:",
                "Enter a Valid name",
                JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.length() == 0) {
            JOptionPane.showMessageDialog(null, "No name given.",
                    "New player not created...",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (isValidName(playerName)) {
            this.player.setName(playerName);
            savePlayers();
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Invalid name given."
                  + "\nName cannot be 'Guest'"
                  + "\n\nName must be less than 8 Characters long.",
                    "Player name not edited...",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        displayUserStats();
    }//GEN-LAST:event_editUserMenuItemActionPerformed

    /**
     * Allows the user to search for a Player in the database, and if that
     * player is found the current Player is switched to the found player.
     * @param evt 
     */
    private void searchUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserMenuItemActionPerformed
        savePlayers();
        shuffleButtonActionPerformed(evt);
        String playerName = JOptionPane.showInputDialog(this, 
                "Search for Player Name:",
                "Enter a Valid name to search",
                JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.length() == 0) {
            JOptionPane.showMessageDialog(null, "No name given.",
                    "Player search not performed...",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            int index = linearPlayerSearch(playerName);
            if (index != -1) {
                JOptionPane.showMessageDialog(null, 
                    "Player: " + players.get(index).getName() 
                            + ", has been found. \nSwitching to this player.",
                    "Switching players",
                    JOptionPane.INFORMATION_MESSAGE);
                this.player =  players.get(index);
                savePlayers();
                displayUserStats();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Player: " + playerName 
                            + ", has not been found.",
                    "Search Failed",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_searchUserMenuItemActionPerformed

    /**
     * Saves the Player Database. Not really necessary, but was in the project
     * requirements.
     * @param evt 
     */
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        savePlayers();
    }//GEN-LAST:event_saveMenuItemActionPerformed

    /**
     * Spawns the about form, which shows information about this project.
     * @param evt 
     */
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        AboutForm about = new AboutForm();
        about.setVisible(true);
        about.dispose();
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    /**
     * Prints out the entire GUI.
     * @param evt 
     */
    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printMenuItemActionPerformed

    /**
     * Prints only the current user's statistics.
     * @param evt 
     */
    private void printStatisticsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printStatisticsMenuItemActionPerformed
        PrintUtilities.printComponent(this.statisticsPanel);
    }//GEN-LAST:event_printStatisticsMenuItemActionPerformed

    /**
     * Shows an extended version of the user's statistics.
     * @param evt 
     */
    private void statisticsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsMenuItemActionPerformed
        StatisticsPanel stats = new StatisticsPanel(player, currentScore);
        stats.setVisible(true);
        stats.dispose();
    }//GEN-LAST:event_statisticsMenuItemActionPerformed

    /**
     * Displays a list of the 10 highest scores.
     * @param evt 
     */
    private void highScoreMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highScoreMenuItemActionPerformed
        ArrayList<Player> sortedPlayers = new ArrayList<>(this.players);
        insertionSort(sortedPlayers);
        String highScores = "";
        for (int i = 0; i < sortedPlayers.size() && i < 10; i++) {
            Player tempPlayer = sortedPlayers.get(i);
            highScores += (i+1) + ". " + tempPlayer.getName() 
                    + ": " + tempPlayer.getHighScore() + "\n";
        }
        JOptionPane.showMessageDialog(null, 
            "High Scores:\n" + highScores,
            "Score Board",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_highScoreMenuItemActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            new CardGame24GUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel cardFourLabel;
    private javax.swing.JLabel cardOneLabel;
    private javax.swing.JLabel cardThreeLabel;
    private javax.swing.JLabel cardTwoLabel;
    private javax.swing.JMenuBar cardsMenuBar;
    private javax.swing.JLabel currentScoreLabel;
    private javax.swing.JTextField currentScoreTextField;
    private javax.swing.JMenuItem deleteUserMenuItem;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JMenuItem editUserMenuItem;
    private javax.swing.JMenuItem evaulateMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton expressionButton;
    private javax.swing.JLabel expressionLabel;
    private javax.swing.JPanel expressionPanel;
    private javax.swing.JTextField expressionTextField;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel highScoreLabel;
    private javax.swing.JMenuItem highScoreMenuItem;
    private javax.swing.JTextField highScoreTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem printStatisticsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem searchUserMenuItem;
    private javax.swing.JMenuItem shffleMenuItem;
    private javax.swing.JButton shuffleButton;
    private javax.swing.JMenuItem statisticsMenuItem;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JMenuItem switchUserMenuItem;
    private javax.swing.JLabel totalScoreLabel;
    private javax.swing.JTextField totalScoreTextField;
    private javax.swing.JPanel upperControlPanel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userTextField;
    private javax.swing.JLabel validLabel;
    // End of variables declaration//GEN-END:variables
}
