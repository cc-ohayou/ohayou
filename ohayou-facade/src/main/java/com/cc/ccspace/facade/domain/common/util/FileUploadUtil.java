package com.cc.ccspace.facade.domain.common.util;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;
import java.util.Random;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/11 15:13.
 */
public class FileUploadUtil {
    private static Logger logger= LoggerFactory.getLogger(FileUploadUtil.class);
    // 获得随机目录
    public static String generateRandomPath(String fileName) {
        int hashcode = fileName.hashCode();
        int d1 = hashcode & 0xf;
        int d2 = (hashcode >> 4) & 0xf;
        return File.separator + d1 + File.separator + d2;
    }

    public static Map<String, Object> saveFile(MultipartFile file, Map<String,Object> fileMess,String basePath) throws IOException {
        String type = file.getContentType();
        String savePath = FileUploadUtil.generateRandomPath(file.getOriginalFilename());
        String filePath = basePath+ File.separator + type + savePath
                + File.separator + file.getOriginalFilename();
        File f = new File(filePath);
        /*
        File f = new File(filename); //不会马上创建文件
        FileOutputStream fos = new FileOutputStream(filename); //马上就创建文件了
        */
        boolean makFlag = false;
        if (!f.exists() && !f.isDirectory()) {
            makFlag = f.mkdirs();
        } else {
            //已经存在了同名的文件 名字更改或者直接就不上传 此处采用了上传 但也不会成功
            file.transferTo(f);
        }
        if (makFlag) {
            file.transferTo(f);
        }
        fileMess.put("name", file.getOriginalFilename());
        fileMess.put("url", filePath);
        return fileMess;
    }

    public static void writeFileToPath(String savePath, FileItem item) throws IOException {
        String fileName = item.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String newFileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + "." + fileExt;
        File uploadedFile = new File(savePath, newFileName);
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        BufferedInputStream is = new BufferedInputStream(item.getInputStream());
        byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度
        int length;
        while ((length = is.read(buf)) > 0) {
            os.write(buf, 0, length);
        }
        //关闭流
        os.flush();
        os.close();
        is.close();
        logger.info("upload success：savePath=" + savePath +File.separator + newFileName);
    }
}
