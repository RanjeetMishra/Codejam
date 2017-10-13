package Expressions;

import java.util.*;
import java.io.*;

/**
 * Convert infix to postfix
 * e.g Infix: a+b*c+d
 *     Postfix: abc*+d+
 *
 *     Infix: a+(b-c)*d+e
 *     Postfix: abc-d*+e+
 */
public class InfixToPostFix {

    enum Operator{
        Multiply('*',1),
        Divide('/',1),
        Add('+',0),
        Minus('-',0),
        LeftParenthesis('(',-1),
        RightParenthesis(')',-1);

        private final char value;
        private final int precendence;

        private Operator(char val, int precedence) {
            this.value = val;
            this.precendence = precedence;
        }

        public static Operator getByValue(char c) {
            switch (c) {
                case '+': return Add;
                case '-': return Minus;
                case '*': return Multiply;
                case '/': return Divide;
                default: return null;
            }
        }

        public int getPrecedence() {
            return this.precendence;
        }
    }

    private static boolean isOperator(char c) {
        Operator op = Operator.getByValue(c);
        if(op!=null)
            return true;
        return false;
    }

    private static boolean isOperand(char c) {
        if(!isOperator(c) && c!='(' && c!=')')
            return true;
        return false;
    }

    private static String infixToPostfix(String infix) {

        char[] infixArray = infix.toCharArray();
        StringBuffer postFix = new StringBuffer();

        Stack<Operator> operatorStack = new Stack<Operator>();

        for(int i=0;i<infixArray.length; i++) {

            char currentChar = infixArray[i];
            if(isOperand(currentChar))
                postFix.append(currentChar);
            else if(isOperator(currentChar)) {
                //Operator
                Operator currentOp = Operator.getByValue(currentChar);

                if(operatorStack.isEmpty()) {
                    operatorStack.push(currentOp);
                }else {

                    while(!operatorStack.isEmpty()) {
                        Operator top = operatorStack.peek();
                        if(top.getPrecedence()>=currentOp.getPrecedence()) {
                            postFix.append(top.value);
                            operatorStack.pop();
                        }else {
                            break;
                        }
                    }
                    operatorStack.push(currentOp);
                }
            }else if(currentChar=='(') {
                operatorStack.push(Operator.LeftParenthesis);
            }else if(currentChar==')') {
                //Pop till '('
                while(!operatorStack.isEmpty()) {
                    Operator top = operatorStack.peek();
                    if(top==Operator.LeftParenthesis) {
                        operatorStack.pop();
                        break;
                    }else {
                        postFix.append(top.value);
                        operatorStack.pop();
                    }

                }
            }else {
                throw new RuntimeException("Unknown character: "+currentChar);
            }

        }

        while(!operatorStack.isEmpty()) {
            postFix.append(operatorStack.pop().value);
        }
        return postFix.toString();
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        String infix = in.next();

        System.out.println(infixToPostfix(infix));
    }
}
