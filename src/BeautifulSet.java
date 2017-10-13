import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
//https://www.hackerrank.com/challenges/beautiful-3-set/problem

public class BeautifulSet {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        HashSet<Integer> X = new HashSet<Integer>();
        HashSet<Integer> Y = new HashSet<Integer>();
        HashSet<Integer> Z = new HashSet<Integer>();

        StringBuffer str = new StringBuffer();
        int count = 0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                int temp = n-i-j;
                if(temp<0)
                    continue;
                if(i!=j && temp!=i && temp!=j) {
                    if(X.contains(i)||Y.contains(j)||Z.contains(temp))
                        continue;
                    X.add(i);
                    Y.add(j);
                    Z.add(temp);
                    count++;
                    str.append(i+" "+j+" "+temp+"\n");
                }
            }
        }
        System.out.println(count);
        System.out.println(str);
    }
}
