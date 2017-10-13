import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/university-codesprint-3/challenges/the-snake-vs-the-wind
 * Greedy algo at each decision
 *
 * Trick involved for intial R/Col move.
 *
 */
public class SnakeWind {

    static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    static int[] rowMoves = {0,-1,0,1};
    static int[] colMoves = {-1,0,1,0};
    static char[] moveDirections = {'w','n','e','s'};

    private static int getMoveCost(char moveDirection, char windDirection) {

        int moveDirIndex = 0;
        int windDirIndex = 0;
        for(int i=0;i<4;i++) {
            if(moveDirections[i]==moveDirection) {
                moveDirIndex = i;
            }
            if(moveDirections[i]==windDirection) {
                windDirIndex = i;
            }
        }

        if(Math.abs(moveDirIndex-windDirIndex)==2)
            return 2;
        else if(Math.abs(moveDirIndex-windDirIndex)==0)
            return 0;
        return 1;
    }

    private static Pair getNextMove(int n, int r, int c, int[][] journey, char windDirection) {

        Pair move = null;
        int minCost = Integer.MAX_VALUE;

        for(int i=0;i<4;i++) {
            int nextR = r+rowMoves[i];
            int nextC = c+colMoves[i];

            if(!(nextR>=0 && nextR<n && nextC>=0 && nextC<n && journey[nextR][nextC]==0))
                continue;
            int cost = getMoveCost(moveDirections[i],windDirection);
            if(cost<minCost) {
                minCost = cost;
                move = new Pair(nextR,nextC);
            }
        }
        return move;
    }

    private static int[][] mapJourney(char direction, int startX, int startY, int n) {

        int[][] journey = new int[n][n];

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++)
                journey[i][j]=0;
        }

        int timer =1;
        journey[startX][startY] = timer;
        Pair nextMove = getNextMove(n,startX,startY,journey,direction);
        while(nextMove!=null) {
            timer++;
            journey[nextMove.row][nextMove.col] = timer;
            nextMove = getNextMove(n,nextMove.row, nextMove.col,journey,direction);
        }

        return journey;
    }
    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        char d = in.next().charAt(0);
        int x = in.nextInt();
        int y = in.nextInt();

        int[][] journey = mapJourney(d,x,y,n);

        for(int i=0;i<n;i++) {
            System.out.print(journey[i][0]);
            for(int j=1;j<n;j++) {
                System.out.print(" "+journey[i][j]);
            }
            System.out.println();
        }
        // Write Your Code Here
        in.close();
    }

}

