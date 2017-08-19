/**
 * Created by ranjem on 8/19/17.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class MaximumStockCount {

    static class StockPriceAndLimitPair implements Comparable<StockPriceAndLimitPair>{
        int stockPrice;
        int buyLimit;

        public StockPriceAndLimitPair(int stockPrice, int buyLimit) {
            this.stockPrice = stockPrice;
            this.buyLimit = buyLimit;
        }

        public int compareTo(StockPriceAndLimitPair stockData) {
            if(this.stockPrice<stockData.stockPrice)
                return -1;
            else if(this.stockPrice==stockData.stockPrice)
                return 0;
            else
                return 1;
        }

    }

    static long buyMaximumProducts(int n, long k, List<StockPriceAndLimitPair> stockDataArray) {
        //Sort the array w.r.t stock prices
        Collections.sort(stockDataArray);
        long maxStockCount = 0;
        long stockValue = 0;
        long totalAmount = k;
        for(int i=0;i<n;i++) {
            if(stockValue==totalAmount)
                break;
            StockPriceAndLimitPair stockData = stockDataArray.get(i);
            if((stockValue+(stockData.buyLimit*stockData.stockPrice)) <= totalAmount) {
                maxStockCount+=stockData.buyLimit;
                stockValue+= (stockData.buyLimit*stockData.stockPrice);
            }else {
                long temp = totalAmount-stockValue;
                maxStockCount+= temp/stockData.stockPrice;
                break;
            }
        }

        return maxStockCount;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<StockPriceAndLimitPair> stockPriceAndLimits = new ArrayList(n);
        for(int i = 0; i < n; i++){
            StockPriceAndLimitPair newStockData = new StockPriceAndLimitPair(in.nextInt(),i+1);
            stockPriceAndLimits.add(newStockData);
        }
        long k = in.nextLong();
        long result = buyMaximumProducts(n, k, stockPriceAndLimits);
        System.out.println(result);
        in.close();
    }
}
