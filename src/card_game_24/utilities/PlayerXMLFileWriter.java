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
package card_game_24.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import card_game_24.objects.Player;


/**
 * PlayerCSVFileWriter.java
 * This class provides an abstraction to write our database.
 * Using XML! For extra credit.
 * <pre>
    Project: 24 Card Game
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on May 10, 2016, 2:09 PM
    Revised on May 23, 2016, 5:30 PM
 </pre>
 * @author Thomas Kercheval
 */
public class PlayerXMLFileWriter {
    /** The file name of our database */
    private String fileName;
    /** ArrayList of Player Objects which we are writing to our database. */
    private ArrayList<Player> players;
    
    /**
     * Sets the list of Players we wish to save and the location at which we
     * will save our database.
     * @param playerList The list of Players we will write.
     * @param filePath The path of the database we wish to write.
     */
    public PlayerXMLFileWriter(ArrayList<Player> playerList, String filePath) {
        this.fileName = filePath;
        this.players = new ArrayList(playerList);
    }
    
    /**
     * Creates an empty XML file at the location of our choice.
     * @param filePath The path of the database we wish to write.
     */
    public PlayerXMLFileWriter(String filePath) {
        this.fileName = filePath;
        this.players = new ArrayList<>();
    }
    
    /**
     * Creates the XML file which stores our player information.
     */
    public void createXMLFile() {
        Element root = new Element("Players");
        Document xmlDatabase = new Document();
        
        for (int i = 0; i < players.size(); i++) {
            Player playerObj = players.get(i);
            Element playerXML = new Element("Player");
            playerXML.addContent(new Element("name").addContent(
                    playerObj.getName()));
            playerXML.addContent(new Element("highScore").addContent(
                    String.valueOf(playerObj.getHighScore())));
            playerXML.addContent(new Element("totalScore").addContent(
                    String.valueOf(playerObj.getTotalScore())));
            playerXML.addContent(new Element("possibleScore").addContent(
                    String.valueOf(playerObj.getPossibleScore())));
            playerXML.addContent(new Element("totalGames").addContent(
                    String.valueOf(playerObj.getTotalGames())));
            root.addContent(playerXML);
        }
        
        xmlDatabase.setRootElement(root);
        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        try {
            outter.output(xmlDatabase, new FileWriter(new File(fileName)));
        } catch (IOException ex) {
            Logger.getLogger(PlayerXMLFileWriter.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
