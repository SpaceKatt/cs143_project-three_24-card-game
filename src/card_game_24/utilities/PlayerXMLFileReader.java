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

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import card_game_24.objects.Player;


/**
 * PlayerCSVFileReader.java
 * This class provides an abstraction to read our database one line at a time.
 * Using XML! For extra credit.
 * <pre>
    Project: 24 Card Game
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on May 11, 2016, 2:08:43 PM
    Revised on May 23, 2016, 5:27 PM
 </pre>
 * @author Thomas Kercheval
 */
public class PlayerXMLFileReader {
    /** The ArrayList of String arrays which will host each Players's info. */
    ArrayList<String[]> playerInfo;
    /** The file path of the database we will read from. */
    String filePath;
    
    /**
     * Initializes the ArrayList in which we will store the arrays with each
     * Player's information and save the file name of the database (passed to
     * this constructor).
     * @param fileName The name of the file we are reading from.
     */
    public PlayerXMLFileReader(String fileName) {
        filePath = fileName;
        playerInfo = new ArrayList<>();
        readFile();
    }
    
    /**
     * Reads the XML file `this.filePath` and creates an ArrayList of String
     * arrays, each containing the complete information of a Player.
     */
    private void readFile() {
        try {
            File database = new File(this.filePath);
            
            SAXBuilder builder = new SAXBuilder();

            Document document = builder.build(database);
            Element playersElement = document.getRootElement();
            List<Element> playerList = playersElement.getChildren();
            for (int i = 0; i < playerList.size(); i++) {
                Element player = playerList.get(i);
                String[] info = new String[]{
                    player.getChildText("name"),
                    player.getChildText("highScore"),                
                    player.getChildText("totalScore"),
                    player.getChildText("possibleScore"),
                    player.getChildText("totalGames")
                };
                this.playerInfo.add(info);
            }
        } catch(JDOMException | IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @return The ArrayList with all of our Players information.
     */
    public ArrayList<String[]> getPlayerInformation() {
        return this.playerInfo;
    }
}