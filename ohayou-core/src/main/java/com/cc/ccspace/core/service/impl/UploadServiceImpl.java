package com.cc.ccspace.core.service.impl;


import com.cc.ccspace.core.manager.RedisManager;
import com.cc.ccspace.core.manager.UploadManager;
import com.cc.ccspace.facade.domain.common.constants.ExceptionCode;
import com.cc.ccspace.facade.domain.common.exception.BusinessException;
import com.cc.ccspace.facade.domain.common.property.PropertyHolder;
import com.cc.ccspace.facade.domain.common.util.FileUploadUtil;
import com.cc.ccspace.facade.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:46.
 */
@Service
public class UploadServiceImpl extends CcBaseServiceImpl implements UploadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisManager redisManager;
    @Resource
    UploadManager uploadManager;
    @Override
    public String uploadPic(String params) {

        return null;
    }

    //  批量上传图片
    @Override
    public void uploadPicturesSSI(List<FileItem> items) {
        try {
            uploadManager.doUpload(items,PropertyHolder.getStringProperty("uploadPath"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("upload faied encoding error", e);
            throw new BusinessException(ExceptionCode.BIZ_ERROR,"上传失败");
        } catch (IOException e) {
            logger.warn("upload faied io error", e);
            throw new BusinessException(ExceptionCode.BIZ_ERROR,"上传失败");
        }
    }



    @Override
    public Map<String, Object> uploadFiles(CommonsMultipartFile[] files) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Map<String, Object>> fileMess = new ArrayList<>();
        String basePath= String.valueOf(PropertyHolder.getContextProperty("filePath"));
        if (files != null && files.length > 0) {
            for (CommonsMultipartFile file1 : files) {
                if (!file1.isEmpty()) {
                    try {
                        Map<String, Object> file = new HashMap<>();
                        file = FileUploadUtil.saveFile(file1, file,basePath);
                        fileMess.add(file);
                    } catch (Exception e) {
                        logger.warn(" !!uploadFiles failed,fileName="+file1.getOriginalFilename(), e);
                    }
                }
            }
        }
        resultMap.put("fileMess", fileMess);
        return resultMap;
    }

    @Override
    public String uploadVideo() {
        return null;
    }
}
