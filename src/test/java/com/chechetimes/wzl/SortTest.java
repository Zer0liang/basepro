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

}
