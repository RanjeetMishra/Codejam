/**
 * Created by ranjem on 8/30/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CutInsertStringWays {

    static final int modulo = 126107;
    static final int dim = 26;
    static int[][] hashMatrix;
    //Lets save hash values of substrings of every length per start index.
    static void preProcessHashMatrix(String s) {

        int length = s.length();
        hashMatrix = new int[length][length+1];
        for(int i=0;i<length;i++) {
            for(int k=0;k<=length;k++)
                hashMatrix[i][k]=-1;
        }
        //hashMatrix[i][j] = hash of substring from index i of length j
        char[] strArray = s.toCharArray();

        for(int i=0;i<length;i++) {

            int h=0;
            for(int j=i;j<length;j++) {
                h=(h*dim+strArray[j])%modulo;
                hashMatrix[i][j-i+1]=h;
            }
        }
        return;
    }

    static long countCutsAndInserts(String s) {
        // Complete this function
        int length = s.length();
        char[] strArray = s.toCharArray();

        //Now for all substring, check on both left and right sides how many substrings match it and increment the count.
        long result = 0;

        for(int i=0;i<length;i++) {

            int hashValue = 0;
            for(int j=i;j<length;j++) {
                hashValue = (hashValue*dim+strArray[j])%modulo;
                int l = j-i+1;
                //Checking for substring i,j of length l=j-i+1. We have to check same hash value at i-l and so on
                //similarly for i+l and so on and for both directions we have to mantain count till it matches.

                int count = 0;
                for(int k=i-l;k>=0;k-=l) {
                    if(hashMatrix[k][l]!=hashValue)
                        break;
                    if(hashMatrix[k][l]==hashValue) {
                        //TODO:: Validate first
                        boolean matched = true;
                        for(int x=0;x<l;x++) {
                            if (strArray[k + x] != strArray[i + x]){
                                matched = false;
                                break;
                            }
                        }
                        if(matched)
                            count++;
                        else
                            break;
                    }
                }

                for(int k=i+l;k<length;k+=l) {
                    if(hashMatrix[k][l]!=hashValue)
                        break;
                    if(hashMatrix[k][l]==hashValue) {
                        //TODO:: Validate first
                        boolean matched = true;
                        for(int x=0;x<l;x++) {
                            if (strArray[k + x] != strArray[i + x]){
                                matched = false;
                                break;
                            }
                        }
                        if(matched)
                            count++;
                        else
                            break;
                    }
                }
                result = result+count+1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //  Return the number of successful ways to cut and insert a substring.
        String s = in.next();
        preProcessHashMatrix(s);
        long result = countCutsAndInserts(s);
        System.out.println(result);
    }
}

