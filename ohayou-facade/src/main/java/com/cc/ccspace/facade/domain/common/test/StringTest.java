package com.cc.ccspace.facade.domain.common.test;


import com.cc.ccspace.facade.domain.common.util.FileUploadUtil;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/3/12 20:42.
 */

public class StringTest {
    public void test(){

    }
    public String test(int a){
        return "";
    }

    public static void main(String[] args) {
        Map h=new Hashtable();
        Map m2=new TreeMap();
//        m2.put(new StringTest(),new StringTest());//存入的元素要求实现Comparable接口或者传入comparator
//h.put("cc",null);//键值都不允许为null
//        listTest();
linkedListTest();
        Vector v=new Vector();
        AtomicInteger at=new AtomicInteger(0);
        Integer a=1;
        Integer b=2;
        Integer c=3;
        Integer d=3;
        System.out.println(c.equals(a+b));
        System.out.println(c==d);
        System.out.println();
        Long g=3L;
        System.out.println(g.equals(a+b));//equals为false 源码中会首先判断Object是不是Integer 不是的话直接返回false
        System.out.println(g==(a+b));//==的话会出发自动拆装箱转型操作
      //  BigInteger
//        testHash();//hash值与计算测试 计算机中存储补码进行运算
//        binaryTransTest();//二进制转换测试
//        testIntegerEquals();//测试int类型的特性 缓存 字面值 做的一些特殊处理
       //testStringIntern();//测试string类型的特性 intern（）方法的特殊之处 hash和equals方法的特殊处理
//       testStringEquals();
//        unicodeTrans();//java中Unicode字符向gbk utf-8的转换
       // ok://采用break ok;的方式则可以直接跳出所有的循环体 直接break的话只是退出j的那层循环
        for(int i=0;i<10;i++){
            for(int j=0;j<i;j++){
                if(j==3){
                    break ;
                }
                System.out.println("i="+i+",j="+j);
            }
        }
    }
/**  * describe:
	 * @author CAI.F
	 * @date:  日期:2017/4/23 时间:18:36
	 * @param
	 */
    private static void linkedListTest() {

Map<String,Object> con=new ConcurrentHashMap<String,Object>();
        con=new HashMap();
    }

    /**  * describe: 简易转换为二进制
	 * @author CAI.F
	 * @date:  日期:2017/4/15 时间:12:29
	 * @param
	 */

    private static void listTest() {
        List cc=new ArrayList();
        List l2=new LinkedList();
        Map m2=new TreeMap();
        Map m03=new LinkedHashMap();
        m2.put(new StringTest(),new StringTest());
        Map<Object,Object> m3=new HashMap();
        Set s=new TreeSet();
        Set s3=new HashSet();
        Map m4=new ConcurrentHashMap();
        Map h=new Hashtable();
        Map h2=new HashMap();
        Map m =  Collections.synchronizedMap(h2);
       List v=new Vector();
        Stack st= new Stack();
        cc.add(1);
        cc.add(2);
        cc.add(3);
        cc.add(4);
        cc.add(5);
        cc.add(6);
        cc.add(7);
        cc.add(8);
        cc.add(9);
        cc.add(10);
        cc.add(11);

    }

    private static void binaryTransTest() {
        String bin = Integer.toBinaryString(-880716637); //二进制 toBinaryString(int i)
//     1100 1011 1000 0001 0101 0100 1010 0011
//     1100 1011 1000 0001 0101 0100 1010 0011
        //八进制 toOctalString(int i)

        //十六进制 toHexString(int i)

        System.out.println(bin);

       // int parseInt = Integer.parseInt(bin,2);
int parseInt=Integer.parseInt("1110",2);
        System.out.println(parseInt);

    }

    /**  * describe: 测试hashcode
	 * @author CAI.F
	 * @date:  日期:2017/4/15 时间:12:09
	 * @param
	 */
    private static void testHash() {
        FileUploadUtil.generateRandomPath("QQ截图20170402121401");
    }

    /**  * describe: utf-8和gbk转换
	 * @author CAI.F
	 * @date:  日期:2017/4/15 时间:10:56
	 * @param
	 */
    private static void unicodeTrans() {
        String str="你是谁啊";
//        StringBuffer sbf=new   StringBuffer();
        StringBuilder utf8Str=new StringBuilder();
        StringBuilder gbkStr=new StringBuilder();
        try {
            byte[] utfByte=str.getBytes("utf-8");
            byte[] gbkByte=str.getBytes("gbk");
            for (byte b:gbkByte){
                gbkStr.append(Integer.toHexString(b&0xFF));//转化为16进制的字符串
            }
            for (byte b : utfByte) {
                utf8Str.append(Integer.toHexString(b & 0xFF));//得到16进制的字符
            }
            System.out.println("你是谁啊 utf-8编码："+utf8Str.toString());
            System.out.println("你是谁啊 gbk编码："+gbkStr.toString());
       System.out.println("UTF-8字符串e4bda0e698afe8b081e5958a转换成中文值======" + new String(utfByte, "utf-8"));//-------手机银行
       System.out.println("gbk字符串"+gbkStr.toString()+"转换成中文值======" + new String(gbkByte, "gbk"));//-------手机银行


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**  * describe: 测试int之间相互加减的时候对象引用的变化
 *  /* 在 Java 5 中，为 Integer 的操作引入了一个新的特性，
 用来节省内存和提高性能。整型对象在内部实现中通过使用相同的对象引用实现了缓存和重用。
 上面的规则适用于整数区间 -128 到 +127。
 这种 Integer 缓存策略仅在自动装箱（autoboxing）的时候有用，
 使用构造器创建的 Integer 对象不能被缓存。譬如h=new Integer(1);
 Java 编译器把原始类型自动转换为封装类的过程称为自动装箱（autoboxing），
 这相当于调用 valueOf 方法
 Javadoc 详细的说明这个类是用来实现缓存支持，并支持 -128 到 127 之间的自动装箱过程。
 最大值 127 可以通过 JVM 的启动参数 -XX:AutoBoxCacheMax=size 修改。
 这种缓存行为不仅适用于Integer对象。我们针对所有整数类型的类都有类似的缓存机制。
 有 ByteCache 用于缓存 Byte 对象
 有 ShortCache 用于缓存 Short 对象
 有 LongCache 用于缓存 Long 对象
 有 CharacterCache 用于缓存 Character 对象
 Byte，Short，Long 有固定范围: -128 到 127。对于 Character,
 范围是 0 到 127。除了 Integer 可以通过参数改变范围外，其它的都不行。
	 * @author CAI.F
	 * @date:  日期:2017/3/13 时间:22:28
	 * @param
	 */
    private static void testIntegerEquals() {
        int a=1;
        Integer b=1;//在JDK5.0之前是不允许直接将基本数据类型的数据直接赋值给其对应地包装类的，如：Integer i = 5;
//        但是在JDK5.0中支持这种写法，因为编译器会自动将上面的代码转换成如下代码：Integer i=Integer.valueOf(5);
        // 此方法在IntegerCache.high默认为127情况下 自动装箱池 -128~127
        //Integer i=Integer.valueOf(5);
        int c=1+b;
        int d=2;
        int e=1+1;
        int f=a+b;
        Integer g=2;
        Integer h=2;

        int m0=130;
        Integer m3=127;
        Integer m4=127;

        Integer m1=130;
        Integer m2=130;
        int m5=h+m0;
        int m6=131;
        int m7=131;

        System.out.println(a==b);//a是常量池中的 不超过默认的
        System.out.println(d==e);//true
        System.out.println(d==f);//true
        System.out.println(e==f);//true
        System.out.println(e==g);//true
        System.out.println(a==h);//true  Integer和int作比较的话会自动进行拆箱操作 1.5以上
        System.out.println(b==h);//false 两个对象比较 引用完全不同
        System.out.println(m1==m2);//false 超出缓存边界 会进行new Integer()
        System.out.println(m3==m4);//true 缓存边界之内 指向同一地址
        System.out.println(m0==m2);//true Integer和int作比较的话会自动进行拆箱操作 1.5以上
        System.out.println(m5==m6);//true Integer和int作比较的话会自动进行拆箱操作 1.5以上
        System.out.println(m7==m6);//true Integer和int作比较的话会自动进行拆箱操作 1.5以上


        //System.out.println(b.equals(a));//true 比较的就只是值而已




    }

    /**  * describe: 着重测试String的intern()方法
 * 该方法会返回长常量池中代表这个字符串的String对象的引用，
 * 如果不存在该字符串则会先将该字符串添加到常量池然后返回该对象的引用
	 * @author CAI.F
	 * @date:  日期:2017/3/13 时间:22:03
	 * @param
	 */
    private static void testStringIntern() {
        String s4=new StringBuilder("a").append("bc").toString();
        //new String("abc");
        // 如果代码中没有同时出现String s1="abc"(无论是在前还是在后都会以这个为先初始化 ？？指令重排序)
        // 或者前面没有new过"abc"字符串的话 一个对象是字面量指向的对象 另外一个则是new 出来的那个对象
        // 一次会创造两个对象 否则就只是创造一个对象
        String s1="abc";
//        System.out.println(s4.intern()==s1);// true 因为都代表常量池中该字符串对象的引用
        System.out.println(s4.intern()==s4);
        //true  ** 注意如果是跟String s1="abc"一起出现的话接过会变为false
        // 因为intern()返回的是常量池的引用 s4是新new出的堆中对象的引用 不是首次出现的
    }


    public static void testStringEquals(){
        String s4=new StringBuilder("a").append("bc").toString();//new String("abc");
        int hs=s4.hashCode();
        String s1="abc";
        String s2="a";
        String s3="bc";
        String s5=s2+s3;
        String s6="a"+s3;
        String s7="a"+"bc";
        Object s8="abc";
        //对于String类型来讲 只要==为true则equals必然为true
        // 反则不一定 盖因为String重写了hashCode方法
        // 判定条件==为true则直接返回 否则则比较字符串字符
        //值得注意的是String只要是相同的字符序列则hashCode必然相同
        // 也是被重写了方法的原因  对于Integer等基础数据类型来讲可能就不一样了 String是特殊的
        System.out.println(s1.hashCode());//96354全部相同
        System.out.println(s4.hashCode());
        System.out.println(s5.hashCode());
        System.out.println(s6.hashCode());
        System.out.println(s7.hashCode());
        System.out.println(s8.hashCode());

        System.out.println(s1.intern());//abc
        System.out.println(s4.intern());//abc
        System.out.println(s5.intern());
        System.out.println(s6.intern());
        System.out.println(s7.intern());
        System.out.println(s8.toString());

        System.out.println(Integer.toHexString(s1.hashCode()));
        System.out.println(Integer.toHexString(s4.hashCode()));
        System.out.println(Integer.toHexString(s5.hashCode()));
        System.out.println(Integer.toHexString(s6.hashCode()));
        System.out.println(Integer.toHexString(s8.hashCode()));

        System.out.println("intern()test:+++++");
        System.out.println(s4.intern()==s4);//false  原理同上

        System.out.println(s1.intern()==s1);//true  首次出现的会将该字符串常量池的引用返回 jdk1.7之后
        // 常量池实例由原先的方法区转移到了堆内存中 1.8之后则直接就废除了方法区
        System.out.println(s5.intern()==s5);//false
        System.out.println("intern()test:+++++");
        System.out.println(s1==s4);//false 一个在常量池（虽然1.7以后常量池移出了永久带 也在堆上）
        // 但是属于堆中的特殊地带  s4代表另外一个非常量池的堆中对象虽然字符序列一样但是地址并不一样
        System.out.println(s1==s5);//false 两个字符串对象相加相当于重新new 一个
        System.out.println(s1==s6);//false 同上
        System.out.println(s1==s7);//true
        System.out.println(s1==s8);//true 还是常量池的数据 运行时常量池
        System.out.println(s1.equals(s4));//true
        // String类型的equals和hashCode方法都被重写了 只要字符串序列相同则必然equals比较为true
        System.out.println(s1.equals(s5));//true
        System.out.println(s1.equals(s6));//true
        System.out.println(s1.equals(s7));//true



    }
}
