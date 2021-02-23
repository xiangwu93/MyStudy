package com.std.likou;

import java.util.Stack;

/**
 * @author chenxiangwu
 * @title: LinkedReverse
 * @projectName ThreadDemo
 * @date 2021/1/25 14:18
 */
public class LinkedReverse {

    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 递归反转
     * @param head
     * @return
     */
    public static Node reverse(Node head){
        if (head == null || head.next == null){
            return head;
        }
        Node temp = head.next;
        Node newHead = reverse(head.next);
        temp.next = head;
        head.next =null;
        return newHead;
    }

    /**
     * 循环
     * @param head
     * @return
     */
    public static Node reverse1(Node head){
        Node newNode = null;
        Node curNode = head;
        while (curNode != null){
            Node tempNode = curNode.next;
            curNode.next = newNode;
            newNode = curNode;
            curNode = tempNode;
        }
        return newNode;
    }

    public static Node reverse2(Node head){
        Stack<Node> nodeStack = new Stack<>();
        Node newNode = null;
        while (head != null){
            nodeStack.push(head);
            head = head.next;
        }

        if (!nodeStack.isEmpty()){
            newNode = nodeStack.pop();
        }

        while (!nodeStack.isEmpty()){
            Node tempNode = nodeStack.pop();
            tempNode.next = tempNode;
            tempNode = null;
        }

        return newNode;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        System.out.println(reverse(node1));
        System.out.println(reverse1(node1));
        System.out.println(reverse2(node1));
    }
}
