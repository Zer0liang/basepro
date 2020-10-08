package com.chechetimes.wzl;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author:WangZhaoliang
 * Date:2020/5/4 10:54
 */
public class SortTest {

    List<Integer> integerList = new ArrayList<>(Arrays.asList(2, 3, 1, 5, 4, 7, 6, 8, 9));

    /**
     * 冒泡排序
     */
    @Test
    public void maoPaoSort() {
        System.out.println(integerList);
        for (int i = 0; i < integerList.size(); i++) {
            for (int j = 0; j < integerList.size() - 1 - i; j++) {
                if (integerList.get(j).compareTo(integerList.get(j + 1)) < 0) {
                    int temp = integerList.get(j);
                    integerList.set(j, integerList.get(j + 1));
                    integerList.set(j + 1, temp);
                }
            }
        }
        System.out.println(integerList);
    }

    /**
     * JDK8以前， Collections.sort()
     */
    @Test
    public void collectionSort() {
        integerList.sort(Comparator.reverseOrder());
        System.out.println(integerList);
    }

    /**
     * JDK8,list.stream.sort
     */
    @Test
    public void streamSort() {
        integerList = integerList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(integerList);
    }

    /**
     * 直接选择排序
     */
    @Test
    public void directSort() {
        for (int i = 0; i < integerList.size(); i++) {
            int maxVal = integerList.get(i);
            int maxIndex = i;
            for (int j = i + 1; j < integerList.size(); j++) {
                if (maxVal < integerList.get(j)) {
                    maxVal = integerList.get(j);
                    maxIndex = j;
                }
            }

            if (maxIndex != i) {
                int temp = integerList.get(i);
                integerList.set(i, integerList.get(maxIndex));
                integerList.set(maxIndex, temp);
            }
        }
        System.out.println(integerList);
    }

    @Test
    public void erFenSort() {
        int targetVal = 9;
        integerList.sort(Comparator.naturalOrder());

        int minIndex = 0;
        int maxIndex = integerList.size() - 1;
        int targetIndex = 0;
        boolean isFindTarget = false;

        while (minIndex <= maxIndex) {
            int mid = (minIndex + maxIndex) >>> 1;
            int val = integerList.get(mid) - targetVal;
            if (val == 0) {
                targetIndex = mid;
                isFindTarget = true;
                break;
            } else if (val < 0) {
                minIndex = mid + 1;
            } else {
                maxIndex = mid - 1;
            }
        }

        if (isFindTarget) {
            System.out.println(targetIndex);
        } else {
            System.out.println("null");
        }
    }

    public int erFenSearchIndex(List<Integer> integerList, int targetVal, int minIndex, int maxIndex) {
        assert CollectionUtils.isNotEmpty(integerList) : "parameters is null";

        if (minIndex <= maxIndex) {
            int mid = minIndex + ((maxIndex - minIndex) >> 1);
            int val = integerList.get(mid) - targetVal;
            if (val == 0) {
                return mid;
            } else if (val < 0) {
                return erFenSearchIndex(integerList, targetVal, mid + 1, maxIndex);
            } else {
                return erFenSearchIndex(integerList, targetVal, minIndex, mid - 1);
            }
        }
        return -1;
    }

    @Test
    public void testErFenSearch() {
        integerList.sort(Comparator.naturalOrder());
        System.out.println(erFenSearchIndex(integerList, 9, 0, integerList.size() - 1));
    }

    @Test
    public void testErFen() {
        List<Integer> intList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

        int targetVal = 9;
        int startIndex = 0;
        int endIndex = intList.size() - 1;
        boolean findSuccess = false;

        while (startIndex <= endIndex) {
            int midIndex = (startIndex + endIndex) >> 1;
            int midVal = intList.get(midIndex);
            if (midVal < targetVal) {
                startIndex = midIndex + 1;
            } else if (midVal > targetVal) {
                endIndex = midIndex - 1;
            } else {
                findSuccess = true;
                System.out.println("targetIndex:" + midIndex);
                break;
            }
        }

        if (!findSuccess) {
            System.out.println("在当前集合中未发现指定元素");
        }
    }

    @Test
    public void testQuickSort() {
        int[] arr = {4,1,8,5,3,2,9,10,6,7};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSort(int[] arr, int startIndex, int endIndex) {
        int left = startIndex, right = endIndex;
        int targetVal = arr[startIndex];

        while (left < right) {
            //从右向左遍历,如果比目标值小，则和目标元素交换位置
            while (left < right) {
                if (arr[right] < targetVal) {
                    swapIndexVal(arr, left, right);
                    break;
                } else {
                    right--;
                }
            }
            //从左向右遍历，如果比目标值大，则和目标元素交换位置
            while (left < right) {
                if (arr[left] > targetVal) {
                    swapIndexVal(arr, left, right);
                    break;
                } else {
                    left++;
                }
            }
        }

        if (left - 1 > startIndex) {
            quickSort(arr, startIndex, left - 1);
        }

        if ((right + 1) < endIndex) {
            quickSort(arr, (right + 1), endIndex);
        }
    }

    public void swapIndexVal(int[] arr, int sourceIndex, int targetIndex) {
        int temp = arr[sourceIndex];
        arr[sourceIndex] = arr[targetIndex];
        arr[targetIndex] = temp;
    }

}
