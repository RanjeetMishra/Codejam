import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/moodys-analytics-fall-university-codesprint/challenges/stock-purchase-day/problem
 */

public class StockPurchaseDay {

    static class StockPair implements Comparable<StockPair> {
        int value;
        int day;

        StockPair(int value, int day) {
            this.value = value;
            this.day = day;
        }

        @Override
        public int compareTo(StockPair sp) {
            if(this.value == sp.value) {
                if(this.day < sp.day)
                    return -1;
                else
                    return 1;
            }else if(this.value > sp.value) {
                return 1;
            }else {
                return -1;
            }
        }
    }

    //Find farthest day with value <= xi
    static int stockPurchaseDay(StockPair[] stockArray, int xi, int left, int right) {

        if(left>right)
            return -1;

        if( (right-left) == 1) {
            if(stockArray[right].value <= xi)
                return stockArray[right].day;
            else if(stockArray[left].value <= xi)
                return stockArray[left].day;
            else
                return -1;
        }

        if(left==right) {
            if(stockArray[left].value <= xi)
                return stockArray[left].day;
            else
                return -1;

        }

        int mid = (left+right)/2;

        if(stockArray[mid].value <= xi) {
            //Search on the right
            return stockPurchaseDay(stockArray,xi,mid,right);
        }else if(stockArray[mid].value > xi) {
            //Search on the left
            return stockPurchaseDay(stockArray, xi, 0, mid-1);
        }else {
            //If equal search on the right including mid as we want to find rightmost day
            return stockPurchaseDay(stockArray, xi, mid, right);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        StockPair[] stockArray = new StockPair[n];
        for(int i = 0; i < n; i++){
            stockArray[i] = new StockPair(in.nextInt(), i+1);
        }

        //Process the array and for any index i put value as the minimum cost from i to n-1
        int currentMinimum = Integer.MAX_VALUE;
        for(int i=n-1;i>=0;i--) {
            if(stockArray[i].value < currentMinimum) {
                currentMinimum = stockArray[i].value;
            }
            stockArray[i].value = currentMinimum;
        }

        Arrays.sort(stockArray);

        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int xi = in.nextInt();
            int result = stockPurchaseDay(stockArray, xi, 0 , n-1);
            System.out.println(result);
        }
        in.close();
    }

}
