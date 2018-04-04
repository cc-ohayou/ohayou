package com.cc.ccspace.facade.domain.common.util;

import com.cc.ccspace.facade.domain.common.property.PropertyHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/11 15:13.
 */
public class FileUploadUtil {
    // 获得随机目录
    public static String generateRandomPath(String fileName) {
        int hashcode = fileName.hashCode();
        int d1 = hashcode & 0xf;
        int d2 = (hashcode >> 4) & 0xf;
        return File.separator+d1 + File.separator + d2;
    }
    public static Map saveFile(MultipartFile file,Map fileMess){
        String type=file.getContentType();
        String savePath= FileUploadUtil.generateRandomPath(file.getOriginalFilename());
        //String name=file.getName();
        String truely_path= (String) PropertyHolder.getContextProperty("filePath");
        String filePath =truely_path+File.separator+type+savePath
                +File.separator+ file.getOriginalFilename();
        File f=new File(filePath);
        /* File f = new File(filename); //不会马上创建文件
           FileOutputStream fos = new FileOutputStream(filename); //马上就创建文件了
           */
        boolean makFlag=false;
        try {
        if(!f.exists()&&!f.isDirectory()){
            makFlag = f.mkdirs();
        }
        else{//已经存在了同名的文件 名字更改或者直接就不上传 此处采用了上传 但也不会成功
                file.transferTo(f);
            }
            if(makFlag) {
                file.transferTo(f);
            }
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        fileMess.put("name",file.getOriginalFilename());
        fileMess.put("url",filePath);
        return fileMess;
    }
}
