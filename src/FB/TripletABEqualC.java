package FB;

/**
 * Given array of integers find if triplet exists such that a+b = c
 *
 * Method1: Hashing. Sort the array and then for every element its possible sum pair can be on left only. So incrementally keep checking and adding all pairs till visited element
 * in hash table. O(N^2)
 *
 * Method2: Sort the array then for every element i=n-1...2 find if pair exists equal to A[i] using window algo with l = 0 and r=i-1
 */
public class TripletABEqualC {
}
