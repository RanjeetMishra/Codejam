package FB;

import java.util.Stack;

public class PrintBTReverseLevel {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public void addLeftNode(int value) {
            Node n = new Node(value);
            this.left = n;
        }

        public void addRightNode(int value) {
            Node n = new Node(value);
            this.right = n;
        }
    }

    static int LEFTRIGHT = 0;
    static int RIGHTLEFT = 1;

    //called with root node and RIGHTLEFT as direction
    private static void printReverseLevelOrder(Stack<Node> stack, int direction) {

        if(stack.isEmpty())
            return;

        //Go to next level
        Stack<Node> nextLevel = new Stack<Node>();
        String currentLevel = "";
        while(!stack.isEmpty()) {
            Node p = stack.pop();
            if(direction==RIGHTLEFT) {
                if(p.right!=null)
                    nextLevel.push(p.right);
                if(p.left!=null)
                    nextLevel.push(p.left);
            }else {
                if(p.left!=null)
                    nextLevel.push(p.left);
                if(p.right!=null)
                    nextLevel.push(p.right);
            }
            if(direction==LEFTRIGHT) {
                //Add in currentLevel String as popped
                currentLevel += p.value+",";
            }else {
               //Add in currentLevel String in reverse order
                currentLevel = p.value + "," + currentLevel;
            }
        }

        printReverseLevelOrder(nextLevel, (direction==RIGHTLEFT)?LEFTRIGHT:RIGHTLEFT);

        currentLevel = currentLevel.substring(0, currentLevel.length()-1);
        currentLevel = "[" + currentLevel + "],";
        System.out.println(currentLevel);
        //Print current stack
    }

    public static void main(String args[]) {

        Node root = new Node(1);
        root.addLeftNode(2);
        root.addRightNode(3);

        root.left.addLeftNode(4);
        root.left.addRightNode(5);

        root.right.addLeftNode(6);
        root.right.addRightNode(7);

        root.left.left.addLeftNode(8);

        root.left.right.addLeftNode(9);
        root.left.right.addRightNode(10);

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);

        System.out.println("[");
        printReverseLevelOrder(stack, RIGHTLEFT);
        System.out.println("]");

    }
}
