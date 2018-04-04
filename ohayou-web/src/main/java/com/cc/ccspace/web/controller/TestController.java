package com.cc.ccspace.web.controller;

import com.alibaba.fastjson.JSON;
import com.cc.ccspace.facade.domain.common.property.PropertyHolder;
import com.cc.ccspace.facade.domain.common.util.FileUploadUtil;
import com.cc.ccspace.facade.service.TestService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/2/26 16:42.
 */
@Controller
@RequestMapping(value="/test",produces="application/json;charset=utf-8")
public class TestController extends BaseController{
   @Resource
   TestService testService;
    @ResponseBody
    @RequestMapping(value = "/test")
    public ModelAndView test01(HttpServletRequest req, HttpServletResponse res){
       String context= req.getContextPath();
        String str=req.getRequestURI();
        Map resultMap=new HashMap();
          resultMap.put("context",context);
          resultMap.put("url",str);
        ModelAndView mav=new ModelAndView();

       /* try {
            //req.getRequestDispatcher("/file/uploadFile.html").forward(req, res);
            //两种方式都可以讲要显示的页面返回给请求端
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return new ModelAndView("/static/file/uploadFile.html");
//			response.sendRedirect("/scm_system/index.htm");
    }
    @ResponseBody
    public String upload(HttpServletRequest req, MultipartFile file){

        String context= req.getContextPath();
        String str=req.getRequestURI();
        String status="0";
        String desc="success";
        List fileMess=new ArrayList();
        // 判断文件是否为空
        if (file!=null&&!file.isEmpty()) {
            try {
                // 文件保存路径
                Map fileMap = new HashMap();
                ServletContext sc=req.getSession().getServletContext();
                fileMap= FileUploadUtil.saveFile(file,fileMap);
                fileMess.add(fileMap);
            } catch (Exception e) {
                status="1";
                desc="文件创建失败";
            }
        }
        Map resultMap=new HashMap();
        resultMap.put("status",status);
        resultMap.put("msg",desc);
        resultMap.put("fileMess",fileMess);
        // 重定向
        return  JSON.toJSONString(resultMap);


    }

/**  * describe: 批量上传文件
	 * @author CAI.F
	 * @date:  日期:2017/4/12 时间:11:42
	 * @param
	 */
    @ResponseBody
    @RequestMapping(value = "/uploadFiles")
    public String uploadFiles(HttpServletRequest req, @RequestParam("file") CommonsMultipartFile[] files){
       // String context= req.getContextPath();
        // String str=req.getRequestURI();
        Map resultMap = new HashMap();
        String status = "0";
        String msg = "success";
        if(files!=null&&files.length>0) {
            List fileMess = new ArrayList();
            for (int i = 0; i < files.length; i++) {

                if (!files[i].isEmpty()) {
                    try {
                        //ServletContext sc = req.getSession().getServletContext();
                        Map file = new HashMap();
                        file = FileUploadUtil.saveFile(files[i], file);
                        fileMess.add(file);
                    } catch (Exception e) {
                        status = "1";
                        msg = "文件创建失败!";
                    }
                }
            }

            resultMap.put("fileMess", fileMess);
        }
        else {
            status="1";
            msg="传入文件不能为空";
        }
        resultMap.put("status",status);
        resultMap.put("msg",msg);
        // 重定向
        return  JSON.toJSONString(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFilesSSI")
    public String uploadFilesSSI(HttpServletRequest request, HttpServletResponse response){
        uploadByServlet(request,response);
        return "success";
    }

    private void uploadByServlet(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传文件的保存路径
         String configPath = "uploadFile";

         String dirTemp = "uploadFile/temp";

         String dirName = "file";
        //文件保存目录路径
        //String savePath = "E:/";
        String savePath = PropertyHolder.getContextProperty("uploadPath")+ configPath;
        // 临时文件目录
        String tempPath = PropertyHolder.getContextProperty("uploadPath") + dirTemp;

        // 时间
        /**
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
         String ymd = sdf.format(new Date());
         savePath += "/" + ymd + "/";
         **/

        System.out.println("------->"+savePath);
        //创建文件夹
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        /**tempPath += "/" + ymd + "/";  **/
        //创建临时文件夹
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List items = upload.parseRequest(request);
            System.out.println("items = " + items);
            Iterator itr = items.iterator();

            while (itr.hasNext())
            {
                FileItem item = (FileItem) itr.next();
                String fileName = item.getName();
                long fileSize = item.getSize();
                if (!item.isFormField()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    String newFileName = new Date().getTime() + "_" + new Random().nextInt(1000) + "." + fileExt;
                    try{
                        File uploadedFile = new File(savePath, newFileName);

                        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                        BufferedInputStream is = new BufferedInputStream(item.getInputStream());
                        byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度
                        int length = 0;
                        while( (length = is.read(buf)) > 0 ){
                            os.write(buf, 0, length);
                        }
                        //关闭流
                        os.flush();
                        os.close();
                        is.close();
                        System.out.println("上传成功！路径："+savePath+"/"+newFileName);
                        out.print(savePath+"/"+newFileName);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else {
                    String filedName = item.getFieldName();
                    if(filedName.equals("customData")){
                        System.out.println("用户自定义参数===============");
                        System.out.println("FieldName："+filedName);
                        System.out.println(URLDecoder.decode(URLDecoder.decode(item.getString(), "utf-8"), "utf-8"));
                    }else if(filedName.equals("tailor")){
                        System.out.println("裁剪后的参数===============");
                        System.out.println("FieldName："+filedName);
                        System.out.println(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
                        // 获得裁剪部分的坐标和宽高
                        System.out.println("String："+item.getString());
                    }
                }
            }

        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    /**  * describe: 根据传入表名 找到对应的实体Bean 返回JSON格式的创表语句
    	 * @author CAI.F
    	 * @date:  日期:2017/5/7 时间:11:57
    	 * @param
    	 */
    @ResponseBody
    @RequestMapping(value = "/bnnDemo")
     public String bnnDemo(HttpServletRequest req, HttpServletResponse response) {
        String beanName=req.getParameter("tableName");
        String tablebSql=testService.createTableStr(beanName);
        if("".equals(tablebSql)){
            return "create table failed";
        }
        Map result=new HashMap();
        result.put("result",tablebSql);
        return JSON.toJSONString(result);
    }
/**  * describe: xa事务测试
	 * @author CAI.F
	 * @date:  日期:2017/5/30 时间:20:26
	 * @param
	 */
    @ResponseBody
    @RequestMapping(value="/xaTest",method = RequestMethod.POST)
    public String test(HttpServletRequest req) {
        String mode=req.getParameter("mode");
    try {
        testService.test(Integer.parseInt(mode));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
}
/**  * describe: ModelAndView测试
	 * @author CAI.F
	 * @date:  日期:2017/5/30 时间:20:27
	 * @param
	 */
    @ResponseBody
    @RequestMapping(value = "/mvtest")
    public ModelAndView modelViewTest(HttpServletRequest req) {
        String path=req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName()
                + ":" + req.getServerPort() + req.getContextPath()
                + "/";
        String ss="/static/common/test.html";
        ModelAndView mav=new ModelAndView(ss);
        mav.addObject("cc",basePath+ss);

        return mav;
    }
/**  * describe: mq生产消费测试
	 * @author CAI.F
	 * @date:  日期:2017/5/30 时间:22:50
	 * @param
	 */
    @ResponseBody
    @RequestMapping(value = "/mqProduce")
    public   String  mqProduce(HttpServletRequest req) {

        int count=Integer.parseInt(req.getParameter("count"));
        testService.produce(count);

      return "";

    }/**  * describe: mq生产消费测试
	 * @author CAI.F
	 * @date:  日期:2017/5/30 时间:22:50
	 * @param
	 */
    @ResponseBody
    @RequestMapping(value = "/mqConsume")
    public   String  mqConsume(HttpServletRequest req) {
      int count=Integer.parseInt(req.getParameter("count"));
      testService.consume(count);
      return "";

    }

    @ResponseBody
    @RequestMapping(value = "/httptest")
    public   String  testHttp(HttpServletRequest req) {
        String result=testService.httpTest();
        return "";

    }

}
