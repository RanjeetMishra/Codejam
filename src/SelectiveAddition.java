/**
 * Created by ranjem on 9/9/17.
 * https://www.hackerrank.com/contests/hourrank-23/challenges/selective-additions
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

// Idea: For every operation we need to find count of numbers which will not be modified. We can precacluate at what index any
//A[i] becomes one of special numbers. We can do it by saving Binary Indexed Tree of all queries and for every A[i] find operations when it
//Becomes special. Now count of special numbers can be found in O(log n) by using segment tree.

public class SelectiveAddition {



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        long[] A = new long[n];
        for(int A_i = 0; A_i < n; A_i++){
            A[A_i] = in.nextLong();
        }
        long[] S = new long[k];
        for(int S_i = 0; S_i < k; S_i++){
            S[S_i] = in.nextLong();
        }

        //preProcessArray(n, A, S);

        for(int a0 = 0; a0 < m; a0++){
            int l = in.nextInt();
            int r = in.nextInt();
            int x = in.nextInt();
            //System.out.println(arraySum(A, l-1,r-1,x,n));
        }
        in.close();
    }

}
