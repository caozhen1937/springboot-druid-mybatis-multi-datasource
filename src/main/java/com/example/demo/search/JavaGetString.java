package com.example.demo.search;

import java.util.*;
/**
寻找一个字符串中的最长的重复子串
*/
class SubStr {
    private String str;// 子串
    private int count;// 重复次数

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SubStr(String str, int count) {
        this.str = str;
        this.count = count;
    }

    //两个参数的set、get方法


    /**获取子串长度*/
    public int getLength() {
        return str.length();
    }

}
class JavaGetStringSon {

    private static String mString = "abcdefgaceaceacw";// 待检测字符串
    private static Map<String, Integer> mMap = new HashMap<String, Integer>();// 所有子串集合<子串,重复次数>
    private static List<SubStr> mList = new ArrayList<SubStr>();// 重复子串列表

    public static void main(String[] args) {

        //我们遍历了字符串的所有子串，并利用HashMap键的唯一性保存了各子串重复出现的次数

        for (int i = 0; i < mString.length(); i++) {// 遍历字符串的每个字符
            for (int step = 1; step <= mString.length() - i; step++) {// 步长从1开始
                String s = mString.substring(i, i + step);//取从第i个字符开始向后step个字符为子串
                if (mMap.containsKey(s)) {// 如果该子串已存在于集合中
                    int count = mMap.get(s) + 1;// 取其保存于集合中的数量并+1
                    mMap.replace(s, count);// 更新结果集
                } else {//如果集合中没有该子串，则存入
                    mMap.put(s, 1);
                }
            }
        }
        //接下来遍历Map
        for (Iterator<String> iterator = mMap.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();//键就是各个子串
            int count = mMap.get(key);//获取该子串重复出现的次数
            if (count == 1) {//如果只出现一次，就不是重复子串，pass掉
                continue;
            }
            SubStr ss = new SubStr(key, count);//是重复子串，则生成实体对象
            mList.add(ss);//保存到重复子串列表
        }

        mList.sort(new Comparator<SubStr>() {//使用自定义的Comparator来给列表排序

            @Override
            public int compare(SubStr o1, SubStr o2) {
                return o2.getLength() - o1.getLength();//排序方式为子串的长度
            }
        });//这样我们就得到了按子串长度排序的重复子串列表

        // 如果是求重复出现次数最多的子串，只要把Comparator改写一下就行了

//        mList.sort(new Comparator<SubStr>() {//使用自定义的Comparator来给列表排序
//
//            @Override
//            public int compare(SubStr o1, SubStr o2) {
//                return o2.getCount() - o1.getCount();//排序方式为子串重复出现的次数
//            }
//
//        });


        System.out.println("最长的重复字符串为: " + mList.get(0).getStr());
    }


}

