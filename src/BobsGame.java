
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/university-codesprint-3/challenges/bobs-game
 * Algo uses tricky Game theory based on Nim game.
 */
public class BobsGame {


    static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static int BLOCKEDCELL = -1;
    static int KINGCELL = 1;
    static int FREECELL = 0;
    static int[][] playBoard;

    static int[] rowMoves = {0,-1,-1};
    static int[] colMoves = {-1,-1,0};

    static int NO_WINNINGMOVE = -1;

    public static List<Pair> getPossibleMoves(int x, int y, int n, int[][] playBoard) {

        List<Pair> moves = new ArrayList<>();

        for(int i=0;i<3;i++) {

            int nextRow = x+rowMoves[i];
            int nextCol = y+colMoves[i];

            if(nextRow>=0 && nextRow<n && nextCol>=0 && nextCol<n && playBoard[nextRow][nextCol]!=BLOCKEDCELL) {
                moves.add(new Pair(nextRow,nextCol));
            }
        }

        return moves;
    }

    public static void processWinningMatrix(int n, int[][] playBoard, int[][] winningMatrix) {

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {

                if(playBoard[i][j]==BLOCKEDCELL) {
                    winningMatrix[i][j] = NO_WINNINGMOVE;
                    continue;
                }
                List<Pair> moves = getPossibleMoves(i,j,n,playBoard);
                if(moves.isEmpty()) {
                    winningMatrix[i][j] = 0;
                }else {
                    //Minimum integer for current cell
                    //not used 0,1,2,3
                    int[] temp = {-1,-1,-1,-1};
                    for(Pair p: moves) {
                        temp[winningMatrix[p.row][p.col]] = 0;
                    }

                    int minIndex = 0;
                    for(int k=0;k<4;k++) {
                        if(temp[k]==-1) {
                            winningMatrix[i][j]=k;
                            break;
                        }
                    }
                }
            }
        }

    }

    public static String countWinMoves(int n, int[][] playBoard, int[][] winningMatrix, List<Pair> kingPositions) {

        int win = winningMatrix[kingPositions.get(0).row][kingPositions.get(0).col];
        for(int i=1;i<kingPositions.size();i++) {
            win ^= winningMatrix[kingPositions.get(i).row][kingPositions.get(i).col];
        }

        if(win==0) {
            return "LOSE";
        }

        //Otherwise
        int result = 0;
        for(Pair kingPos: kingPositions) {

            int kingVal = winningMatrix[kingPos.row][kingPos.col];
            List<Pair> nextMoves = getPossibleMoves(kingPos.row, kingPos.col,n,playBoard);
            for(Pair nextMove: nextMoves) {
                int nextVal = winningMatrix[nextMove.row][nextMove.col];
                if((win ^ kingVal ^ nextVal) == 0) {
                    result+=1;
                }
            }
        }

        if(result>0) {
            return "WIN "+result;
        }else {
            return "LOSE";
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();

        for(int a0 = 0; a0 < q; a0++){

            //For every game
            int n = in.nextInt();
            playBoard = new int[n][n];
            List<Pair> kingPositions = new ArrayList<>();

            for(int i = 0; i < n; i++){
                char[] temp = in.next().toCharArray();
                for(int j=0;j<n;j++) {
                    switch (temp[j]) {
                        case '.': playBoard[i][j] = FREECELL; break;
                        case 'K': playBoard[i][j] = KINGCELL; kingPositions.add(new Pair(i,j)); break;
                        case 'X': playBoard[i][j] = BLOCKEDCELL;break;
                    }
                }
            }
            // Write Your Code Here

            int[][] winningMatrix = new int[n][n];
            processWinningMatrix(n,playBoard, winningMatrix);
            System.out.println(countWinMoves(n,playBoard, winningMatrix,kingPositions));

        }
        in.close();
    }
}
