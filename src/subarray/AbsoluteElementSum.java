package subarray;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/playing-with-numbers
 * Created by ranjem on 9/10/17.
 *
 *  Idea 1: At any query i, maintain sum of all query values. Keep the input array sorted, find opposite of this value and
 *  use leftSum and rightSum accordingly.
 *  A better O(Q+N) solution is to use the fact that values are in range -2000 to 2000.
 */
public class AbsoluteElementSum {
    private static int MAX_RANGE = 2000;

    private static void getQuerySums(int[] indexNum, int[] query) {

        //Store sum of numbers
        long[] prefixSum = new long[indexNum.length];
        //We also need to store how many numbers are actually there till each index
        int[] numCount = new int[indexNum.length];
        int sum=0;
        int count=0;
        for(int i=0;i<indexNum.length;i++) {
            sum+=indexNum[i]*(i-MAX_RANGE);
            prefixSum[i]=sum;
            count+=indexNum[i];
            numCount[i]=count;
        }

        //For each query we need to find closest greater element in indexNum for opposite of the query
        long qPrefixSum = 0;
        int searchIndex = 0;
        StringBuffer sb = new StringBuffer();
        long leftSum = 0;
        long rightSum = 0;
        int temp = 0;
        long prefSum = 0;
        for(int i=0;i<query.length;i++) {

            temp = 0;
            prefSum = 0;
            leftSum=0;
            rightSum=0;
            qPrefixSum+=query[i];
            searchIndex = (int)(-qPrefixSum+MAX_RANGE);
            if(searchIndex<0) {
                temp = 0;
                prefSum = 0;
                leftSum = 0;
                rightSum = prefixSum[indexNum.length-1];
                rightSum = rightSum+(numCount[indexNum.length-1])*qPrefixSum;
            }else if(searchIndex>2*MAX_RANGE) {
                temp = numCount[indexNum.length-1];
                rightSum = 0;
                prefSum = prefixSum[indexNum.length-1];
                leftSum = Math.abs(prefSum+temp*qPrefixSum);
            }else {
                temp = numCount[searchIndex];
                prefSum = prefixSum[searchIndex];
                leftSum = Math.abs(prefSum+temp*qPrefixSum);
                rightSum = prefixSum[indexNum.length-1]-prefSum;
                rightSum = rightSum+(numCount[indexNum.length-1]- temp)*qPrefixSum;
            }

            sb.append((leftSum+rightSum)+"\n");
        }
        System.out.print(sb.toString());
    }
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.valueOf(br.readLine());
            String numStr = br.readLine();
            String[] nums = numStr.split(" ");
            numStr = null;

            //Stores values from -MAX_RANGE to MAX_RANGE. MAX_RANGE+1+MAX_RANGE
            int[] allNums = new int[2*MAX_RANGE+1];
            for(int i=0;i<=2*MAX_RANGE;i++)
                allNums[i]=0;

            for(int i=0;i<nums.length;i++) {
                allNums[Integer.valueOf(nums[i])+MAX_RANGE]++;
            }
            nums = null;

            int Q = Integer.valueOf(br.readLine());
            numStr = br.readLine();
            nums = numStr.split(" ");
            numStr = null;
            int[] query = new int[Q];
            for(int i=0;i<nums.length;i++) {
                query[i]=Integer.valueOf(nums[i]);
            }
            nums = null;
            getQuerySums(allNums, query);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
