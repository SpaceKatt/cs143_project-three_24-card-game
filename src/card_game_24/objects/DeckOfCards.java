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
package card_game_24.objects;

import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * This class represents a Deck of Cards. Hearts (1-13), 
     * Diamonds (14-26), Clubs (27-39), then Spades(40-52).
 * 
 * Card images found at: 
 *     http://opengameart.org/content/playing-cards-vector-png
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 23, 2016, 4:02 PM
 * Revised on May 23, 2016, 5:07 PM
 * 
 * @author Thomas Kercheval
 */
public class DeckOfCards {
    /**
     * ImageIcons of all the cards in the deck. Hearts (1-13), 
     * Diamonds (14-26), Clubs (27-39), then Spades(40-52).
     */
    private HashMap cards;
    /**
     * The current hand being played, integer numbers 1-52 representing
     * which card is in play.
     */
    private int[] hand;
    
    /**
     * Default constructor, creates the deck of cards and draws a hand to play.
     */
    public DeckOfCards() {
        this.cards = createCards();
        this.hand = drawHand();
    }
    
    /**
     * Resets the hand which the player can choose cards from.
     */
    public void resetHand() {
        this.hand = drawHand();
    }
    
    /**
     * Draws four unique cards from the deck, and returns their indices to
     * the caller.
     * @return An int array of the cards indices which were drawn.
     */
    private int[] drawHand() {
        Random rando = new Random();
        int[] newDraw = new int[4];
        int count = 0;
        while (count < 4) {
            int number = rando.nextInt(52) + 1;
            if (!isNumberInHand(number, newDraw)) {
                newDraw[count++] = number;
            }
        }
        return newDraw;
    }
    
    /**
     * @return The current hand of the Game.
     */
    public int[] getHand() {
        return this.hand;
    }
    
    /**
     * @return The deck of cards, as ImageIcons, Hearts (1-13), 
     * Diamonds (14-26), Clubs (27-39), then Spades(40-52).
     */
    public HashMap getDeck() {
        return this.cards;
    }
    
    /**
     * Determines whether a number is already in an array.
     * @param number The number we are searching for.
     * @param arr The array we are searching through.
     * @return true if the number is found.
     */
    private boolean isNumberInHand(int number, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == number) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Generates a HashMap with 1-52 as keys and ImageIcons of the
     * corresponding cards as values. Key % 13 should give the corresponding
     * card value, though 13 will map to zero.
     * @return A HashMap with playing card ImageIcons.
     */
    private HashMap createCards() {
        HashMap deck = new HashMap();
        String pathStub = "src/card_game_24/images/cards";
        String ext = ".png";
        deck.put("1", new ImageIcon(pathStub + "ace_of_hearts" + ext));
        deck.put("2", new ImageIcon(pathStub + "2_of_hearts" + ext));
        deck.put("3", new ImageIcon(pathStub + "3_of_hearts" + ext));
        deck.put("4", new ImageIcon(pathStub + "4_of_hearts" + ext));
        deck.put("5", new ImageIcon(pathStub + "5_of_hearts" + ext));
        deck.put("6", new ImageIcon(pathStub + "6_of_hearts" + ext));
        deck.put("7", new ImageIcon(pathStub + "7_of_hearts" + ext));
        deck.put("8", new ImageIcon(pathStub + "8_of_hearts" + ext));
        deck.put("9", new ImageIcon(pathStub + "9_of_hearts" + ext));
        deck.put("10", new ImageIcon(pathStub + "10_of_hearts" + ext));
        deck.put("11", new ImageIcon(pathStub + "jack_of_hearts" + ext));
        deck.put("12", new ImageIcon(pathStub + "queen_of_hearts" + ext));
        deck.put("13", new ImageIcon(pathStub + "king_of_hearts" + ext));
        deck.put("14", new ImageIcon(pathStub + "ace_of_diamonds" + ext));
        deck.put("15", new ImageIcon(pathStub + "2_of_diamonds" + ext));
        deck.put("16", new ImageIcon(pathStub + "3_of_diamonds" + ext));
        deck.put("17", new ImageIcon(pathStub + "4_of_diamonds" + ext));
        deck.put("18", new ImageIcon(pathStub + "5_of_diamonds" + ext));
        deck.put("19", new ImageIcon(pathStub + "6_of_diamonds" + ext));
        deck.put("20", new ImageIcon(pathStub + "7_of_diamonds" + ext));
        deck.put("21", new ImageIcon(pathStub + "8_of_diamonds" + ext));
        deck.put("22", new ImageIcon(pathStub + "9_of_diamonds" + ext));
        deck.put("23", new ImageIcon(pathStub + "10_of_diamonds" + ext));
        deck.put("24", new ImageIcon(pathStub + "jack_of_diamonds" + ext));
        deck.put("25", new ImageIcon(pathStub + "queen_of_diamonds" + ext));
        deck.put("26", new ImageIcon(pathStub + "king_of_diamonds" + ext));
        deck.put("27", new ImageIcon(pathStub + "ace_of_clubs" + ext));
        deck.put("28", new ImageIcon(pathStub + "2_of_clubs" + ext));
        deck.put("29", new ImageIcon(pathStub + "3_of_clubs" + ext));
        deck.put("30", new ImageIcon(pathStub + "4_of_clubs" + ext));
        deck.put("31", new ImageIcon(pathStub + "5_of_clubs" + ext));
        deck.put("32", new ImageIcon(pathStub + "6_of_clubs" + ext));
        deck.put("33", new ImageIcon(pathStub + "7_of_clubs" + ext));
        deck.put("34", new ImageIcon(pathStub + "8_of_clubs" + ext));
        deck.put("35", new ImageIcon(pathStub + "9_of_clubs" + ext));
        deck.put("36", new ImageIcon(pathStub + "10_of_clubs" + ext));
        deck.put("37", new ImageIcon(pathStub + "jack_of_clubs" + ext));
        deck.put("38", new ImageIcon(pathStub + "queen_of_clubs" + ext));
        deck.put("39", new ImageIcon(pathStub + "king_of_clubs" + ext));
        deck.put("40", new ImageIcon(pathStub + "ace_of_spades" + ext));
        deck.put("41", new ImageIcon(pathStub + "2_of_spades" + ext));
        deck.put("42", new ImageIcon(pathStub + "3_of_spades" + ext));
        deck.put("43", new ImageIcon(pathStub + "4_of_spades" + ext));
        deck.put("44", new ImageIcon(pathStub + "5_of_spades" + ext));
        deck.put("45", new ImageIcon(pathStub + "6_of_spades" + ext));
        deck.put("46", new ImageIcon(pathStub + "7_of_spades" + ext));
        deck.put("47", new ImageIcon(pathStub + "8_of_spades" + ext));
        deck.put("48", new ImageIcon(pathStub + "9_of_spades" + ext));
        deck.put("49", new ImageIcon(pathStub + "10_of_spades" + ext));
        deck.put("50", new ImageIcon(pathStub + "jack_of_spades" + ext));
        deck.put("51", new ImageIcon(pathStub + "queen_of_spades" + ext));
        deck.put("52", new ImageIcon(pathStub + "king_of_spades" + ext));
        return deck;
    }
}
