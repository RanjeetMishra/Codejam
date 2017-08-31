/**
 * Created by ranjem on 8/21/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//Idea is to maintain #a, #b, #c, #d, #ad, #cd till index i and calculate these values for i+1 char. Use these values and keep updating#abcds too
public class CountPerfectSubsequence {

    static final long modulo = 1000000007;

    static long countSubs(String s){

        char[] chars = s.toCharArray();
        long countA = 0;
        long countB = 0;
        long countC = 0;
        long countD = 0;
        long countAB = 0;
        long countCD = 0;
        long countABCD = 0;

        long newABs = 0;
        long newCDs = 0;
        long newABCD = 0;
        for(int i=0;i<chars.length;i++) {
            switch(chars[i]) {
                case 'a':
                    countA++;
                    if(countAB==0)
                        newABs = countB;
                    else
                        newABs = countAB;
                    //Add 1 to new ABs formed if we can combine equal as and bs with this extra a
                    if(countB>=countA && countA>=2)
                        newABs+=1;
                    countAB = (countAB+newABs)%modulo;

                    if(countABCD==0)
                        newABCD = (countAB*countCD)%modulo;
                    newABCD = countABCD;
                    if(countC>0&&countD>0&&countB>=countA)
                        newABCD=(newABCD+1)%modulo;
                    countABCD = (countABCD+newABCD)%modulo;
                    break;
                case 'b':
                    countB++;
                    if(countAB==0)
                        newABs = countA;
                    else
                        newABs = countAB;
                    if(countA>=countB && countB>=2)
                        newABs+=1;
                    countAB = (countAB+newABs)%modulo;
                    newABCD = countABCD;
                    if(countC>0&&countD>0&&countA>=countB)
                        newABCD=(newABCD+1)%modulo;
                    countABCD = (countABCD+newABCD)%modulo;
                    break;
                case 'c':
                    countC++;
                    if(countCD==0)
                        newCDs = countD;
                    else
                        newCDs = countCD;
                    if(countD>=countC && countC>=2)
                        newCDs+=1;
                    countCD = (countCD+newCDs)%modulo;
                    newABCD = countABCD;
                    if(countA>0 && countB>0 && countD>=countC)
                        newABCD=(newABCD+1)%modulo;
                    countABCD = (countABCD+newABCD)%modulo;
                    break;
                case 'd':
                    countD++;
                    if(countCD==0)
                        newCDs = countC;
                    else
                        newCDs = countCD;
                    if(countC>=countD && countD>=2)
                        newCDs+=1;
                    countCD = (countCD+newCDs)%modulo;
                    newABCD = countABCD;
                    if(countA>0 && countB>0 &&(countC>=countD))
                        newABCD=(newABCD+1)%modulo;
                    countABCD = (countABCD+newABCD)%modulo;
                    break;
            }
        }
        return (countAB+countCD+countABCD)%modulo;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Return the number of non-empty perfect subsequences mod 1000000007
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            String s = in.next();
            long result = countSubs(s);
            System.out.println(result);
        }
    }
}
