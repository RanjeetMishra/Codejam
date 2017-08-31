/**
 * Created by ranjem on 8/29/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class EvenOddArrangement {

    static int minimumChocolateMoves(int n, int[] X) {
        // Complete this function
        int result = 0;
        //Count moves needed for even and odd Moves. We have two kind of moves. One is must add and other can be add/remove both is fine. We will keep them separate.
        int evenAddOnlyMoves = 0;
        int evenCommonMoves = 0;
        int oddMoves = 0;
        for(int i=0;i<n;i++) {
            if((i%2)==0) {
                if(X[i]==1) {
                    evenAddOnlyMoves +=1;
                }else {
                    evenCommonMoves += (X[i]%2);
                }
            }else {
                oddMoves += (X[i]+1)%2;
            }
        }

        if((evenCommonMoves+oddMoves)>=evenAddOnlyMoves) {
            int temp = evenAddOnlyMoves+evenCommonMoves+oddMoves;
            if(temp%2 !=0)
                return -1;
            return temp/2;
        }else {
            //There are lot of 1s at even position. We have to just verify if we have that many chocolates we can get from even/odd positions in quantity of two each.
            evenAddOnlyMoves -= (evenCommonMoves+oddMoves);
            if(evenAddOnlyMoves%2 != 0)
                return -1;

            long sum = 0;
            long minSum = 0;
            for(int i=0;i<n;i++) {
                sum+=X[i];
                if(i%2==0)
                    minSum+=2;
                else
                    minSum+=1;
            }

            if((sum-minSum) < evenAddOnlyMoves)
                return -1;
            else {
                return evenAddOnlyMoves+evenCommonMoves+oddMoves;
            }
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //  Return the minimum number of chocolates that need to be moved, or -1 if it's impossible.
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            int[] X = new int[n];
            for(int X_i = 0; X_i < n; X_i++){
                X[X_i] = in.nextInt();
            }
            int result = minimumChocolateMoves(n, X);
            System.out.println(result);
        }
    }
}

