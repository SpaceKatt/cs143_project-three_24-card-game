package card_game_24.utilities;

import card_game_24.objects.EmptyStackException;
import card_game_24.objects.FullStackException;
import card_game_24.objects.GenericStack;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RPN {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String answer = "y";
        boolean again = true;
        while(again) {
            System.out.print("Input an expression in postfix notation: ");
            String express = keyboard.nextLine();
            try {
                int result = evaluateExpression(express);
                System.out.println(result);
            } catch (Exception ex) {
                System.out.println("Fuck you." + ex.toString());
            }
            System.out.print("Try another? (y/n): ");
            answer = keyboard.nextLine();
            again = answer.equalsIgnoreCase("y");
        }        
    }
    
    public static int evaluateExpression(String expression) 
            throws FullStackException, EmptyStackException {
        GenericStack<Object> operandStack = new GenericStack<>();
        //GenericStack<Object> operatorStack = new GenericStack<>();
        StringTokenizer tokens = new StringTokenizer(expression, " +-*/%", 
                true);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (token.length() == 0) {
                continue;
            } else if (token.charAt(0) == '+' || token.charAt(0) == '-' 
                    || token.charAt(0) == '/' ||token.charAt(0) == '*' 
                    || token.charAt(0) == '%') {
                processAnOperator(token.charAt(0), operandStack);
            } else {
                operandStack.push(Integer.valueOf(token));
            }
        }
        return (int) operandStack.pop();
    }
    
    public static void processAnOperator(char op, GenericStack operandStack) 
            throws FullStackException, EmptyStackException {
        int result = 0;
        System.out.println(operandStack);
        if (op == '+') {
            int op1 = (int) operandStack.pop();
            int op2 = (int) operandStack.pop();
            result = op2 + op1;
        } else if (op == '-') {
            int op1 = (int) operandStack.pop();
            int op2 = (int) operandStack.pop();
            result = op2 - op1;
        } else if (op == '*') {
            int op1 = (int) operandStack.pop();
            int op2 = (int) operandStack.pop();
            result = op2 * op1;
        } else if (op == '/') {
            int op1 = (int) operandStack.pop();
            int op2 = (int) operandStack.pop();
            result = op2 / op1;
        } else if (op == '%') {
            int op1 = (int) operandStack.pop();
            int op2 = (int) operandStack.pop();
            result = op2 % op1;
        } else {
            System.out.println("OPATORNOTRECOGNIZED");
            System.exit(0);
        }
        operandStack.push(result);
    }
}
