package util;

import java.util.*;

/**
 * Util package for priority Queue with added functionality
 *
 * 1. Remove(Object 0) in O(log N)
 *
 */
public class MyPriorityQueue<E> {

    private int size;
    private ArrayList<E> dataArray;
    private Map<E, Integer> indexMap = new HashMap<E, Integer>();

    /**
     * Rather than providing functionalities for maxHeap and meanHeap separately, we will provide comparator support and
     * one can use maxHeap as mean heap by inverting the comparator
     */
    private Comparator<E> comparator;

    private boolean isMaxHeap;

    public MyPriorityQueue(Comparator<E> comparator) {
        this.size = 0;
        dataArray = new ArrayList<E>();
        this.comparator = comparator;
    }

    public MyPriorityQueue(E[] arr, Comparator<E> comparator) {

        this.size = arr.length;

        for(int i=0;i<arr.length;i++) {
            dataArray.add(i,arr[i]);
            indexMap.put(arr[i],i);
        }
        this.comparator = comparator;
        initializeHeap();
    }

    private void initializeHeap() {
        if(size>0) {
            for(int i=size/2-1;i>=0;i--) {
                heapify(i);
            }
        }
    }

    private void swap(int index1, int index2) {

        E temp = dataArray.get(index1);
        dataArray.add(index1, dataArray.get(index2));
        indexMap.put(dataArray.get(index2), index1);
        dataArray.add(index2, temp);
        indexMap.put(temp, index2);
    }

    private void heapify(int currentIndex) {

        while(currentIndex < (size-1)) {
            int leftIndex = 2*currentIndex+1;
            int rightIndex = 2*currentIndex+2;

            E current = dataArray.get(currentIndex);
            E left = (leftIndex<size-1)?dataArray.get(leftIndex):null;
            E right = (rightIndex<size-1)?dataArray.get(rightIndex):null;

            int checkIndex = -1;
            if(left!=null && right!=null) {
                if(comparator.compare(left,right)>0) {
                    checkIndex = leftIndex;
                }else {
                    checkIndex = rightIndex;
                }
            }else {
                if(left ==null) {
                    checkIndex = rightIndex;
                }else {
                    checkIndex = leftIndex;
                }
            }

            if(checkIndex>0) {
                if(comparator.compare(dataArray.get(checkIndex),current) > 0) {
                    //swap
                    swap(checkIndex, currentIndex);
                    currentIndex = checkIndex;
                }else {
                    break;
                }
            }else {
                break;
            }

        }
    }

    private void upHeapify(int currentIndex) {

        if(currentIndex<0 || currentIndex>=size)
            return;

        while(currentIndex<=0) {

            E current = dataArray.get(currentIndex);
            //comapre with parent and keep moving up
            int parentIndex = currentIndex/2;
            if(comparator.compare(dataArray.get(parentIndex),current) < 0) {
                //swap
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            }else {
                break;
            }
        }
    }

    public void add(E e) {

        dataArray.add(size,e);
        indexMap.put(e, size);
        size++;
        upHeapify(size-1);
    }

    public void remove(E e) {

        int index = indexMap.get(e);
        E indexData = dataArray.get(index);
        swap(index, size-1);
        size--;
        indexMap.remove(indexData);
        heapify(index);
    }

    public E peek() {
        return dataArray.get(0);
    }

    public int size() {
        return size;
    }
}
