/**
 * Created by ranjem on 8/19/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Currencies {

    static long modValue = 1000000007;
    static HashMap<Integer, Double[][]> dpMaxCurrencies = new HashMap<>();
    static HashMap<String, Long> backPropagationIndex = new HashMap<>();

    static long calculateActualExchange(int n, int s, int f, int m, double[][] A) {

        if(backPropagationIndex.get(s+"_"+f+"_"+m)!=null)
            return Long.valueOf(backPropagationIndex.get(s+"_"+f+"_"+m));
        if(m==1) {
            return (long)(A[s][f]);
        }else if(m>=2) {

            int firstHalf = m/2;
            int secondHalf = m-firstHalf;
            Double[][] firstHalfDpMatrix = dpMaxCurrencies.get(firstHalf);
            Double[][] secondHalfDpMatrix = dpMaxCurrencies.get(secondHalf);

            double maxValue = Double.MIN_VALUE;
            int maxIndex = 0;
            //Find k for which it maximized the value
            for(int k=0;k<n;k++) {
                double temp = (Double.valueOf(firstHalfDpMatrix[s][k])+Double.valueOf(secondHalfDpMatrix[k][f]));
                if(temp > maxValue) {
                    maxValue = temp;
                    maxIndex = k;
                }
            }

            long firstMod = calculateActualExchange(n,s,maxIndex,firstHalf,A)%modValue;
            long secondMod = calculateActualExchange(n,maxIndex,f,secondHalf,A)%modValue;
            long result = (firstMod*secondMod)%modValue;
            backPropagationIndex.put(s+"_"+f+"_"+m, result);
            return result;
        }

        return 0;
    }

    static void recursiveMaxCurrency(int n,int s, int f, double[][] A, int m) {

        if(dpMaxCurrencies.get(m)!=null) {
            return;
        }
        if(m==1 && (dpMaxCurrencies.get(1)==null)) {
            //update dpMaxCurrencies
            Double[][] initMaxCurrencies = new Double[n][n];
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(i==j) {
                        initMaxCurrencies[i][j] = Double.MIN_VALUE;
                    }else {
                        initMaxCurrencies[i][j] = Math.log(A[i][j]);
                    }
                }
            }
            dpMaxCurrencies.put(1,initMaxCurrencies);
            return;
        }else if(m>=2) {
            int firstHalf = m/2;
            int secondHalf = m-firstHalf;

            //If both are null, call recursively
            if(dpMaxCurrencies.get(firstHalf)==null)
                recursiveMaxCurrency(n,s,f,A,firstHalf);
            if(dpMaxCurrencies.get(secondHalf)==null)
                recursiveMaxCurrency(n,s,f,A,secondHalf);

            //Update dpMaxCurrencies for m using the two partitions
            Double[][] firstHalfDpMatrix = dpMaxCurrencies.get(firstHalf);
            Double[][] secondHalfDpMatrix = dpMaxCurrencies.get(secondHalf);
            Double[][] currentMaxCurrency = new Double[n][n];
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {

                    double maxValue = Double.MIN_VALUE;
                    for(int k=0;k<n;k++) {
                        double temp = (Double.valueOf(firstHalfDpMatrix[i][k])+Double.valueOf(secondHalfDpMatrix[k][j]));
                        if(temp > maxValue) {
                            maxValue = temp;
                        }
                    }
                    currentMaxCurrency[i][j] = maxValue;
                }
            }
            dpMaxCurrencies.put(m,currentMaxCurrency);
            return;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x = in.nextInt();
        int s = in.nextInt();
        int f = in.nextInt();
        int m = in.nextInt();
        double[][] A = new double[n][n];
        for(int A_i = 0; A_i < n; A_i++){
            for(int A_j = 0; A_j < n; A_j++){
                A[A_i][A_j] = in.nextLong();
            }
        }
        recursiveMaxCurrency(n,s,f,A,m);

        //We will have to retract the path to find actual value while doing modulo
        long result = (x*calculateActualExchange(n,s,f,m,A))%modValue;
        System.out.println((int)result);

        in.close();
    }
}
