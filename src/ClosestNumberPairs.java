import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;


public class ClosestNumberPairs {

    static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void printSmallestPairs(int n, int[] arr) {

        //Sort the array
        Arrays.sort(arr);
        List<Pair> minPairList = new ArrayList<Pair>();
        int minDifference = Integer.MAX_VALUE;

        for(int i=0;i<n-1;i++) {
            //comapre i and i+1
            int diff = Math.abs(arr[i+1]-arr[i]);
            if(diff<minDifference) {
                minDifference = diff;
                minPairList.clear();
                minPairList.add(new Pair(arr[i],arr[i+1]));
            }else if(diff==minDifference) {
                minPairList.add(new Pair(arr[i],arr[i+1]));
            }
        }

        System.out.print(minPairList.get(0).x + " "+minPairList.get(0).y);
        for(int i=1;i<minPairList.size();i++) {
            Pair p = minPairList.get(i);
            System.out.print(" " + p.x+" "+p.y);
        }

    }
    public static  void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[] arr = new int[n];
        for(int i=0;i<n;i++) {
            arr[i]=in.nextInt();
        }
        printSmallestPairs(n,arr);

    }
}
