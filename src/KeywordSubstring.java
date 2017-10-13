import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * https://www.hackerrank.com/contests/optimization-oct17/challenges/keywords
 */
public class KeywordSubstring {

    public static int minimumLength(String text, List<String> keys) {

        int matchCount = keys.size();

        Map<String, Integer> keyCountMap = new HashMap<>();
        for(String str: keys) {
            keyCountMap.put(str, 1);
        }

        //We will keep track of found keys in sliding window. whenever matchCount becomes zero we have found a substring
        int startIndex = 0;
        int endIndex = 0;
        int minSubStringLength = Integer.MAX_VALUE;

        String[] strArray = text.split(" ");
        int[]  wordLengths = new int[strArray.length+1];
        wordLengths[0] = 0;



        for(int i=0;i<strArray.length;i++) {
            wordLengths[i+1] = wordLengths[i]+strArray[i].length();
        }

        //Substring length strLength[endIndex+1]-strLength[startIndex]

        int i=0,j=0;

        for(i=0;i<strArray.length;i++) {

            Integer keyCount = keyCountMap.get(strArray[i]);
            if(keyCount!=null) {
                keyCount--;
                if(keyCount==0) {
                    matchCount--;
                }
                keyCountMap.put(strArray[i],keyCount);
            }

            if(matchCount==0) {
                //Found a subString

                //Lets try to increment j to get the smallest substring. At the end we will do j+1 and update the map to for next iteration
                boolean check = true;
                while(check) {
                    keyCount = keyCountMap.get(strArray[j]);
                    if(keyCount==null) {
                        j++;
                        continue;
                    }
                    if(keyCount<0) {
                        keyCount++;
                        keyCountMap.put(strArray[j], keyCount);
                        j++;
                    }else {
                        break;
                    }
                }

                int subStringLength = wordLengths[i+1]-wordLengths[j]+(i-j);
                if(subStringLength<minSubStringLength) {
                    minSubStringLength = subStringLength;
                    startIndex = j;
                    endIndex = i;
                }

                //Lets increment j
                keyCount = keyCountMap.get(strArray[j]);
                keyCount++;
                keyCountMap.put(strArray[j], keyCount);
                matchCount++;
                j++;

            }

        }

        if(minSubStringLength==Integer.MAX_VALUE)
            return -1;
        return minSubStringLength;

    }

    public static void main(String args[]) {
        String text, buf;
        ArrayList<String> keys = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        text = in.nextLine();
        int keyWords = in.nextInt();
        in.nextLine();
        for (int i = 0; i < keyWords; i++) {
            buf = in.nextLine();
            keys.add(buf);
        }

        System.out.println(minimumLength(text, keys));
    }
}
