package com.cc.ccspace.facade.domain.common.test.sort;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ListReverse {

    public static ListNode reverseListByLocal(ListNode head){
        /*
        pCur是要插入到新链表的节点。
        pNex是临时保存的pCur的next。
        pNex保存下一次要插入的节点
        把pCur插入到dummy中
        纠正头结点dummy的指向
        pCur指向下一次要插入的节点*/
        ListNode dummy = new ListNode(-1,null);
               ListNode pCur = head;
               //1号位
                while (pCur != null) {
                        //2号位 下一个节点的指针先拿出来
                        ListNode pNex = pCur.next;
                        //1号位插入到新链表
                        pCur.next = dummy.next;
                        dummy.next = pCur;
                        //当前节点转换为2号位 继续循环插入
                        pCur = pNex;
                    }
                return dummy.next;
       /* //先把头部的节点作为虚拟节点的下一节点
        resultList.next=head;
        //头结点指1号位
        if(head==null||head.next==null){
            //如果到了尾结点则返回
            return head;
        }
        //2号位
        ListNode f = head.next;
        //3号位
        ListNode s = f.next;

        while(s!=null){
            //固定节点的下一节点指向下下个节点
            //
            f.next=s.next;
            //虚拟头结点指向最新的头结点
            resultList.next=s;
            // s转移为真正的头结点 下一节点指向f
            s.next=f;
            // s变为f指向的下一节点开始新的循环
            s=f.next;
        }
        return resultList.next;
*/

       /* resultList.next= listNode;
        ListNode p = listNode;
        ListNode pNext = p.next;
        while (pNext!=null){
            p.next = pNext.next;
            pNext.next = resultList.next;
            resultList.next = pNext;
            pNext=p.next;
        }
        return resultList.next;*/
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(3, null);
        ListNode node2 = new ListNode(2, node1);
        ListNode node3 = new ListNode(1, node2);
        System.out.println(node3);
        ListNode afterNode = reverseListByLocal(node3);
        System.out.println(afterNode);
    }
}

@Data
class ListNode {
    public int data;
    public ListNode next;

    public ListNode(int data, ListNode next) {
        this.data = data;
        this.next = next;
    }
}