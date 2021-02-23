package com.std.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiangwu
 * @title: Demo1
 * @projectName ThreadDemo
 * @date 2020/12/16 10:38
 */
public class Demo1 {
    private static boolean hasCycle(Node head){
        Node slow = head;
        Node fast = head.next.next;
        while (slow != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                return true;
            }
        }
        return false;
    }

    private static boolean hasCycle2(Node head){
        HashMap<Object, Object> maps = new HashMap<Object, Object>();
        while (head.next!=null){
            head = head.next;
            if (maps.containsKey(head)){
                return true;
            }
            maps.put(head, head);
        }
        return false;
    }

    private static class Node{
        Integer data;
        Node next;
        public Node(Integer data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6= new Node(6);
        Node node7 = new Node(7);
        node1.next =node2;
        node2.next =node3;
        node3.next =node4;
        node4.next =node5;
        node5.next =node6;
        node6.next =node7;
        node7.next =node4;

        System.out.println(hasCycle(node1));
    }
}
