import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class CollatzSequence {

    static int SIZE = 8800000;
    static int[] dp = new int[SIZE];
    static int collatzSequenceLen(int n) {

        if(n<SIZE && dp[n]!=-1)
            return dp[n];

        if (n % 2 == 0) {
            int res = collatzSequenceLen(n/2);
            if(n/2<SIZE)
                dp[n/2] = res;
            return 1 + res;
        }

        int index = 3*n+1;
        int res = collatzSequenceLen(index);

        if(index < SIZE)
            dp[index] = res;
        return 1 + res;
    }

    static int collatzSequenceSum(int T, int A, int B) {

        Arrays.fill(dp,-1);
        dp[0]=0;
        dp[1]=1;
        int n = 0;
        int result = 0;
        while (T-- > 0) {
            n = (A*n + B) % 5003;
            int best_len = 0;
            int best_num = 0;
            for (int k = n; k >= (n/2); k--) {
                int cur_len = collatzSequenceLen(k);
                if (cur_len >= best_len) {
                    best_len = cur_len;
                    best_num = k;
                }
            }
            result += best_num;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        int A = in.nextInt();
        int B = in.nextInt();
        int result = collatzSequenceSum(T, A, B);
        System.out.println(result);
        in.close();
    }

}
