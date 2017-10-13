import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class KingMarch {

    static int[] rowMoves = {};
    static int[] colMoves = {};

    /*public static void getNextMove() {

    }

    public static long numWaysToWord() {

    }*/

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        String word = in.next();

        int[][] visited = new int[8][8];
        char[][] board = new char[8][8];

        for(int i = 0; i < 8; i++){
            char[] charArr = in.next().toCharArray();
            for(int j=0;j<8;j++) {
                board[i][j] = charArr[j];
                visited[i][j] = 0;
            }
        }


        in.close();
    }
}
