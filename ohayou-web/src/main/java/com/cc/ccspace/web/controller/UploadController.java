package com.cc.ccspace.web.controller;


import com.cc.ccspace.facade.domain.bizobject.common.Result;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;


/**
 * 文件上传
 *
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:43.
 */
@Controller
@RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
public class UploadController extends BaseController {
    private static Logger logger= LoggerFactory.getLogger(UploadController.class);
    @Resource
    private UploadService uploadService;

    /**
     * describe: SSI组件上传图片
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/8/6 时间:22:55
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFilesSSI")
    public String uploadFilesSSI(HttpServletRequest request, HttpServletResponse response) {
        String result="";
        try {
            List<FileItem> list = getListFilesFromReq(request);
            result = uploadService.uploadPicturesSSI(list);
        } catch (FileUploadException e) {
            logger.error(e.getMessage(),e);
            return json(new Result());
        }
        return result;
    }

    private List<FileItem> getListFilesFromReq(HttpServletRequest request) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
        String tempPath = String.valueOf(PropertyHolder.getContextProperty("uploadPath"));
        /**tempPath += "/" + ymd + "/";  **/
        //创建临时文件夹
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }
        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items = upload.parseRequest(request);
        return items;
    }

}
