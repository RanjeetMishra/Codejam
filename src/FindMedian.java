import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Using quick sort for the sake of it.
 */

public class FindMedian {


    private static int findKthElement(int[] arr, int lIndex, int rIndex, int k) {

        //TODO
        return 0;

    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int arr[] = new int[n];
        for(int i=0;i<n;i++) {
            arr[i] = in.nextInt();
        }
        System.out.println(findKthElement(arr,0,n-1,n/2));
    }
}
