/*
 * Copyright (C) 2016 Thomas Kercehval
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

/**
 * This class represents a Player, with a name, total score, high score,
 * total number of games played, and total possible points that could have
 * been achieved for every one of those games.
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 23, 2016, 3:03 PM
 * Revised on May 23, 2016, 3:59 PM
 * 
 * @author Thomas Kercheval
 */
public class Player extends Person implements Comparable<Player> {
    /** The highest score this player achieved in a hand. */
    private int highScore;
    /** The total score this player has achieved in all hands. */
    private int totalScore;
    /** 
     * The total possible score the player could have achieved if all
     * points were gained in each game. This score will only be available
     * if program can generate all possible combinations to 24.
     */
    private int possibleScore;
    /** Total games the player has played. */
    private int totalGames;
    
    /** The index of the name of our player. */
    public static final int NAME_INDEX = 0;
    /** The index of the highest score a player has made. */
    public static final int HIGH_SCORE_INDEX = 1;
    /** The index of the total score a player has scored. */
    public static final int TOTAL_SCORE_INDEX = 2;
    /** The index of the total possible point a player could have scored. */
    public static final int POSSIBLE_SCORE_INDEX = 3;
    /** The index of the total games played by a player. */
    public static final int TOTAL_GAMES_INDEX = 4;
    
    /**
     * Default constructor. Generates a new player with a name and no score
     * statistics.
     * @param namePerson The name of this Player.
     */
    public Player(String namePerson) {
        super(namePerson);
        highScore = 0;
        totalScore = 0;
        possibleScore = 0;
        totalGames = 0;
    }
    
    /**
     * Overloaded constructor. Generates an extant player with a name existing
     * score statistics.
     * @param namePerson The name of this Player.
     * @param scoreHigh The highest score this player achieved in a hand.
     * @param scoreTotal The total points this player has scored.
     * @param scorePossible The total possible score the player could have 
     * achieved if all points were gained in each game.
     * @param gamesTotal The total amount of games this player has played.
     */
    public Player(String namePerson, int scoreHigh, int scoreTotal, 
            int scorePossible, int gamesTotal) {
        super(namePerson);
        this.highScore = scoreHigh;
        this.totalScore = scoreTotal;
        this.possibleScore = scorePossible;
        this.totalGames = gamesTotal;
    }
    
    /**
     * Array constructor, instantiates a new Player using an array grabbed
     * from the XML file storing all of our players information. The indices
     * in this array are associated with specific information and those
     * relations are stored as constants in this class.
     * @param playerInfo The array with our player's information indexed
     * by integer constants in this class.
     */
    public Player(String[] playerInfo) {
        super(playerInfo[NAME_INDEX]);
        this.highScore = Integer.valueOf(playerInfo[HIGH_SCORE_INDEX]);
        this.totalScore = Integer.valueOf(playerInfo[TOTAL_SCORE_INDEX]);
        this.possibleScore = Integer.valueOf(playerInfo[POSSIBLE_SCORE_INDEX]);
        this.totalGames = Integer.valueOf(playerInfo[TOTAL_GAMES_INDEX]);
    }

    /**
     * Copy constructor, creates a clone of another player.
     * @param player The player we are copying.
     */
    public Player(Player player) {
        super(player.getName());
        this.highScore = player.getHighScore();
        this.totalScore = player.getTotalScore();
        this.possibleScore = player.getPossibleScore();
        this.totalGames = player.getTotalGames();
    }
    
    /**
     * @return The high score of this player.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * @param highScore The high score of the player.
     */
    private void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * @return This player's total score between all games.
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore This player's total score between all games.
     */
    private void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * @return The total possible score this player could have earned between
     * all games.
     */
    public int getPossibleScore() {
        return possibleScore;
    }

    /**
     * @param possibleScore The total possible score this player could have 
     * earned between all games.
     */
    private void setPossibleScore(int possibleScore) {
        this.possibleScore = possibleScore;
    }

    /**
     * @return The total amount of games this player has played.
     */
    public int getTotalGames() {
        return totalGames;
    }

    /**
     * @param totalGames The total amount of games this player has played.
     */
    private void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
    
    /** Increases the total number of games player by one. */
    public void incrementTotalGames() {
        this.totalGames++;
    }
    
    /**
     * Adds an amount of score to the total earned by the player.
     * @param amount The amount to add to the total.
     */
    public void addTotalScore(int amount) {
        checkIfHighScore(amount);
        this.totalScore += amount;
    }
    
    /**
     * Checks to see if the amount being added to the total score is greater
     * than the current high score, and if it is updates the high score.
     * @param amount The amount we are checking against the high score.
     */
    public void checkIfHighScore(int amount) {
        if (amount > this.highScore) {
            this.highScore = amount;
        }
    }
    
    /**
     * Add an amount of score that this player could've possibly earned.
     * @param amount The amount of possible score we are adding to this player.
     */
    public void addPossibleScore(int amount) {
        this.possibleScore += amount;
    }
    
    @Override
    /**
     * Compares one Player to this one by calling the compareTo method on
     * both players names.
     * @return Returns The compareTo of the Integer class between the two
     * players high score.
     */
    public int compareTo(Player otherPlayer) {
        return (new Integer(getHighScore()))
                .compareTo(otherPlayer.getHighScore());
    }
}
