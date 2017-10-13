import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class ValidString {
    static String isValid(String s){

        int[] charCount = new int[26];
        char[] charArray = s.toCharArray();
        for(int i=0;i<charArray.length;i++) {
            charCount[charArray[i]-'a']++;
        }

        //All counts must be same except one being more than that
        int[] freqValues = new int[2];
        int[] freqCount = new int[2];
        int found = 0;

        for(int i=0;i<26;i++) {
            if(charCount[i]>0) {
                if(found==0) {
                    freqValues[0] = charCount[i];
                    freqCount[0]++;
                    found++;
                }else if(found==1){
                    if(freqValues[0]!=charCount[i]) {
                        freqValues[found] = charCount[i];
                        freqCount[found]++;
                        found++;
                    }else {
                        freqCount[0]++;
                    }
                }else {
                    //two different frequencies already found. If this is different from both return NO or increment the
                    //freqCount
                    if(freqValues[0]==charCount[i]) {
                        freqCount[0]++;
                    }else if(freqValues[1]==charCount[i]) {
                        freqCount[1]++;
                    }else {
                        return "NO";
                    }
                }
            }
        }

        if(found==1)
            return "YES";

        if(found==2) {
            if(freqCount[0]==1 && (freqValues[0]-freqValues[1])==1)
                return "YES";
            if(freqCount[1]==1 && (freqValues[1]-freqValues[0])==1)
                return "YES";
        }

        return "NO";

        //all are maxFrequency or all but one are maxFrequency-1

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String result = isValid(s);
        System.out.println(result);
    }
}
