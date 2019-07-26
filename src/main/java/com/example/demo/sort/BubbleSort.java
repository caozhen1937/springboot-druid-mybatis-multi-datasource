package com.example.demo.sort;

/**
 * Created by caozhen on 2019/1/25
 */

/**
 * 冒泡排序：
 *
 * 时间复杂度为：O(n2)
 *
 * 双重循环语句，外层控制循环多少趟，内层控制每一趟的循环次数。
 *
 * 每进行一趟排序，就会少比较一次，因为每进行一趟排序都会找出一个较大值。
 *
 * 外层循环 n次，内层循环n-i次
 */
public class BubbleSort {

    public static void main(String[] args) {

        int[] a = {10,50,30,69,79,40,20,80};

        int n = a.length;
        int tmp = 0;

        for (int i=0; i<n-1; i++){
            for (int j=0; j < n-i-1; j++){

                if(a[j] > a[j+1]){
                    tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] =tmp;
                }
            }

        }

        for (int i=0; i<a.length; i++) {
            System.out.printf("%d ", a[i]);
        }
        System.out.printf("\n");

    }
}
