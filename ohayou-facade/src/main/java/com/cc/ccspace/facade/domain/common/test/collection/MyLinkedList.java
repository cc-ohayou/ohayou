package com.cc.ccspace.facade.domain.common.test.collection;

/**
 * @AUTHOR CF
 * 链表注意点原理：一个个的节点串联而起，有单、双向链表，每种链表又有是否循环之分
 * 循环也就是尾节点指向头结点
 * @DATE Created on 2017/4/25 0:25.
 */
public class MyLinkedList<E> {
    Node first;
    Node last;
    private  int size;


    private  static class Node<E>{
        Node next;
        Node prev;
        E item;


        public Node (E e,Node prev,Node next){
            this.item=e;
            this.prev=prev;
            this.next=next;
        }
    }
Node<E> node(int index){
    if(index<(size>>1)){//前半部分 从头结点开始遍历
        Node<E> f=first;
        for(int i=0;i<index;i++){
            f=f.next;
        }
        return f;
    }
    else{
        Node e=last;//后半部分 从尾节点开始遍历
        for(int i=0;i>index;i--){
            e=e.prev;
        }
        return e;
    }

}





}
