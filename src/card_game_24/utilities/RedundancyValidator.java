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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class RedundancyValidator {
    private ArrayList<String> generalForms;
    private HashSet<String> currentHand;
    
    public RedundancyValidator(int[] hand) {
        
        generalForms = new ArrayList<>();
        currentHand = createHashHandSet(createHandStringArray(hand));
    }
    
    private String[] createHandStringArray(int[] hand) {
        String[] handString = new String[hand.length];
        for (int i = 0; i < hand.length; i++) {
            int number = (hand[i] % 13 == 0) ? 13 : hand[i] % 13;
            handString[i] = String.valueOf(number);
        }
        return handString;
    }
    
    private HashSet<String> createHashHandSet(String[] hand) {
        HashSet<String> set = new HashSet();
        for (String string : hand) {
            set.add(string);
        }
        return set;
    }
    
    public void clearList() {
        generalForms.clear();
    }
    
    public String createForm(String express) {
        String[] vars = new String[]{"a", "b", "c", "d"};
        express = express.replaceFirst("\\d+", vars[0]);
        express = express.replaceFirst("\\d+", vars[1]);
        express = express.replaceFirst("\\d+", vars[2]);
        express = express.replaceFirst("\\d+", vars[3]);
        return express;
    }
    
    public ArrayList<String> getGeneralForms() {
        return this.generalForms;
    }
    
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
                    testExpression = testExpression.replaceAll(number, testInt[i]);
                    genExpression = genExpression.replaceAll(number, testInt[i]);
                    i++;
                }
                
                if (closeEnough(evaluateInfixExpression(testExpression), 
                        evaluateInfixExpression(genExpression))) {
                    counter++;
                }
//                System.out.println(testExpression + " :vs: " + genExpression 
//                    + ", Yields: " + evaluateInfixExpression(testExpression) 
//                        + " :vs: " + evaluateInfixExpression(genExpression));
            }
            if (counter == 4) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        int[] hand = new int[]{3, 5, 8, 2};
        RedundancyValidator valido = new RedundancyValidator(hand);
        System.out.println(valido.createForm("(3 - 5) * 8 / 2"));
        System.out.println(valido.validateExpression("(3 - 5) * 8 / 2"));
        System.out.println(valido.validateExpression("8 / 2 * (3 - 5)"));
        
        System.out.println(valido.validateExpression("8 / 2 * (5 + 3)"));
        System.out.println(valido.validateExpression("8 / 2 * (3 + 5)"));
        
        System.out.println(valido.validateExpression("8 / 2 * (3 * 5)"));
        System.out.println(valido.validateExpression("(3 * 8 / 2 * 5)"));
        
        System.out.println(valido.validateExpression("8 - 2 * (3 + 5)"));
        System.out.println(valido.validateExpression("(8 - 2) * (3 + 5)"));
        
        
        System.out.println(valido.validateExpression("8 / 2 - (3 - 5)"));
        System.out.println(valido.validateExpression("8 / 2 + (5 - 3)"));
    }
}
