import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;

/**
 * Find minimum number of swaps to sort an array
 * Idea is to use graph. For any element at index i, find final index where it should go say j. Now such edges will form many cycles.
 * For any cycle, we need cycle-1 swaps to sort the array.
 *
 * There are two takeaways here.
 * 1. To find minimum swaps for a beautiful array, beautiful array can be ascending or descending. we need to check for both.
 * 2. Though question does not take duplicates, but I have added check to even consider duplicates.
 *
 * https://www.hackerrank.com/challenges/lilys-homework
 *
 */

public class MinimumSwapSort {

    static class Pair {
        int value;
        int index;

        Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    private static int minSwapCount(int n,int[] input) {

        List<Pair> inputPosPairList = new ArrayList<Pair>(n);
        for(int i=0;i<n;i++) {
            inputPosPairList.add(i, new Pair(input[i],i));
        }
        inputPosPairList.sort( new Comparator<Pair>() {

            @Override
            public int compare(Pair o, Pair e) {
                if(o.value<e.value)
                    return -1;
                else if(o.value>e.value)
                    return 1;
                else
                    return 0;
            }
        });

        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        int ascResult = 0;
        for(int i=0;i<n;i++) {
            if(visited[i] || inputPosPairList.get(i).index==i)
                continue;

            //TODO:: Important We should ignore swaps if duplicate value
            int j = i;
            int cycleLength = 0;
            while(!visited[j]) {
                visited[j] = true;
                int next = inputPosPairList.get(j).index;
                if(inputPosPairList.get(j).value != inputPosPairList.get(next).value)
                    cycleLength++;
                else
                    System.out.println("Duplicate");
                j = next;
            }
            if(cycleLength>0)
                ascResult += cycleLength-1;
        }


        //Check same count with descending order
        int descResult = 0;
        inputPosPairList.sort( new Comparator<Pair>() {

            @Override
            public int compare(Pair o, Pair e) {
                if(o.value<e.value)
                    return 1;
                else if(o.value>e.value)
                    return -1;
                else
                    return 0;
            }
        });

        Arrays.fill(visited, false);

        for(int i=0;i<n;i++) {
            if(visited[i] || inputPosPairList.get(i).index==i)
                continue;

            //TODO:: Important We should ignore swaps if duplicate value
            int j = i;
            int cycleLength = 0;
            while(!visited[j]) {
                visited[j] = true;
                int next = inputPosPairList.get(j).index;
                if(inputPosPairList.get(j).value != inputPosPairList.get(next).value)
                    cycleLength++;
                else
                    System.out.println("Duplicate");
                j = next;
            }
            if(cycleLength>0)
                descResult += cycleLength-1;
        }

        return Math.min(ascResult,descResult);
    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[] input = new int[n];
        System.out.println();
        for(int i=0;i<n;i++) {
            input[i] = in.nextInt();
        }
        System.out.println(minSwapCount(n,input));
    }
}
