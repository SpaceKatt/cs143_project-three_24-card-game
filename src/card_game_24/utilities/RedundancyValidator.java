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

import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import static card_game_24.utilities.EvaluateExpression.closeEnough;
import static card_game_24.utilities.EvaluateExpression.evaluateInfixExpression;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class allows us to determine if a sequence of mathematical expressions
 * involving a set of four numbers are all unique, or which ones are unique.
 * All redundant expressions will not be recorded, and false will be returned
 * by validateExpression(String express) if express is equivalent to a 
 * previously validated expression.
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 27, 2016, 2:31 PM
 * Revised on May 28, 2016, 3:46 PM
 * 
 * @author Thomas Kercheval
 */
public class RedundancyValidator {
    /** All unique expressions for this hand. */
    private ArrayList<String> generalForms;
    /** The current set of numbers we are validating expressions for. */
    private HashSet<String> currentHand;
    
    /**
     * Initializes our validator with an empty solution set, and a set of 
     * numbers our expressions will contain.
     * @param hand The list of numbers we are validating, though need to be
     * processed by (% 13) in order to reflect the literal values of the cards.
     */
    public RedundancyValidator(int[] hand) {
        generalForms = new ArrayList<>();
        currentHand = createHashHandSet(createHandStringArray(hand));
    }
    
    /**
     * Since we are given an int[] through the constructor of values, that do
     * not reflect the literal value of the cards they represent, we need to 
     * modify them and store then all in a String array before we may
     * feed them into a HashSet.
     * @param hand The list of numbers we are validating, though need to be
     * processed by (% 13) in order to reflect the literal values of the cards.
     * @return String array representation of the literal values of the current
     * hand we are validating expressions for.
     */
    private String[] createHandStringArray(int[] hand) {
        String[] handString = new String[hand.length];
        for (int i = 0; i < hand.length; i++) {
            int number = (hand[i] % 13 == 0) ? 13 : hand[i] % 13;
            handString[i] = String.valueOf(number);
        }
        return handString;
    }
    
    /**
     * From our String array we must eliminate all duplicate elements, so we
     * create a HashSet from this array.
     * @param hand The String array of numbers we are validating expressions
     * for.
     * @return HashSet of the numbers we are validating expressions for.
     */
    private HashSet<String> createHashHandSet(String[] hand) {
        HashSet<String> set = new HashSet();
        for (String string : hand) {
            set.add(string);
        }
        return set;
    }
    
    /**
     * Clears our list of unique solutions.
     */
    public void clearList() {
        generalForms.clear();
    }
    
    /**
     * Create general representation of an expression form (i.e., replace
     * all numbers with variables).
     * @param express The expression we want a general form of.
     * @return The general for of an expression.
     */
    public String createForm(String express) {
        String[] vars = new String[]{"a", "b", "c", "d"};
        express = express.replaceFirst("\\d+", vars[0]);
        express = express.replaceFirst("\\d+", vars[1]);
        express = express.replaceFirst("\\d+", vars[2]);
        express = express.replaceFirst("\\d+", vars[3]);
        return express;
    }
    
    /**
     * @return The set of all our solutions.
     */
    public ArrayList<String> getGeneralForms() {
        return this.generalForms;
    }
    
    /**
     * Validate a single expression by comparing it to all previously found
     * solutions. If an expression is not redundant, add it to the list of
     * all unique solutions.
     * @param express The expression we wish to validate.
     * @return true if the expression is valid and nonredundant.
     */
    public boolean validateExpression(String express) {
        boolean redundant;
        if (!generalForms.isEmpty()) {
            try {
                redundant = isRedundant(express);
            } catch (EmptyStackException | FullStackException ex) {
                System.out.println("This won't happen");
                return false;
            } 
            if (redundant == false) {
                this.generalForms.add(express);
            }
        } else {
            this.generalForms.add(express);
            redundant = false;
        }
        return !redundant;
    }
    
    /**
     * Tests to see if an expression is redundant. This is done by comparing
     * express against every found, unique solution. We compare two expressions
     * by replacing the numbers in the current hand (and therefore in both
     * expressions) with arbitrary test numbers and see if both expressions
     * evaluate to the same number for every substitution we make. Of course, 
     * we need to replace every instance of a number, in both expressions, with
     * the same test number, which is why the current hand is stored in a set.
     * So, if expression [1] is: (a+b+c)*d and expression [2] is d*(c+b+a), 
     * when we perform a substitution, say (a = 1, b = 2, c = 3, d =4), [1]
     * reads: (1+2+3)+4 and [2] reads 4*(3+2+1). If both [1] and [2] are 
     * equivalent for any arbitrary choice of substitution, then they are
     * redundant.
     * @param express The expression we are testing for redundancy.
     * @return True if the expression is redundant.
     * @throws EmptyStackException This will be thrown if an invalid expression
     * is given (i.e., a syntax error).
     * @throws FullStackException I don't know when this would be thrown.
     */
    public boolean isRedundant(String express) 
            throws EmptyStackException, FullStackException {
        String[][] testInts = {
            {"1", "3", "5", "7"},
            {"3", "3", "7", "9"},
            {"7", "5", "4", "9"},
            {"2", "3", "5", "2"}
        };
        
        for (String form: this.generalForms) {
            int counter = 0;
            for (String[] testInt : testInts) {
                int i = 0;
                String testExpression = express;
                String genExpression = form;
                for (String number: this.currentHand) {
                    testExpression = testExpression.replaceAll(number, 
                            testInt[i]);
                    genExpression = genExpression.replaceAll(number, 
                            testInt[i]);
                    i++;
                }
                
                if (closeEnough(evaluateInfixExpression(testExpression), 
                        evaluateInfixExpression(genExpression))) {
                    counter++;
                }
            }
            if (counter == 4) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Provides a test case.
     * @param args 
     */
    public static void main(String[] args) {
        int[] hand = new int[]{3, 5, 8, 2};
        RedundancyValidator valido = new RedundancyValidator(hand);
        System.out.println(valido.createForm("(3 - 5) * 8 / 2"));
        
        //True
        System.out.println(valido.validateExpression("(3 - 5) * 8 / 2"));
        //False
        System.out.println(valido.validateExpression("8 / 2 * (3 - 5)"));
        
        //True
        System.out.println(valido.validateExpression("8 / 2 * (5 + 3)"));
        //False
        System.out.println(valido.validateExpression("8 / 2 * (3 + 5)"));
        
        //True
        System.out.println(valido.validateExpression("8 / 2 * (3 * 5)"));
        //False
        System.out.println(valido.validateExpression("(3 * 8 / 2 * 5)"));
        
        //True
        System.out.println(valido.validateExpression("8 - 2 * (3 + 5)"));
        //True
        System.out.println(valido.validateExpression("(8 - 2) * (3 + 5)"));
        
        //True
        System.out.println(valido.validateExpression("8 / 2 - (3 - 5)"));
        //False
        System.out.println(valido.validateExpression("8 / 2 + (5 - 3)"));
    }
}
