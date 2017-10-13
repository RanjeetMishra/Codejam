import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class AnagramPair {

    static int sherlockAndAnagrams(String s){

        Map<String, Integer> subStringCountMap = new HashMap<String, Integer>();
        char[] charArray = s.toCharArray();
        int anagramPairCount = 0;

        char[] temp = new char[100];
        for(int i=0;i<charArray.length;i++) {

            for(int j=i;j>=0;j--) {

                temp[i-j] = charArray[j];
                Arrays.sort(temp,0,i-j+1);
                String newStr = new String(temp,0,i-j+1);

                Integer currentCount = subStringCountMap.get(newStr);
                if(currentCount!=null) {
                    anagramPairCount+= currentCount.intValue();
                    currentCount = new Integer(currentCount.intValue()+1);
                }else {
                    currentCount = new Integer(1);
                }
                subStringCountMap.put(newStr,currentCount);

            }
        }

        return anagramPairCount;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            String s = in.next();
            int result = sherlockAndAnagrams(s);
            System.out.println(result);
        }
    }

}
