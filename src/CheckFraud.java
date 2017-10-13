import util.MyPriorityQueue;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * https://www.hackerrank.com/challenges/fraudulent-activity-notifications
 * Find number of times withdrawals are > 2*median in last d transactions.
 * Idea is to maintain median while processing the withdrawals. We will use indexed heap for the same.
 *
 */
public class CheckFraud {

    private static class MinComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o, Integer e) {
            return -o.compareTo(e);
        }
    }

    private static class MaxComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o, Integer e) {
            return o.compareTo(e);
        }
    }

    /*private static int getFraudCount(int n, int d, int[] withdrawals) {

        MyPriorityQueue<Integer> minHeap = new MyPriorityQueue<Integer>(new MinComparator());
        MyPriorityQueue<Integer> maxHeap = new MyPriorityQueue<Integer>(new MaxComparator());

        //Initialize
        //size difference between heap can be at max 1
        for(int i=0;i<d;i++) {
           if(minHeap.size()>maxHeap.size()) {

           }
        }

    }*/

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int d = in.nextInt();

        int[] withdrawals = new int[n];
        for(int i=0;i<n;i++) {
            withdrawals[i] = in.nextInt();
        }
    }
}
