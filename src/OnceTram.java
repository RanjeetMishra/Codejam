/**
 * Created by ranjem on 8/7/17.
 */

//Find next lucky number. Lucky number is one with sum of first 3 digit equal to sum of last 3 digit.

import java.util.*;

/**
 * Basic idea is to divide the number between first 3 and last 3 digits. Increment last number till sum becomes equal to first one.
 * If it becomes 1000, add 1 to previous number and then we can start from 0 for the last number as a cheap solution or divide
 * try to put first sum.
  */
public class OnceTram {

    private static int getFinalNumber(int[] firstThreeDigitArray, int[] secondThreeDigitArray) {
        int result = 0;
        for(int i=0;i<=2;i++) {
            result = result*10 + firstThreeDigitArray[i];
        }
        for(int i=0;i<=2;i++) {
            result = result*10 + secondThreeDigitArray[i];
        }
        return result;
    }

    private static int getNextLuckyNumber(int n) {

        int firstThreeDigitNumber = 0;
        int secondThreeDigitNumber = 0;
        int[] firstThreeDigitArray = new int[3];
        int[] secondThreeDigitArray = new int[3];

        int firstThreeDigitSum = 0;
        int secondThreeDigitSum = 0;

        int temp = n;
        int count = 0;
        while(count<3) {
            secondThreeDigitArray[3-count-1] = temp%10;
            secondThreeDigitSum += secondThreeDigitArray[3-count-1];
            secondThreeDigitNumber = secondThreeDigitNumber*10+secondThreeDigitArray[3-count-1];
            temp = temp/10;
            count++;
        }

        firstThreeDigitNumber = temp;
        count=0;
        while(temp>0) {
            firstThreeDigitArray[3-count-1] = temp%10;
            firstThreeDigitSum += firstThreeDigitArray[3-count-1];
            temp = temp/10;
            count++;
        }

        int diff = firstThreeDigitSum-secondThreeDigitSum;

        if(diff>0) {
            //Try to accomodate this difference with max from left to right
            for(int i=2;i>=0;i--) {
                if(secondThreeDigitArray[i]<9) {
                    if(diff<=(9-secondThreeDigitArray[i])) {
                        secondThreeDigitArray[i]+=diff;
                        diff=0;
                        break;   //Break or return;
                    }else {
                        diff = diff - (9-secondThreeDigitArray[i]);
                        secondThreeDigitArray[i]=9;
                    }
                }
            }
            if(diff==0) {
                return getFinalNumber(firstThreeDigitArray, secondThreeDigitArray);
            }
        }else if(diff==0) {
            //Try to find pair from end to increase first digit by 1 and decrease next by 1

            //Special cases.
            if(secondThreeDigitArray[2]==0&&secondThreeDigitArray[1]==0) {
                //TODO:: return for new number by incrementing first number by 1
                int newNumber = (firstThreeDigitNumber+1)*1000+secondThreeDigitNumber;
                return  getNextLuckyNumber(newNumber);
            }else if(secondThreeDigitArray[2]>0 && secondThreeDigitArray[1]<9) {
                secondThreeDigitArray[2]-=1;
                secondThreeDigitArray[1]+=1;
                return getFinalNumber(firstThreeDigitArray, secondThreeDigitArray);
            }else if(secondThreeDigitArray[0]<9) {
                secondThreeDigitArray[0]+=1;
                int aux = secondThreeDigitArray[1];
                secondThreeDigitArray[1] = secondThreeDigitArray[2]-1;
                secondThreeDigitArray[2] = aux;
                if(secondThreeDigitArray[1]<0) {
                    secondThreeDigitArray[1] = 0;
                    secondThreeDigitArray[2]-=1;
                }
                return getFinalNumber(firstThreeDigitArray, secondThreeDigitArray);
            }else {
                //Return for next number
                return getNextLuckyNumber(n+1);
            }
        } else if(diff<0) {
            //Return for new number. We need to accomodate this diff in first number digits.
            diff = Math.abs(diff);
            for(int i=2;i>=0;i--) {
                if(secondThreeDigitArray[i]>0) {
                    if(diff<=secondThreeDigitArray[i]) {
                        secondThreeDigitArray[i]=secondThreeDigitArray[i]-diff;
                        break;   //Break or return;
                    }else {
                        diff = diff -secondThreeDigitArray[i];
                        secondThreeDigitArray[i]=0;
                    }
                }
            }
            return getNextLuckyNumber(getFinalNumber(firstThreeDigitArray,secondThreeDigitArray));
        }
        return 0;
    }

    public static void main(String args[]) {
        System.out.println(getNextLuckyNumber(156456));
    }

}
