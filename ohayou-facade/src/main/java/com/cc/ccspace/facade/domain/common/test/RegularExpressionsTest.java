package com.cc.ccspace.facade.domain.common.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 16:47.
 */
public class RegularExpressionsTest {
    public static void main(String[] args) {
       /* System.out.println("abc".matches("..."));//字符匹配 一个点一个英文字母
        String str="a8729a";
//        替换所有的数字
        System.out.println(str.replaceAll("\\d","-"));
        Pattern p= Pattern.compile("[a-z]{3}");//预编译正则规则 这样真正去匹配字符串时会快很多，
        // 尤其是遇到大批量匹配的时候
       Matcher m= p.matcher("fgh");//匹配的结果会存储在匹配器中
        System.out.println(m.matches());*/
       //*0或多个,  ?0或一,+至少一个  {n} {n,m}n到m次  {n,}至少n次
       out("a".matches("."));
       out("aa".matches("aa"));
       out("aaaa".matches("a*"));
       out("aaaa".matches("a+"));
       out("".matches("a*"));
       out("".matches("a?"));
       out("a".matches("a?"));
       out("aaaa".matches("a?"));

        //范围
        out("a".matches("[abc]"));
        out("a".matches("[^abc]"));//取除了abc外的其它字符
        out("a".matches("[a-zA-Z]"));//字母大小写匹配
        out("A".matches("[a-z[A-Z]]"));//字母大小写匹配
        out("R".matches("[A-Z&&[RFG]]"));//字母大写并在RFG

        //特殊字符  \s空白字符 空格 table 换行 backspace  \t空格
        //  \d[0-9] \D[^0-9] \S 非空白字符 \w [a-zA-Z_0-9]数字或字母 下横线  \W  [^\w]
             out("\\".matches("\\\\"));//匹配一个反斜线 要用四个斜线 字符串表示出来的话 每个反斜线要配一个反斜线
           //^在中括号里面代表取反  外面代表以什么开头
        out("hello sir".matches("^h.*"));
        out("hello sir".matches("^h[a-z]{1,3}o\\b.*"));// \b代表单词边界
        out("hello sir".matches("^h[a-z]{1,3}o\\b.*"));
        out("hellosir".matches("^h[a-z]{1,3}o\\b.*"));
        //whitlines
        out(" \n".matches("^[\\s&&[^\\n]]*\\n$")); //$以什么结尾  非换行符的空白符开头 换行结尾

        Pattern p=Pattern.compile("\\d{3,5}");
        String s="123-34345-234-00-01-887";
        Matcher m=p.matcher(s);
        out(m.matches());//false 要求是全部匹配 匹配到123- 到第四个字符 发现不匹配 后面find不会从头 会继续从这里开始找寻的
        m.reset();//恢复 matches造成的前四位消失
        out(m.find());//找到一个符合的 就会从字符串中去掉
        out(m.find());
        out(m.find());
        out(m.find());

        out(m.lookingAt());//每次都从头上开始找
        out(m.lookingAt());
        out(m.lookingAt());
        out(m.lookingAt());

        //分组
        Pattern p1=Pattern.compile("(\\d{3,5})([a-z]{2})");
        String s1="123aa-34345a-234cc-00c-01c-887s-888";
        Matcher m1=p1.matcher(s1);
        while(m1.find()){
            out("所有匹配的："+m1.group());
            out("匹配数字的"+m1.group(1));
            out("匹配字母："+m1.group(2));
            out(m1.end());//不符合规则的下标索引
//            out(m1.group(3));//有几组小括号 就有几组
        }

        out(m1.groupCount());//几种规则分组
        //email匹配 从一个文件中一行行额读取
        Pattern p0 = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
        Matcher m0 = p.matcher(s);
        while(m0.find()) {
            System.out.println(m0.group());
        }

//   ".*\\.java$" .java 结尾 .代表任意 \\. 转义就表示.
        // "^[\\s&&[^\\n]]*\\n$"  空行
        //startsWith("/*")&&!endsWith("*/")注释行

    }

    public static void out(Object o){
        System.out.println(o);
    }

}
