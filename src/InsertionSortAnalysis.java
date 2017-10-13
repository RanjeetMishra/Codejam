import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/insertion-sort
 * Idea is to maintain BST with every node storing left and right subtree size. We keep updating it whenever we add a new node O(logn)
 * And for any update we can find total insertion operations in O(log n). Total complexity n*O(logn)
 * We might like to use Red-black tree or something which gaurantees balanced tree but it would be complex so using simple BST
 *
 */

public class InsertionSortAnalysis {

    static class Node {
        Node left;
        Node right;
        int value;
        int leftTreeSize;
        int rightTreeSize;

        Node(int value) {
            this.left = null;
            this.right = null;
            this.value = value;
            this.leftTreeSize = 0;
            this.rightTreeSize = 0;
        }
    }

    static Node root;

    public static int updateTreeWithInsertionCount(int data, Node root) {

        if(root.value>data) {
            if(root.left!=null) {
                root.leftTreeSize+=1;
                return updateTreeWithInsertionCount(data, root.left)+1+root.rightTreeSize;
            }else {
                Node newNode = new Node(data);
                root.left = newNode;
                root.leftTreeSize+=1;
                return 1+root.rightTreeSize;
            }
        }else {
            if(root.right !=null) {
                root.rightTreeSize+=1;
                return updateTreeWithInsertionCount(data,root.right);
            }else {
                Node newNode = new Node(data);
                root.right = newNode;
                root.rightTreeSize+=1;
                return 0;
            }
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        long insertionCount = 0;
        for(int i=0;i<t;i++){
            int n = in.nextInt();
            int[] arr = new int[n];
            for(int j=0;j<n;j++){
                arr[j] = in.nextInt();
            }

            root = new Node(arr[0]);
            for(int k=1;k<n;k++) {
                insertionCount += updateTreeWithInsertionCount(arr[k],root);
            }
            System.out.println(insertionCount);
            insertionCount = 0;
        }
    }
}
