import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Countluck {

    static char VISITED = '#';

    static class Pair {
        int r;
        int c;

        Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static int[] neighboursR = {0,-1,0,1};
    static int[] neighboursC = {-1,0,1,0};

    private static boolean canMove(char[][] maze, int N, int M, int row, int column) {

        if(!(row>=0 && row<N && column>=0 && column<M))
            return false;
        if(maze[row][column]=='X' || maze[row][column]==VISITED)
            return false;

        return true;
    }

    private static List<Pair> getNextMoves(char[][] maze, int N, int M, Pair p) {

        List<Pair> result = new ArrayList<Pair>();
        for(int i=0;i<4;i++) {
            if(canMove(maze,N,M,p.r+neighboursR[i], p.c+neighboursC[i])) {
                result.add(new Pair(p.r+neighboursR[i], p.c+neighboursC[i]));
            }
        }

        return result;
    }

    private static String canReach(char[][] maze, int N, int M, int prediction, Pair start, Pair end ) {

        Queue<Pair> bfsQueue = new LinkedList<Pair>();
        bfsQueue.add(start);
        int count = 0;
        boolean reached = false;
        while(!bfsQueue.isEmpty() && !reached) {
            //We will empty one depth
            Queue<Pair> bfsQueueTemp = new LinkedList<Pair>();
            while(!bfsQueue.isEmpty()) {
                Pair p = bfsQueue.poll();
                if(maze[p.r][p.c]=='*') {
                    reached = true;
                }
                maze[p.r][p.c] = VISITED;
                List<Pair> moves = getNextMoves(maze,N,M,p);
                bfsQueueTemp.addAll(moves);
            }
            bfsQueue = bfsQueueTemp;
            count++;
        }

        if(count==prediction)
            return "Impressed";
        return "Oops!";
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        int t = in .nextInt();
        for(int i=0;i<t;i++) {
            int N = in.nextInt();
            int M = in.nextInt();

            char[][] maze = new char[N][M];

            int startR = 0, startC=0, endR=0, endC=0;

            for(int j=0;j<N;j++) {
                String str = in.next();
                char[] strToCharArray = str.toCharArray();
                for(int k=0;k<M;k++) {
                    if(strToCharArray[k]=='*') {
                        endR = j;endC = k;
                    }else if(strToCharArray[k]=='M') {
                        startR = j; startC = k;
                    }
                    maze[j][k]=strToCharArray[k];
                }
            }

            int prediction = in.nextInt();
            System.out.println(canReach(maze,N, M, prediction,new Pair(startR,startC), new Pair(endR, endC)));
        }
    }
}
