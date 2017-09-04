import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/codeagon/challenges/buying-everything
 * Created by ranjem on 9/3/17.
 */
public class GreedyBuy {

    static int nextItemShopIndex[];


    static long minimumTime(int[] b, int m, int k) {

        nextItemShopIndex = new int[b.length];
        int prevIndex = 0;
        for(int i=1;i<b.length;i++) {
            if(b[i]==1) {
                nextItemShopIndex[prevIndex]=i;
                prevIndex=i;
            }
        }

        //We will find minimum time for all contiguous m 1s
        int startIndex = 0;
        int itemCount = 0;
        long minTime = Long.MAX_VALUE;
        long tempTime = 0;
        for(int i=1;i<b.length;i++) {

            if(b[i]==1) {
                itemCount++;
                if(startIndex==0) {
                    startIndex=i;
                }
            }

            if((itemCount-b[i])>0) {
                tempTime += (itemCount-b[i])*k;
            }else {
                tempTime += 1;
            }

            if(itemCount==m) {
                if(tempTime<minTime)
                    minTime = tempTime;

                //For coming groups of m items lets remove the first item found and its cost.
                itemCount--;
                tempTime -= k*(i-startIndex);
                tempTime += nextItemShopIndex[startIndex]-startIndex;

                //Need to update startIndex for next group of items. That is found first index after startIndex with value 1.
                //Lets store these values when preprocessed.
                startIndex = nextItemShopIndex[startIndex];
            }
        }

        if(minTime==Long.MAX_VALUE)
            return -1;
        return minTime;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[] b = new int[n];
        for(int b_i = 0; b_i < n; b_i++){
            b[b_i] = in.nextInt();
        }
        long result = minimumTime(b, m, k);
        System.out.println(result);
        in.close();
    }
}
