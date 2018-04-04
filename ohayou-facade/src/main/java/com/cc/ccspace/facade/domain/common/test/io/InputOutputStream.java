package com.cc.ccspace.facade.domain.common.test.io;


import com.cc.ccspace.facade.domain.common.test.FileTest;

import java.io.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 10:17.
 */
public class InputOutputStream {
    public static void main(String[] args) throws Exception {

       // fileInputStreamTest();
//        bufferStreamTest();
        String path="E:\\ideaworkspace\\CCSpace\\printtest04.txt";
//        bufferReaderTest(path);
//        dataStreamTest(path);
        printTest(path);
    }
/**  * describe: 更改系统输出的目的地 打印出unicode字符
	 * @author CAI.F
	 * @date:  日期:2017/4/18 时间:14:31
	 * @param
	 */
    private static void printTest(String path) throws Exception {
        PrintStream ps=null;
        FileOutputStream fos=new FileOutputStream(path);
        ps=new PrintStream(fos);
        if(ps!=null){
            System.setOut(ps);
        }
        int ln=0;
        for(char c=0;c<=65535;c++){
            System.out.print(c+"");
            if(ln>=100){
                System.out.println();
                ln=0;
            }
            ln++;
        }

    }

    /**  * describe: DataInputStream
	 * @author CAI.F
	 * @date:  日期:2017/4/18 时间:11:58
	 * @param
	 */
    private static void dataStreamTest(String path) throws IOException {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();//默认32字节长度的字节数组
        FileOutputStream fos=new FileOutputStream(path);
        DataOutputStream dos=new DataOutputStream(fos);
        dos.writeDouble(Math.random());
        dos.writeBoolean(true);
        FileInputStream fis=new FileInputStream(path);
        BufferedInputStream bis=new BufferedInputStream(fis);
        byte [] b=new byte[1024];
        DataInputStream dis=null;
//        注意这个要先写先读，先进先出 队列
        while(bis.read(b)!=-1){
            System.out.println(bis.read(b));

        }
        ByteArrayInputStream bais=new ByteArrayInputStream(b);//默认32字节长度的字节数组

        dis=new DataInputStream(bais);
        System.out.println(dis.readDouble());
        System.out.println(dis.readBoolean());

//        ByteArrayInputStream bais=new ByteArrayInputStream(b);
//        System.out.println(bais.available());
        dos.flush();
        dos.close();
        fos.flush();
        fos.close();
        fis.close();
        bis.close();
        dis.close();
    }

    private static void bufferReaderTest(String path) throws Exception {
           String str=null;
        FileReader fr=new FileReader(path);
        BufferedReader br=new BufferedReader(fr);
        FileWriter fw=new FileWriter("E:\\ideaworkspace\\CCSpace\\test03.txt",true);
        BufferedWriter bw=new BufferedWriter(fw);
        while((str=br.readLine())!=null){//一行一行的读取写入
            System.out.println(str);
            bw.write(str);
          bw.newLine();//不加换行的话还是会一行写到底
        }
        bw.flush();
        bw.close();
        br.close();


    }

    private static void bufferStreamTest() {
        int b=0;
        byte[] by=new byte[1024];
        FileInputStream in=null;
        BufferedInputStream bis=null;
        FileOutputStream fos=null;
        BufferedOutputStream  bos=null;
        try {
            in=new FileInputStream("E:\\ideaworkspace\\CCSpace\\test01.txt");
            bis=new BufferedInputStream(in);//默认8192大小的字节数组
            fos=new FileOutputStream("E:\\ideaworkspace\\CCSpace\\test02.txt",true);
            bos=new BufferedOutputStream(fos);
            String encode= FileTest.getFileEncode("C:\\Users\\Administrator\\Desktop\\1.txt");
            System.out.println(encode);
            String ss="hello bitch!";
            while((b=bis.read(by))!=-1){
                System.out.println(b);
                System.out.println(new String(by,"utf-8"));

                bos.write(by);

            }
            bos.write(ss.getBytes());
            bos.write("\n".getBytes());//写入换行符
            bos.flush();
            bos.close();
            bis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileInputStreamTest() {

            int b=0;
        byte[] by=new byte[1024];
        FileInputStream in=null;
        FileOutputStream fos=null;
        try {
            in=new FileInputStream("E:\\ideaworkspace\\CCSpace\\test01.txt");
           fos=new FileOutputStream("E:\\ideaworkspace\\CCSpace\\test02.txt");
            String encode= FileTest.getFileEncode("C:\\Users\\Administrator\\Desktop\\1.txt");
            System.out.println(encode);
            String ss="hello bitch!";
            while((b=in.read(by))!=-1){
                System.out.println(b);
                System.out.println(new String(by,"utf-8"));
                fos.write(by);

            }
            fos.write(ss.getBytes());
            fos.flush();
            fos.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
