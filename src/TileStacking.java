import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class TileStacking {

    static int Modulo = 1000000007;

    static int tileStackingProblem(int n, int m, int k) {

        int[][] dp = new int[n+1][m];

        //For height 0, number of ways = 1
        for(int j=0;j<m;j++) {
            dp[0][j]=1;
        }

        dp[1][0] = m+1;
        for(int j=1;j<m;j++) {
            dp[1][j] = m-j+dp[0][j];
        }


        for(int i=1;i<= Math.min(k,n);i++) {
            dp[i][m-1]=dp[i-1][m-1]+1;
        }


        for(int i=(Math.min(k,n)+1);i<=n;i++) {
            dp[i][m-1]=dp[i-1][m-1];
        }

        //Column wise summation we have to maintain


        //I have to solve from left to right row wise. row value starts with 2 and goes till n. column value starts with m-2 and goes till 0
        for(int i=2;i<=n;i++) {
            for(int j=m-2;j>=0;j--) {

                /*int count = 0;
                while( (i-count) >= 0 && count<=k ) {
                    dp[i][j] = (dp[i][j] + dp[i-count][j+1])%Modulo;
                    count++;
                }*/
                //Save this loop

                if(i==n && j==0) {
                    dp[i][j] = dp[i][j+1]-dp[Math.max(0,i-k-1)][j+1];
                }else {

                    //we are maintaing cummulative column wise sum in i,j. So actually we need to add dp[i][j+1]-dp[i-k][j+1]


                    int temp = dp[i][j+1]-  ((i-k-1)>=0?dp[i-k-1][j+1]:0) +  dp[i-1][j];
                    if(temp<0)
                        temp+=Modulo;
                    dp[i][j] = temp % Modulo;
                }
            }
        }

        return dp[n][0];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int result = tileStackingProblem(n, m, k);
        System.out.println(result);
        in.close();
    }
}
