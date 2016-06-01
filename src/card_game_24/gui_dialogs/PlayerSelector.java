/*
 * Copyright (C) 2016 Thomas
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import card_game_24.utilities.PlayerXMLFileWriter;
import javax.swing.JOptionPane;

/**
 * A form by which the User may select Player to play the 24 Card Game with.
 *
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 2, 2016, 2:31 PM 
 * Revised on May 23, 2016, 6:33 PM
 *
 * @author thomas.kercheval
 */
public class PlayerSelector extends javax.swing.JDialog {
    /** The set of Player objects the user has an option to choose from. */
    private ArrayList<Player> playerObjects;
    /** The Names of every Player in the ArrayList playerObjects. */
    private ArrayList<String> playerNames;
    /** The Player selected by the user for play. */
    private Player selectedPlayer;
    
    /**
     * Creates new form PlayerSelector
     */
    public PlayerSelector(ArrayList<Player> players) {
        initComponents();
        this.setModal(true);
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.selectButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/card_game_24/images/cards/king_of_hearts.png"));
        this.playerObjects = players;
        DefaultListModel model = new DefaultListModel();
        insertionSort(playerObjects);
        playerNames = grabPlayerNames(playerObjects);
        for (int i = 0; i < playerNames.size(); i++) {
            model.addElement(playerNames.get(i));
        }
        this.playerList.setModel(model);
        this.playerList.setSelectedIndex(0);
    }
    
    /** @return Player which will be the user in the game.  */
    public Player getPlayer() {
        return this.selectedPlayer;
    }
    
    /**
     * From an ArrayList of Player objects, an ArrayList containing their
     * names is returned.
     * @param users The ArrayList of Player Objects.
     * @return An ArrayList of the Player Object names.
     */
    private ArrayList<String> grabPlayerNames(ArrayList<Player> users) {
        ArrayList<String> names = new ArrayList<>();
        for (Player user: users) {
            names.add(user.getName());
        }
        return names;
    }
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSort(ArrayList<Player> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && less(list.get(j).getName(), 
                    list.get(j - 1).getName()); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
   /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. 
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean less(String one, String two) {
        return one.compareTo(two) < 0;
    }
    
   /**
     * This method swaps two specified elements of an ArrayList.
     * @param list ArrayList of Comparable objects
     * @param indexOne Index of first item we want to swap
     * @param indexTwo Index of second item we want to swap
     */
    public static void swap(ArrayList<Player> list, int indexOne, 
                            int indexTwo) {
        Player temp = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, temp);
    }
    
//    /**
//     * Grabs all the file names in `src/card_game_24/data` so we can give
// the user a list of playerNames to choose from.
//     * @return An arraylist of database file paths.
//     */
//    public static ArrayList<String> grabFileNames() {
//        File folder = new File("src/card_game_24/data");
//        File[] listOfFiles = folder.listFiles();
//        ArrayList<String> list = new ArrayList<>();
//        for (File file : listOfFiles) {
//            list.add(file.toString());
//        }
//        return list;
//    }

//    /**
//     * Generates a String so we can name our new Database uniquely.
//     * @return String of todays date, made unique by integer addition.
//     */
//    public String generateFileName() {
//        Date today = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yy");
//        String dateNow = "src/card_game_24/data/" + dateFormat.format(today);
//        String canidateName = dateNow;
//        int additionalPart = 0;
//        while (fileAlreadyExists(canidateName + ".xml")) {
//            additionalPart++;
//            canidateName = dateNow + "-" + additionalPart;
//        }
//        return canidateName + ".xml";
//    }
//    
//    /** 
//     * @return true if the file name already exists
//     * @param name Name of the file we are looking for
//     */ 
//    private boolean fileAlreadyExists(String name) {
//        if (!playerNames.isEmpty() && playerNames.get(0).contains("\\")) {
//            name = name.replace("/", "\\");
//        }
//        return this.playerNames.contains(name);     
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList<>();
        controlPanel = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        openCancelPanel = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        guestButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        playaaLabel = new javax.swing.JLabel();
        sellLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select a Player");
        setResizable(false);

        playerList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        playerList.setToolTipText("The current extant players, click to select one and press Enter");
        jScrollPane1.setViewportView(playerList);

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.GridLayout(2, 1));

        newButton.setMnemonic('n');
        newButton.setText("New");
        newButton.setToolTipText("Create a new player, you will be asked for a name");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        controlPanel.add(newButton);

        openCancelPanel.setLayout(new java.awt.GridLayout(1, 3));

        selectButton.setMnemonic('s');
        selectButton.setText("Select");
        selectButton.setToolTipText("Open selected player");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });
        openCancelPanel.add(selectButton);

        guestButton.setMnemonic('g');
        guestButton.setText("Guest");
        guestButton.setToolTipText("Use guest account, stats will not be saved");
        guestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestButtonActionPerformed(evt);
            }
        });
        openCancelPanel.add(guestButton);

        exitButton.setMnemonic('c');
        exitButton.setText("Cancel");
        exitButton.setToolTipText("Cancel Player selection");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        openCancelPanel.add(exitButton);

        controlPanel.add(openCancelPanel);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        playaaLabel.setFont(new java.awt.Font("AR JULIAN", 0, 24)); // NOI18N
        playaaLabel.setText("Player");

        sellLabel.setFont(new java.awt.Font("AR JULIAN", 0, 24)); // NOI18N
        sellLabel.setText("Select");

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/card_game_24/images/kinglogo-panel.png"))); // NOI18N

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(playaaLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(sellLabel)
                        .addGap(27, 27, 27))))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(playaaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellLabel))
                    .addComponent(logoLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exits our application.
     * @param evt The event which triggers this listener.
     */
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        selectedPlayer = null;
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    /**
     * Saves the name of the currently selected database and closes the \
     * Dialog.
     * @param evt The event which triggers this listener.
     */
    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        selectedPlayer = playerObjects.get(playerList.getSelectedIndex());
        this.dispose();
    }//GEN-LAST:event_selectButtonActionPerformed

    /**
     * Generates a new, unique name by calling generateFileName() and closes
     * the Dialog.
     * @param evt The event which triggers this listener.
     */
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        String playerName = JOptionPane.showInputDialog(this, 
                "New Player Name:",
                "Enter a Valid name",
                JOptionPane.PLAIN_MESSAGE);
        if (playerName == null) {
            JOptionPane.showMessageDialog(null, "No name given.",
                    "New player not created...",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (isValidName(playerName)) {
            this.selectedPlayer = new Player(playerName);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Invalid name given."
                  + "\nName cannot be 'Guest'",
                    "New player not created...",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_newButtonActionPerformed

    /**
     * Creates a guest account for this game.
     * @param evt The event which triggers this listener.
     */
    private void guestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestButtonActionPerformed
        this.selectedPlayer = new Player("Guest");
        this.dispose();
    }//GEN-LAST:event_guestButtonActionPerformed

    /**
     * Determines if a given name is valid for our application.
     * @param name The name we are validating.
     * @return True if the name is valid.
     */
    public static boolean isValidName(String name) {
        return !name.equals("Guest") && !name.equals("guest");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton guestButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton newButton;
    private javax.swing.JPanel openCancelPanel;
    private javax.swing.JLabel playaaLabel;
    private javax.swing.JList<String> playerList;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel sellLabel;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
