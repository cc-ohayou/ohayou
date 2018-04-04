package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/8 12:59.
 */
public class TreeSort<Key extends Comparable<Key> ,Value> {
    private Node root;
    private class Node {
        int N;//每个节点下所有节点个数之和
        Node left;
        Node right;
        private Value value;
        private Key key;
    public Node(Key key,Value value,int n){
    this.key=key;
    this.value=value;
    this.N=n;
    }

    }
    public int size(){
        return TreeSort.this.size(root);
    }

    private int size(Node x) {
        if(x==null){
            return 0;
        }
        else return x.N;
    }

    private  void put(Key key,Value val){
        root=put(root,key,val);
    }
    private Node put(Node x,Key key,Value val){
        if(x==null)
            return new Node(key,val,1);
        int cmp=key.compareTo(x.key);
        if(cmp<0) x.left=put(x.left,key,val);
        else if(cmp>0) x.right=put(x.right,key,val);
        else
            x.value=val;
        x.N=size(x.left)+size(x.right)+1;
        return x;

    }
    private Value get(Key key){
        return get(root,key);
    }

    private Value get(Node x, Key key) {
        if(x==null)
        {
            return null;
        }
        int cmp=key.compareTo(key);
        if(cmp<0) return get(x.left,key);
        else if(cmp>0)return get(x.right,key);
        else return x.value;
    }

/*
    先序遍历：先根再←后→   中序遍历：先←后根再→  后序遍历：先←再→后根
    自上而下的遍历
*/

    private void show(Node x){
        if(x==null)
            System.out.println();
        else {
            System.out.println(x.left);
            System.out.println(x.right);
             show(x.left);
            show(x.right);//自上而下的遍历二叉树
        }

    }
/**  * describe: PreOrderVisitTree       先序遍历
	 * @author CAI.F
	 * @date:  日期:2017/5/10 时间:0:09
	 * @param
	 */
    private void showFirst(Node n){
        if(n==null)
            System.out.println("");
        else
        {
            System.out.println(n.value);
            showFirst(n.left);
            showFirst(n.right);
        }


    }

/**  * describe: 中序遍历
	 * @author CAI.F
	 * @date:  日期:2017/5/10 时间:0:15
	 * @param
	 */
    private  void  midOrderVisit(Node n){

        if(n==null)
            System.out.println("");
         else{
            midOrderVisit(n.left);
            System.out.println(n.value);
            midOrderVisit(n.right);
        }

    }

    /**  * describe: 后序遍历
     * @author CAI.F
     * @date:  日期:2017/5/10 时间:0:15
     * @param
     */
    private  void  afterOrderVisit(Node n){

        if(n==null)
            System.out.println("");
        else{
            afterOrderVisit(n.left);
            afterOrderVisit(n.right);
            System.out.println(n.value);
        }

    }


}
