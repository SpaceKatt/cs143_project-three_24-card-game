package card_game_24.utilities;

import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import card_game_24.objects.GenericStack;
import java.util.Scanner;

public class EvaluateExpression {

//    public static void main(String[] args) {
//        Scanner keyboard = new Scanner(System.in);
//        String answer = "y";
//        boolean again = true;
//        while(again) {
//            System.out.print("Input an expression in infix notation: ");
//            String express = keyboard.nextLine();
//            try {
//                if (express.equals("") || express.length() < 1) {
//                    throw new NumberFormatException();
//                }
//                System.out.println(express + " = " + 
//                        evaluateInfixExpression(express));
//            } catch (NumberFormatException ex) {
//                System.out.println("Fuck you.");
//            }
//            System.out.print("Try another? (y/n): ");
//            answer = keyboard.nextLine();
//            again = answer.equalsIgnoreCase("y");
//        }
//    }
    
    // Using two stacks
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
                        //|| operatorStack.peek() == '%')) {
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
//        } else if (op == '%') {
//            result = op2 % op1;
        } else {
            System.out.println("OPATORNOTRECOGNIZED");
            System.exit(0);
        }
        operandStack.push(result);
    }
    
    public static String insertBlanks(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(' || str.charAt(i) == ')' 
                    || str.charAt(i) == '+' || str.charAt(i) == '-'
                    || str.charAt(i) == '*' || str.charAt(i) == '/'
                    ) { //|| str.charAt(i) == '%') {
                result += " " + str.charAt(i) + " ";
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
    
    public static boolean closeEnough(double one, double two) {
        double eps = 0.00000001;
        return Math.abs(one-two)<eps;
    }
}
