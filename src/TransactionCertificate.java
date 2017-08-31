/**
 * Created by ranjem on 8/20/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class TransactionCertificate {

    //Stores values of sequences as key and index of that sequence as value.
    //We start with sequence length 1. For any sequence length n, previous one is cleared and certificateArray is used to calculate
    //values for new sequence.
    static HashMap<Long, Integer> certificates = new HashMap<Long, Integer>();

    //Stores values of sequences to be used to calculate values of sequences of length+1
    static ArrayList<Long> certificateArray = new ArrayList<Long>();

    //numbers in range 1 to K. Return n for which we have found duplicate certificate.
    static String incrementalHashValueSeries(int n,long p, long modulo, int k) {

        if(n==1) {
            for(int i=1;i<=k;i++) {
                long value = i%modulo;
                if(certificates.get(value)==null) {
                    //Put value as key and certificateArray.size() is the index for the sequence
                    certificates.put(value,certificateArray.size());
                    certificateArray.add(value);
                }else {
                    //Try to return indexes in certificateArray with same value
                    return n+"_"+certificates.get(value)+"_"+certificateArray.size();
                }
            }
            return incrementalHashValueSeries(n+1,p,modulo,k);
        }

        int startIndex = 0;
        //We want to find duplicates in same size input. Clearing the hashmap now
        certificates.clear();
        ArrayList<Long> newCertificateArray = new ArrayList<Long>();
        for(int i=startIndex;i<certificateArray.size();i++) {
            for(int j=1;j<=k;j++) {
                long value = ((certificateArray.get(i) *p)%modulo+j)%modulo;
                if(certificates.get(value)==null) {
                    //Put value as key and certificateArray.size() is the index for the sequence
                    certificates.put(value,newCertificateArray.size());
                    newCertificateArray.add(value);
                }else {
                    return n+"_"+certificates.get(value)+"_"+newCertificateArray.size();
                }
            }
        }
        certificateArray = newCertificateArray;
        return incrementalHashValueSeries(n+1,p,modulo,k);
    }

    //Generate sequence if we know sequence length and index.
    //For example for k=3  and subSequenceLength=2 and index=3 it should output 21
    // For subsequencelength two series is like 11 12 13 21 22 23 and at index=3 its 21 :)
    static String subSequence(int subSequenceLength, int currentIndex, int k) {
        if(subSequenceLength==1)
            return (currentIndex+1)+"";
        return subSequence(subSequenceLength-1,currentIndex/k,k)+" "+(currentIndex%k + 1);
    }


    static String specialSequence(long m, long p, int k) {
        ArrayList<Long> result = new ArrayList<>();

        while(m>0) {
            long temp = m%p;
            if(temp>(k-1))
                return null;
            result.add(temp);
            m=m/p;
        }

        String sequence = (result.get(0)+1)+"";
        for(int i=1;i<result.size();i++) {
            sequence = sequence+" "+(result.get(i)+1);
        }
        return sequence;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int p = in.nextInt();
        long m = in.nextLong();


        int subSequenceLength = 0;
        String firstSequence = "";
        String secondSequence = "";
        boolean found = false;
        if(k>=p) {
            //Generate sequence with base p for m
            firstSequence = specialSequence(m,p,k);
            int size = (firstSequence.split(" ")).length;
            secondSequence = 1+"";
            subSequenceLength = size;
            for(int i=1;i<size;i++) {
                secondSequence = secondSequence+" "+1;
            }
            found = true;
        }else {
            //Lets try for m,2m .... and see if we can find values in range k
            for(long i=m;i<1000*m;i+=m) {
                firstSequence = specialSequence(i,p,k);
                if(firstSequence!=null)
                    break;
            }
            if(firstSequence!=null) {
                int size = (firstSequence.split(" ")).length;
                secondSequence = 1 + "";
                for (int i = 1; i < size; i++) {
                    secondSequence = secondSequence + " " + 1;
                }
                subSequenceLength = size;
                found = true;
            }
        }

        if(!found)

        {
            //Lets find two sequences of length i with same hash value, then we can append 1s for the answer
            String result = incrementalHashValueSeries(1, p, m, k);
            String[] array = result.split("_");
            firstSequence = subSequence(Integer.valueOf(array[0]), Integer.valueOf(array[1]), k);
            secondSequence = subSequence(Integer.valueOf(array[0]), Integer.valueOf(array[2]), k);

            subSequenceLength = Integer.valueOf(array[0]);
        }

        if (subSequenceLength % n != 0) {
            //Append ones to make it multiple of n
            int addLength = n - (subSequenceLength % n);
            for (int i = 0; i < addLength; i++) {
                firstSequence += " " + 1;
                secondSequence += " " + 1;
            }
        }
        System.out.println(firstSequence);
        System.out.println(secondSequence);
        in.close();
    }

}
