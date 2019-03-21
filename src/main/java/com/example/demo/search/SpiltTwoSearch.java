package com.example.demo.search;

/**
 * Created by caozhen on 2019/3/15
 *
 * 二分查找
 */
public class SpiltTwoSearch {


    /**
     * 递归算法  注意点：退出递归，mid-1或mid+1，mid如何取值
     * @param a
     * @param start
     * @param end
     * @param x
     */
    public static int search (int[] a,int start,int end,int x){

        // 退出递归机制,start或者end值是递增递减的
        if(start > end) {
            return -1;
        }
        // 拿到中间元素下标
        int mid = (start + end) / 2;

        if (a[mid] == x) {
            return mid;
        } else if (a[mid] > x) {
            // 中间值大于x，数据在左半边，下标end改变为mid-1，跳过mid
            return search(a, start, mid-1, x);
        } else{
            // 中间值小于x，数据在右半边，下标start改变为mid+1，跳过mid
            return search(a, mid+1, end, x);
        }
    }




    /**
     * 非递归算法  注意点：退出循环 <=，mid-1或mid+1，mid如何取值
     * @param a
     * @param start
     * @param end
     * @param x
     * @return
     */
    public static int search2(int[] a,int start, int end,int x){

        int mid;
        // 如果数组越界退出，数组大小为1时，start=end
        while (start<=end){
            // 拿到中间元素下标
            mid = (start +end)/2;
            if(a[mid] == x){
                return mid;
            }else if (a[mid] < x){
                // 中间值小于x，数据在右半边，下标start改变为mid+1，跳过mid
                start = mid + 1;
            }else {
                // 中间值大于x，数据在左半边，下标end改变为mid-1，跳过mid
                end = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int index = search2(a,0,a.length,6);
        System.out.println(index);
    }

}
