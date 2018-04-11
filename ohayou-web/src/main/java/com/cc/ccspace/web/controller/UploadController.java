package com.cc.ccspace.web.controller;


import com.cc.ccspace.facade.domain.common.constants.ExceptionCode;
import com.cc.ccspace.facade.domain.common.exception.BusinessException;
import com.cc.ccspace.facade.domain.common.property.PropertyHolder;
import com.cc.ccspace.facade.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 文件上传
 *
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:43.
 */
@Controller
@RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
public class UploadController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Resource
    private UploadService uploadService;

    /**
     * describe: 批量上传文件
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/4/12 时间:11:42
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFiles")
    public Map<String, Object> uploadFiles(@RequestParam("file") CommonsMultipartFile[] files) {
        return uploadService.uploadFiles(files);
    }

    /**
     *  describe: SSI组件上传图片
     *  利用spring的上传组件内进行文件上传
     * @param
     * @author CAI.F
     * @date: 日期:2017/8/6 时间:22:55
     *
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFilesSSI")
    public void uploadFilesSSI(HttpServletRequest request) throws FileUploadException {
        List<FileItem> list = getListFilesFromReq(request);
        uploadService.uploadPicturesSSI(list);
    }

    private List<FileItem> getListFilesFromReq(HttpServletRequest request) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设定使用内存超过限定值时，将产生临时文件并存储于临时目录中。
        factory.setSizeThreshold(20 * 1024 * 1024);
        String tempPath = String.valueOf(PropertyHolder.getContextProperty("uploadPath"));
        //创建临时文件夹 tempPath += "/" + ymd + "/";
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()&&dirTempFile.mkdirs()) {
            //设定存储临时文件的目录。
            factory.setRepository(new File(tempPath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            return upload.parseRequest(request);
        }else{
            logger.warn("#####!!!makeDir failed");
            throw new BusinessException(ExceptionCode.BIZ_ERROR,"上传失败！");
        }
    }

}
