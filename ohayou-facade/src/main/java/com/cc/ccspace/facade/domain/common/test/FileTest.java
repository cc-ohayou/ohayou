package com.cc.ccspace.facade.domain.common.test;

import java.io.*;
import java.net.URLDecoder;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/3/16 18:04.
 */
public class FileTest {

    public static void main(String[] args) {
String path="D:\\ProgramFiles\\apache-tomcat-7.0.57idea\\apache-tomcat-7.0.57\\" +
        "logs\\throwbill\\init\\initLog-log.log";
        String code=getFileEncode(path);
        System.out.println(code);
        String filestr=  readFile(path);
        System.out.println(filestr);
        System.out.println(URLDecoder.decode(filestr));
        try {
            writeFile(URLDecoder.decode(filestr).getBytes("UTF-8"),"D:\\logs\\testlog.log","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //  String   testStr="季后赛的交付时间对方";
        //  String ss= URLEncoder.encode(testStr);
        //   String  dstr  = URLDecoder.decode(ss);
        //   System.out.println(dstr);
    }

    public static String getFileEncode(String path) {
        String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)
                            //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                //TextLogger.getLogger().info(loc + " " + Integer.toHexString(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charset;
    }

    private static String getEncode(int flag1, int flag2, int flag3) {
        String encode="";
        // txt文件的开头会多出几个字节，分别是FF、FE（Unicode）,
        // FE、FF（Unicode big endian）,EF、BB、BF（UTF-8）
        if (flag1 == 255 && flag2 == 254) {
            encode="Unicode";
        }
        else if (flag1 == 254 && flag2 == 255) {
            encode="UTF-16";
        }
        else if (flag1 == 239 && flag2 == 187 && flag3 == 191) {
            encode="UTF8";
        }
        else {
            encode="asci";// ASCII码
        }
        return encode;
    }
    /**
     * 通过路径获取文件的内容，这个方法因为用到了字符串作为载体，为了正确读取文件（不乱码），只能读取文本文件，安全方法！
     */
    public static String readFile(String path){
        String data = null;
        // 判断文件是否存在
        File file = new File(path);
        if(!file.exists()){
            return data;
        }
        // 获取文件编码格式
        String code = getFileEncode(path);
        InputStreamReader isr = null;
        try{
            // 根据编码格式解析文件
            if("asci".equals(code)){
                // 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码
                // code = System.getProperty("file.encoding");
                code = "GBK";
            }
            isr = new InputStreamReader(new FileInputStream(file),code);
            // 读取文件内容
            int length = -1 ;
            char[] buffer = new char[1024];
            StringBuffer sb = new StringBuffer();
            while((length = isr.read(buffer, 0, 1024) ) != -1){
                sb.append(buffer,0,length);
        }
            data = new String(sb);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 按照指定的路径和编码格式保存文件内容，这个方法因为用到了字符串作为载体，为了正确写入文件（不乱码）
     * ，只能写入文本内容，安全方法
     *
     * @param data
     *          将要写入到文件中的字节数据
     * @param path
     *          文件路径,包含文件名
     * @return boolean
     * 			当写入完毕时返回true;
     */
    public static boolean writeFile(byte data[], String path , String code){
        boolean flag = true;
        OutputStreamWriter osw = null;
        try{
            File file = new File(path);
            if(!file.exists()){
                file = new File(file.getParent());
                if(!file.exists()){
                    file.mkdirs();
                }
            }
            if("asci".equals(code)){
                code = "GBK";
            }
            osw = new OutputStreamWriter(new FileOutputStream(path),code);
            osw.write(new String(data,code));
            osw.flush();
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }finally{
            try{
                if(osw != null){
                    osw.close();
                }
            }catch(IOException e){
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
    /**
     * 从指定路径读取文件到字节数组中,对于一些非文本格式的内容可以选用这个方法
     * 			457364578634785634534
     * @param path
     *          文件路径,包含文件名
     * @return byte[]
     * 			 文件字节数组
     *
     */
    public static byte[] getFile(String path) throws IOException {
        FileInputStream stream=new FileInputStream(path);
        int size=stream.available();
        byte data[]=new byte[size];
        stream.read(data);
        stream.close();
        stream=null;
        return data;
    }



    /**
     * 把字节内容写入到对应的文件，对于一些非文本的文件可以采用这个方法。
     * @param data
     *            将要写入到文件中的字节数据
     * @param path
     *            文件路径,包含文件名
     * @return boolean isOK 当写入完毕时返回true;
     * @throws Exception
     */
    public static boolean toFile(byte data[], String path) throws Exception {
        FileOutputStream out=new FileOutputStream(path);
        out.write(data);
        out.flush();
        out.close();
        out=null;
        return true;
    }


}
