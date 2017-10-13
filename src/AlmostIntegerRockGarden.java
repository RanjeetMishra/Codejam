import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/almost-integer-rock-garden/problem
 *
 * First idea that comes to mind is chose 9 random points and then look for rest 2 points.
 */

public class AlmostIntegerRockGarden {

    static boolean isIntegerDistant [][] = new boolean[25][25];
    static double distanceMat[][] = new double[25][25];

    private static void preProcessDistances() {

        int count = 0;
        for(int i=-12;i<=12;i++) {
            for(int j=-12;j<=12;j++) {
                double dist = Math.sqrt(i*i + j*j);
                if((dist-(int)(dist)) == 0) {
                    count++;
                    isIntegerDistant[i+12][j+12]=true;
                }else {
                    distanceMat[i+12][j+12]=dist;
                }
            }
        }
        System.out.println(count);
    }

    private static void findPositions(int x, int y) {

        double d = distanceMat[x+12][y+12];
        boolean selected[][] = new boolean[25][25];
        //Lets chose 9 points randomly
        int count = 0;
        selected[x+12][y+12] = true;
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        double distance = d;

        while(count<8) {
            int i = rand.nextInt(24);
            int j = rand.nextInt(24);
            if(selected[i][j] || isIntegerDistant[i][j])
                continue;
            d += distanceMat[i][j];
            selected[i][j] = true;
            count++;
        }

        int[] xIndexes = new int[625];
        int[] yIndexes = new int[625];

        //Now lets check all pairs of points if we can get an almost Integer distance sum
        int counter = 0;
        for(int i=0;i<25;i++) {
            for(int j=0;j<25;j++) {
                if(!selected[i][j] && !isIntegerDistant[i][j]) {

                    xIndexes[counter] = i;
                    yIndexes[counter] = j;
                    counter++;

                }

            }
        }

        boolean found = false;
        for(int i=0;i<counter-2;i++) {
            for(int j=i+1;j<counter-1;j++) {
                for(int k=j+1;k<counter;k++) {
                    double tempDistance = d + distanceMat[xIndexes[i]][yIndexes[i]] + distanceMat[xIndexes[j]][yIndexes[j]]+
                            distanceMat[xIndexes[k]][yIndexes[k]];
                    if ((tempDistance - (int) tempDistance) < 0.00000002) {

                        found = true;
                        System.out.println("Hurray " + tempDistance);
                        return;
                    }
                }
            }

        }

        if(!found) {
            System.out.println("Trying again");
            findPositions(x,y);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        preProcessDistances();

        findPositions(x,y);
        // your code goes here
    }
}
