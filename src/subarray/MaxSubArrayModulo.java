package subarray;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/maximum-subarray-sum/problem
 * Created by ranjem on 9/10/17.
 */

//Idea: This is maximum sum modulo m
// Now conider any sub[i,j] = (PrefixSumMod[j]-PrefixSumMod[i-1] + m)%m
//BF can be done in O(n^2). But can we identify something we need not calculate.
// See if PrefixSumMod[j]>PrefixSumMod[i-1] , then sub[i,j] = PrefixSumMod[j]-PrefixSumMod[i-1] < PrefixSumMod[j].
// So effectively, at any i, we have to find all prefixes greater than current prefix on its left side and judge the maximum value.
// If we store prefixes in sorted DS, we can easily find all values greater than prefix[i] on its left side while iterating the given array.
// We will use TreeSet and its function tailSet :)

public class MaxSubArrayModulo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        long[] res = new long[q];
        for(int i=0;i<q;i++){
            int n = sc.nextInt();
            long m = sc.nextLong();
            long[] arr = new long[n];
            for(int j=0;j<n;j++){
                arr[j]=sc.nextLong();
            }
            res[i]= getMaxSum(n,m,arr);

        }
        for(int i=0;i<q;i++){
            System.out.println(res[i]);
        }
    }

    private static long getMaxSum(int n, long m, long[] arr) {
        long maxSum=0;

        TreeSet<Long> prefix = new TreeSet<Long>();
        long currentSum=0;
        for(int i=0;i<n;i++){
            currentSum=(currentSum+arr[i]%m)%m;
            SortedSet<Long> set = prefix.tailSet(currentSum+1);
            Iterator<Long> itr = set.iterator();
            if(itr.hasNext()){

                maxSum= Math.max(maxSum, (currentSum-itr.next()+m)%m);
            }

            maxSum=Math.max(maxSum, currentSum);
            prefix.add(currentSum);
        }


        return maxSum;
    }
}
