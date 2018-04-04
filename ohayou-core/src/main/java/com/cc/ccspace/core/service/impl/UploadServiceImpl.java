package com.cc.ccspace.core.service.impl;


import com.cc.ccspace.core.manager.RedisManager;
import com.cc.ccspace.facade.domain.bizobject.common.Result;
import com.cc.ccspace.facade.domain.common.property.PropertyHolder;
import com.cc.ccspace.facade.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:46.
 */
@Service
public class UploadServiceImpl extends CcBaseServiceImpl implements UploadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisManager redisManager;
    @Override
    public String uploadPic(String params) {

        return null;
    }

    //  批量上传图片
    @Override
    public String uploadPicturesSSI(List items) {
        try {
            doUpload(items);
        } catch (UnsupportedEncodingException e) {
            logger.error("upload faied encoding error", e);
            return json(new Result("上传失败"));
        } catch (IOException e) {
            logger.error("upload faied io error", e);
            return json(new Result("上传失败"));
        }
        return json(new Result(000));
    }

    private void doUpload(List items) throws IOException {
        String savePath = PropertyHolder.getStringProperty("uploadPath");
        //创建文件夹
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        Iterator itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            String fileName = item.getName();
//            long fileSize = item.getSize();
            if (!item.isFormField()) {
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
                logger.info("上传成功！路径：" + savePath + "/" + newFileName);
            } else {
                String filedName = item.getFieldName();
                if (filedName.equals("customData")) {
                    logger.info("用户自定义参数===============");
                    logger.info("FieldName：" + filedName);
                    logger.info(URLDecoder.decode(URLDecoder.decode(item.getString(), "utf-8"), "utf-8"));
                } else if (filedName.equals("tailor")) {
                    logger.info("裁剪后的参数===============");
                    logger.info("FieldName：" + filedName);
                    logger.info(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
                    // 获得裁剪部分的坐标和宽高
                    logger.info("String：" + item.getString());
                }
            }
        }
    }

    @Override
    public String uploadFiles() {
        return null;
    }

    @Override
    public String uploadVideo() {
        return null;
    }
}
