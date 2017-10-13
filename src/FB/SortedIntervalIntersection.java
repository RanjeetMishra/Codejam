package FB;
import java.io.*;
import java.util.*;

/**
 * Return intersection of two sorted interval lists
 *
 */

public class SortedIntervalIntersection {

    private static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static List<Interval> intersectingIntervals(List<Interval> listA, List<Interval> listB) {

        List<Interval> result = new ArrayList<Interval>();
        int listALen = listA.size();
        int listBLen = listB.size();

        int i=0,j=0;

        //In every iteration i or j or both migh be incremented
        while(i<listALen && j<listBLen) {

            Interval A = listA.get(i);
            Interval B = listB.get(j);

            if(A.end < B.start) {
                //A is left to B
                i++;
            } else if(A.start > B.end) {
                //A is right to B
                j++;
            } else {
                //Intersection interval exists
                //Max of start and min of end
                Interval newInterval = new Interval(Math.max(A.start, B.start), Math.min(A.end, B.end));
                result.add(newInterval);

                // A is consumed increment i
                if(A.end < B.end) {
                    i++;
                }else if(A.end == B.end) {
                    i++;
                    j++;
                }else {
                    j++;
                }
            }

        }

        return result;
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        //Count of first interval list
        int N1 = in.nextInt();

        List<Interval> intervalListA = new ArrayList<Interval>();

        for(int i=0;i<N1;i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            intervalListA.add(new Interval(a,b));
        }

        int N2 = in.nextInt();
        List<Interval> intervalListB = new ArrayList<Interval>();

        for(int i=0;i<N2;i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            intervalListB.add(new Interval(a,b));
        }

        List<Interval> intersection = intersectingIntervals(intervalListA, intervalListB);

        for(int i=0;i<intersection.size();i++) {
            System.out.print(intersection.get(i).start+" "+intersection.get(i).end+" : ");
        }
        System.out.println();
    }
}
