package card_game_24.utilities;

import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import card_game_24.objects.GenericStack;
import java.util.Scanner;

/**
 * This class provides static methods to evaluate an arbitrary infix 
 * expression, insert blank spaces into mathematical equations, and to 
 * determine whether or not a double is close enough to the value 24 to
 * suggest equivalence miffed by round-off error.
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 19, 2016, 3:31 PM
 * Revised on May 28, 2016, 6:16 PM
 * 
 * @author Thomas Kercheval
 */
public class EvaluateExpression {
    /**
     * Using two GenericStack objects, we push operators and operands onto
     * their respective stacks until we hit a symbol which ranks high upon
     * the order of operations. Once we do so, we evaluate smaller expressions
     * by popping off two operands and one operator, then push the result
     * back onto the operand stack for future operations. The final result
     * is then popped off and returned. If the mathematical equation
     * is invalid (e.g., 1 ++ 2) then an EmptyStackException will be thrown.
     * @param expression The algebraic expression which we wish to parse.
     * @return Result of the algebraic expression.
     * @throws EmptyStackException Indicator of an invalid expression.
     * @throws FullStackException I don't know when this would be thrown.
     */
    public static double evaluateInfixExpression(String expression) 
            throws EmptyStackException, FullStackException {
        GenericStack<Double> operandStack = new GenericStack<>();
        GenericStack<Character> operatorStack = new GenericStack<>();
        expression = insertBlanks(expression);
        // Extract operands and operators
        String[] tokens = expression.split(" ");
        // Scan tokens
        for (String token: tokens) {
            if (token.length() == 0) {
            } else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
                while (!operatorStack.isEmpty() 
                        && (operatorStack.peek() == '+'
                        || operatorStack.peek() == '-'
                        || operatorStack.peek() == '*'
                        || operatorStack.peek() == '/')) { 
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
                while (!operatorStack.isEmpty() 
                        && (operatorStack.peek() == '*'
                        || operatorStack.peek() == '/')) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '(') {
                operatorStack.push('(');
            } else if (token.charAt(0) == ')') {
                // Process all the operators in the Stack until '('
                while (operatorStack.peek() != '(') {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.pop();
            } else {
                operandStack.push(Double.valueOf(token));
            }
        }
        // Phase II: Process all the remaining operators
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }
        return operandStack.pop();
    }
    
    /**
     * Processes a single operator, in other words an expression with only one
     * function applied to two doubles is evaluated and returned.
     * @param operandStack Our stack of numerical values.
     * @param operatorStack Our stack of operators ('+','-','*','/').
     * @throws EmptyStackException Indicator of an invalid expression.
     * @throws FullStackException I don't know when this would be thrown.
     */
    public static void processAnOperator(GenericStack<Double> operandStack, 
            GenericStack<Character> operatorStack) throws EmptyStackException, 
            FullStackException {
        double result = 0;
        char op = operatorStack.pop();
        double op1 = (double) operandStack.pop();
        double op2 = (double) operandStack.pop();
        if (op == '+') {
            result = op2 + op1;
        } else if (op == '-') {
            result = op2 - op1;
        } else if (op == '*') {
            result = op2 * op1;
        } else if (op == '/') {
            result = op2 / op1;
        } else {
            while (true) {
                operandStack.pop(); // Declare the expression invalid
                                    // by throwing a EmptyStackException.
            }
        }
        operandStack.push(result);
    }
    
    /**
     * Inserts blanks into a mathematical expression, before and after every
     * meaningful symbol.
     * @param str The expression we wish to insert blanks into.
     * @return The new expression with lots of blank spaces.
     */
    public static String insertBlanks(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(' || str.charAt(i) == ')' 
                    || str.charAt(i) == '+' || str.charAt(i) == '-'
                    || str.charAt(i) == '*' || str.charAt(i) == '/'
                    ) {
                result += " " + str.charAt(i) + " ";
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
    
    /**
     * This method determines if two numbers are close enough to be the same
     * with some round-off error.
     * @param one The first number we are comparing.
     * @param two The second number we are comparing.
     * @return True if the values are close enough to be the same.
     */
    public static boolean closeEnough(double one, double two) {
        double eps = 0.00000001;
        return Math.abs(one-two)<eps;
    }
}
