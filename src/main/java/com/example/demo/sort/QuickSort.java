package com.example.demo.sort;

import java.util.Arrays;

/**
 * Created by caozhen on 2019/1/24
 */
public class QuickSort {

    /**
     * 快速排序（递归）
     *
     * 时间复杂度：O(n*log2n)
     *
     * 1. 从数列中挑出一个元素，称为"基准"（pivot）。
     * 2. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
     * 3. 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     * @param a   待排序数组
     * @param low   左边界
     * @param high  右边界
     */
    public static void quickSort(int[] a, int low, int high){
        if(a.length <= 0) return;
        if(low >= high) return;
        int i = low;
        int j = high;

        int temp = a[i];   //挖坑1：保存基准的值
        // 只要i不等于j 就进行循环
        while (i < j){

            //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while(i < j && a[j] >= temp){
                j--;
            }
            // 执行完上面循环  表示遇到了后边值的比基准值小，要对调。
            if(a[j] < temp)
                a[i] = a[j];

            //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
            while(i < j && a[i] <= temp){
                i++;
            }
            // 执行完上边循环，表示遇到了比基准值大的值要将其放到后边的坑
            if(a[i] > temp)
                a[j] = a[i];

        }
        // 这时 跳出了 “while (i<j) {}” 循环
        // 说明 i=j 左右在同一位置
        a[i] = temp;   //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(a));
        quickSort(a, low, i-1);
        quickSort(a, i+1, high);
    }

    public static void main(String[] args) {
        int a[] = {80,700,3000};
//        int a[] = {80,70,30,20,90,40};
        int i;

        System.out.printf("before sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        long st =System.currentTimeMillis();
        quickSort(a,0,2);
        long en =System.currentTimeMillis();
        System.out.println("耗时" + (en-st) + "毫秒");

        System.out.printf("after  sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

    }
}
