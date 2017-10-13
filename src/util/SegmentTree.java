package util;

/**
 * Created by ranjem on 9/12/17.
 */
public class SegmentTree {

    int segmentTree[];
    int inputSize;
    public SegmentTree(int[] input, int n) {

        //Segment tree has n leaf nodes so n-1 internal nodes. But as we are using array for tree representation
        //We will have to keep array size as 2*(Math.ceil(logn)-1) to maintain the parent-child indexed relation.
        // parent i will have its child indexes 2*i+1 and 2*i+2

        int height = (int) Math.ceil(Math.log(n)/Math.log(2));
        int treeArraySize = 2*((int) Math.pow(2,height)) - 1;
        inputSize = n;
        segmentTree = new int[treeArraySize];

        constructSegmentTree(input,0,n-1,0);

    }

    private int constructSegmentTree(int[] input, int arrayStartIndex, int arrayEndIndex, int segmentTreeIndex) {

        if(arrayStartIndex==arrayEndIndex) {
            segmentTree[segmentTreeIndex] = input[arrayStartIndex];
            return input[arrayStartIndex];
        }

        //else

        int mid = (arrayStartIndex+arrayEndIndex)/2;
        int leftSegmentSum = constructSegmentTree(input, arrayStartIndex, mid,2*segmentTreeIndex+1);
        int rightSegmentSum = constructSegmentTree(input,mid+1,arrayEndIndex,2*segmentTreeIndex+2);
        segmentTree[segmentTreeIndex] = leftSegmentSum+rightSegmentSum;

        return segmentTree[segmentTreeIndex];
    }


    private int getRangeSumUtil(int arrayStartIndex, int arrayEndIndex, int segementStartIndex, int segmentEndIndex, int segmentIndex) {

        if(arrayStartIndex<=segementStartIndex && arrayEndIndex>=segmentEndIndex) {
            return segmentTree[segmentIndex];
        }

        if(arrayStartIndex>segmentEndIndex || arrayEndIndex<segementStartIndex)
            return 0;

        int mid = (segementStartIndex+segmentEndIndex)/2;
        return (getRangeSumUtil(arrayStartIndex,arrayEndIndex,segementStartIndex,mid,2*segmentIndex+1) +
                getRangeSumUtil(arrayStartIndex,arrayEndIndex,mid+1,segmentEndIndex,2*segmentIndex+2));
    }

    public int getRangeSum(int arrayStartIndex, int arrayEndIndex) {
        return getRangeSumUtil(arrayStartIndex,arrayEndIndex,0,inputSize-1,0);
    }

    private int updateInputValueUtil(int newValue, int arrayIndex, int segementStartIndex, int segmentEndIndex, int segmentIndex) {

        if( arrayIndex < segementStartIndex || arrayIndex > segmentEndIndex)
            return 0;

        if(segementStartIndex==segmentEndIndex) {
            int diff = newValue - segmentTree[segmentIndex];
            segmentTree[segmentIndex]=newValue;
            return diff;
        }

        int mid = (segementStartIndex+segmentEndIndex)/2;
        int leftDiff = updateInputValueUtil(newValue, arrayIndex, segementStartIndex, mid, 2*segmentIndex+1);
        int rightDiff = updateInputValueUtil(newValue, arrayIndex, mid+1, segmentEndIndex, 2*segmentIndex+2);
        segmentTree[segmentIndex] += Math.max(leftDiff, rightDiff);
        return Math.max(leftDiff, rightDiff);

    }
    public void updateInputValue(int newValue, int arrayindex) {
        updateInputValueUtil(newValue, arrayindex, 0, inputSize-1, 0);
    }

    public static void main(String args[])
    {
        int arr[] = {1, 3, 5, 7, 9, 11};
        int n = arr.length;
        SegmentTree  tree = new SegmentTree(arr, n);

        // Print sum of values in array from index 1 to 3
        System.out.println("Sum of values in given range = " +
                tree.getRangeSum(1, 3));
        tree.updateInputValue(2, 2);
        System.out.println("Updated sum of values in given range = " +
                tree.getRangeSum(1, 3));
    }

}
