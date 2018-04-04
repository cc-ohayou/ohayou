package com.cc.ccspace.facade.domain.common.test;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/3/10 12:49.
 */
public class JVMTest {
  /*  Heap  初始参数还没调用test方法时 新生代就已经被占用26%了 所以最多再放6mb的对象会引发MinorGC
    此时无GC发生
    PSYoungGen      total 9216K, used 2163K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
    eden space 8192K, 26% used [0x00000000ff600000,0x00000000ff81cde8,0x00000000ffe00000)
    from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
    to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
    ParOldGen       total 10240K, used 0K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
    object space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
    PSPermGen       total 21504K, used 2942K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
    object space 21504K, 13% used [0x00000000f9a00000,0x00000000f9cdfb10,0x00000000faf00000)
 */
  private static final int _1MB=1024*1024;

   public static void main(String [] args ){

//        testAllocation();


    }




   /*情形一：新生代刚好用完 还是无GC发生
    刚好申请6mb内存新生代刚好用完 不发生垃圾回收
    Heap
 PSYoungGen      total 9216K, used 8192K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 100% used [0x00000000ff600000,0x00000000ffe00000,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 0K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
 PSPermGen       total 21504K, used 2942K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
  object space 21504K, 13% used [0x00000000f9a00000,0x00000000f9cdfb48,0x00000000faf00000)
    byte[] allo,allo1,allo2,allo3;
    allo=new byte[_1MB];
    allo1=new byte[2*_1MB];
    allo2=new byte[2*_1MB];
    allo3=new byte[_1MB];*/

  /*情形二：分配担保发生 先minorGC再FUllGC 还有种情形当然是只有minorGC没有FULLGC 要survivor空间足够大
   或者设计一下代码
   使得可回收的对象小于survivor大小 放入的新对象在MinorGC后可以放入新生代
   新生代不够用 不能放置新的对象 先发生MinorGC但又发现survivor空间不够用放不下这些可被回收的对象 因为是采用的
   复制算法是会把所有新生代存活的对象复制到survivor区的但结果发现不行 于是通过分配担保机制转移到老年代
   新生的 allo3=new byte[1024*800];最后放在新生代中GC数据只是代表GC时的新生代和老年代大小情况 最终的数据分布情况并不是如此
   而是要看最后的输出Heap PSYoungGen
[GC [PSYoungGen: 7919K->944K(9216K)] 7919K->6864K(19456K), 0.0035884 secs] [Times: user=0.06 sys=0.03, real=0.00 secs]
[Full GC [PSYoungGen: 944K->0K(9216K)] [ParOldGen: 5920K->6654K(10240K)] 6864K->6654K(19456K) [PSPermGen: 2936K->2935K(21504K)], 0.0108880 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
Heap
 PSYoungGen      total 9216K, used 1213K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 14% used [0x00000000ff600000,0x00000000ff72f5b0,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 6654K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 64% used [0x00000000fec00000,0x00000000ff27f890,0x00000000ff600000)
 PSPermGen       total 21504K, used 2973K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
  object space 21504K, 13% used [0x00000000f9a00000,0x00000000f9ce75f8,0x00000000faf00000)
   byte[] allo,allo1,allo2,allo3,allo4;
    allo=new byte[_1MB];
    allo1=new byte[2*_1MB];
    allo2=new byte[2*_1MB];
    allo4=new byte[1024*800];
    allo3=new byte[1024*800];*/
  /*  public static void testAllocation(){


        byte[]allo1,allo2,allo3;
//        byte[] allo,allo,allo1,allo2,allo3,allo4;
         allo1=new byte[1/4*_1MB];//第一次gc后移动到survivor区
         allo2=new byte[4*_1MB];//分配失败 移动到老年代 allo3分配到新生代
         allo3=new byte[4*_1MB];
     *//*    allo3=null;//allo3引用指向null指针   释放与新生代的内存的关联  但这块内存（命名为a）并没有被释放 要等待下次新生代gc释放
         allo3=new byte[4*_1MB];// allo3再次指向新的4Mb大小内存（b） 发现新生代内存不够 果断清除没有引用链的上一块内存a
*//*
    }*/
/*
      -首先看 无GC时内存占用
        3220
       -1024（ _1MB=1024*1024 直接分配的内存大小）
       =2106（多出的内存就是这部分了 方法运行起来的堆栈所占用的内存 等等）
        Heap
        def new generation   total 9216K, used 3220K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  39% used [0x00000000fec00000, 0x00000000fef25388, 0x00000000ff400000)
        from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
        tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
        Metaspace       used 3265K, capacity 4494K, committed 4864K, reserved 1056768K
        class space    used 355K, capacity 386K, committed 512K, reserved 1048576K

        Process finished with exit code 0

*/


/* 只有一次gc发生后的内存使用状况
   from区域被占用了99% 我们的allo1只有256k 其余的别问我 内存中你以为就这个allo1可以被转移到survior中吗
   那就天真了 支持程序运行的还有很多细小的变量存在呢
[GC (Allocation Failure) [DefNew: 7282K->1023K(9216K), 0.0052388 secs] 7282K->5242K(19456K), 0.0052945 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
        Heap
        def new generation   total 9216K, used 5284K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  52% used [0x00000000fec00000, 0x00000000ff029148, 0x00000000ff400000)
        from space 1024K,  99% used [0x00000000ff500000, 0x00000000ff5ffff8, 0x00000000ff600000)
        to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        tenured generation   total 10240K, used 4218K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        the space 10240K,  41% used [0x00000000ff600000, 0x00000000ffa1ea08, 0x00000000ffa1ec00, 0x0000000100000000)
        Metaspace       used 3298K, capacity 4494K, committed 4864K, reserved 1056768K
        class space    used 357K, capacity 386K, committed 512K, reserved 1048576K*/



        /*
   第一次gc 新生代剩余
   [GC (Allocation Failure) [DefNew: 7282K->1023K(9216K), 0.0080722 secs] 7282K->5242K(19456K), 0.0081441 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
   7282=
   3220
   +256=
    3476
   +4096 5376 （局部变量直接占用的内存）
         2106（方法变量线程堆栈这些额外的内存开销）
         ~ 7482 近似值
         虚拟机额外的细小内存开销是很复杂的 详细计算出来非常麻烦 我们平常只关注常用的即可

  [GC (Allocation Failure) [DefNew: 5202K->0K(9216K), 0.0025882 secs] 9420K->5159K(19456K), 0.0026395 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
        第二次gc  道理其实一样的了 allo3再次占用新生代内存 但是allo1从survivor移到老年代
        上次gc后新生代剩下的
        1023+
        4096 allo3新占用的内存
        5119~ 5202K

        两次gc完毕按理讲 新生代只剩下一个4096的内存占用 这里是4232 道理同上 老年代5159
        4096
         256

         4232
         5159
         9391
         4096
         4096
          256
         8192
         8448
         9391
          947

        Heap
        def new generation   total 9216K, used 4232K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        eden space 8192K,  51% used [0x00000000fec00000, 0x00000000ff021ed8, 0x00000000ff400000)
        from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400298, 0x00000000ff500000)
        to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
        tenured generation   total 10240K, used 5159K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        the space 10240K,  50% used [0x00000000ff600000, 0x00000000ffb09c70, 0x00000000ffb09e00, 0x0000000100000000)
        Metaspace       used 3305K, capacity 4494K, committed 4864K, reserved 1056768K
        class space    used 359K, capacity 386K, committed 512K, reserved 1048576K*/





}
