package com.example.demo.link;


/**
 * @author chen zhen
 * @version 创建时间：2018年1月17日 上午10:56:50
 * @value 类说明: 链表的一些操作
 */
public class SingleLink {
    private Node first; // 一个头结点
    private int N;            // 链表长度

    public SingleLink() {
        first = null;
        N = 0;
    }

    /**
     * 自定义一个节点类
     */
    public static class Node{
        int item;
        Node next;
    }

    // 链表表头添加一个新的结点
    public void addNode(int i) {
        Node temp = first;
        first = new Node();
        first.item = i;
        first.next = temp;
        N++;
    }

    // 删除一个尾结点
    public void deleteLastNode() {
        if (N == 0)
            return;
        if (N == 1)
            first = null;
        else
            for (Node x = first; x != null; x = x.next) {
                if (x.next.next == null)
                    x.next = null;
            }
        N--;
    }

    // 删除指定位置的结点
    public void deletePosition(int position) {
        if (position  == 1) {
            first = null;
            return;
        }
        if (position > N)
            System.out.println("the position is too large");
        else {
            int index = 1; // 记录遍历的位置
            for (Node x = first; x != null; x = x.next) {
                if (index + 1 == position) {
                    x.next = x.next.next;
                    break;
                }
                index++;
            }
        }
    }

    //在指定位置添加结点
    public void addToPosition(int position, int value) {
        if (position > N + 1)
            System.out.println("the position is too large");
        else {
            int index = 1;
            for (Node x = first; x != null; x = x.next) {
                if (index + 1 == position) {
                    Node temp = new Node();
                    temp.item = value;
                    temp.next = x.next;
                    x.next = temp;
                    break;
                }
                index++;
            }
        }
    }

    // 查找链表里是否含有某个值
    public boolean find(int key) {
        for (Node x = first; x != null; x = x.next) {
            if (x.item == key)
                return true;
        }
        return false;
    }


    // 定义一个 toString，方便打印
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Node x = first; x != null; x = x.next) {
            str.append(x.item);
            str.append(" ");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        SingleLink link = new SingleLink();
        link.addNode(1);
        link.addNode(2);
        link.addNode(3);
        link.addNode(4);

        System.out.println(link.toString());

        link.deleteLastNode();
        System.out.println(link.toString());

        link.deletePosition(2);
        System.out.println(link.toString());

        link.addToPosition(2, 10);
        System.out.println(link.toString());

        System.out.println(link.find(2));
    }



}
