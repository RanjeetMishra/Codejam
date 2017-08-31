/**
 * Created by ranjem on 8/19/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//Given time series stock prices (ti,pi), solve n queries of two types.
//Type 1 query -> find smallest time when stock price was atleat v
//Type 2 query -> find highest stock price after time v

public class TimeSeries {



    static int findIndexForAtleastValue(int v, ArrayList<Integer> arr, int start, int end) {
        if(end==start) {
            if(arr.get(end)>=v)
                return end;
        }
        if(end<=start)
            return -1;
        int middle = (start+end)/2;
        if(arr.get(middle)==v)
            return middle;
        else if(arr.get(middle)<v)
            return findIndexForAtleastValue(v,arr,middle+1,end);
        else
            return findIndexForAtleastValue(v,arr,start,middle);
    }
    static void preProcessTypeOneData(int n,int []t, int[] p, ArrayList<Integer> typeOneTime, ArrayList<Integer> typeOnePrice) {

        //TODO:: Check for boundary condition and revisit
        int maxPriceTillNow = p[0];
        typeOnePrice.add(p[0]);
        typeOneTime.add(t[0]);

        for(int i=1;i<n;i++) {
            if(p[i]>maxPriceTillNow) {
                maxPriceTillNow = p[i];
                typeOnePrice.add(p[i]);
                typeOneTime.add(t[i]);
            }
        }

    }

    static int typeOneQuery(int v, ArrayList<Integer> typeOneTime, ArrayList<Integer> typeOnePrice) {
        //Find index of first element in typeOnePrice with value >=v.
        //Note both arrays are sorted
        int index = findIndexForAtleastValue(v,typeOnePrice,0,typeOnePrice.size()-1);
        if(index>=0)
            return typeOneTime.get(index);
        else return -1;
    }

    static int typeTwoQuery(int v, ArrayList<Integer> typeTwoTime, ArrayList<Integer> typeTwoPrice) {

        int index = findIndexForAtleastValue(v,typeTwoTime,0,typeTwoTime.size()-1);
        if(index>=0)
            return typeTwoPrice.get(index);
        else
            return -1;
    }
    static void preProcessTypeTwoData(int n,int []t, int[] p, ArrayList<Integer> typeTwoTime, ArrayList<Integer> typeTwoPrice) {

        int maxStockPriceTillNow = p[n-1];
        typeTwoPrice.add(p[n-1]);
        typeTwoTime.add(t[n-1]);

        for(int i=n-2;i>=0;i--) {
            if(p[i]>maxStockPriceTillNow) {
                maxStockPriceTillNow=p[i];
                typeTwoPrice.add(p[i]);
                typeTwoTime.add(t[i]);
            }
        }

        Collections.reverse(typeTwoPrice);
        Collections.reverse(typeTwoTime);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        int[] t = new int[n];
        for(int t_i = 0; t_i < n; t_i++){
            t[t_i] = in.nextInt();
        }
        int[] p = new int[n];
        for(int p_i = 0; p_i < n; p_i++){
            p[p_i] = in.nextInt();
        }

        //Process and store for Type 1 query.
        //We will store in two arrays time and price but store only relevant values so that v can be searched for
        //type 1 query in O(logn) and we can answer it
        ArrayList<Integer> typeOneTime = new ArrayList<>();
        ArrayList<Integer> typeOnePrice = new ArrayList<>();

        preProcessTypeOneData(n,t,p,typeOneTime,typeOnePrice);

        ArrayList<Integer> typeTwoTime = new ArrayList<>();
        ArrayList<Integer> typeTwoPrice = new ArrayList<>();

        preProcessTypeTwoData(n,t,p,typeTwoTime,typeTwoPrice);

        for(int a0 = 0; a0 < q; a0++){
            int type = in.nextInt();
            int v = in.nextInt();
            int result = 0;
            if(type==1) {
                result = typeOneQuery(v,typeOneTime,typeOnePrice);
            }else {
                result = typeTwoQuery(v,typeTwoTime,typeTwoPrice);
            }
            System.out.println(result);
        }
        in.close();
    }
}
