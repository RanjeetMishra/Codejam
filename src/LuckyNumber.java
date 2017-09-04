/**
 * Created by ranjem on 9/2/17.
 *
 * If a number can be written as 7*a+4*b
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class LuckyNumber {



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long n = in.nextLong();

            int a =0;
            int b=0;

            //If b==0
            if(n%7==0 || n%4==0) {
                System.out.println("YES");
            }else {
                long d = n/7;
                long remainder = n%7;
                String isLucky = "NO";
                for(int i=0;i<=d;i++) {
                    long temp = remainder+7*i;
                    if(remainder%4==0) {
                        isLucky = "YES";
                        break;
                    }
                }

                System.out.println(isLucky);

            }
        }
    }

}
