package com.cc.ccspace.facade.domain.common.util.upyun;


import com.cc.ccspace.facade.domain.common.util.security.SecurityUtil;

import java.io.File;

public class UpLoadUtil {
    //域名 示例 https://cc.b0.upaiyun.com
    public static final String VIS_PREFIX = "";
    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "";
    private static final String OPERATOR_NAME = "";
    private static final String OPERATOR_PWD = "";
    
    private static UpYun upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
    
      private static String isSuccess(boolean result) {
        return result ? " 成功" : " 失败";
      }
      
      public static String upload(String path,File localFile) throws Exception {
          upyun.setDebug(true);
          upyun.setContentMD5(UpYun.md5(localFile));

          String filePath = path+ SecurityUtil.MD5(localFile.getName()) + localFile.getName().substring(localFile.getName().lastIndexOf("."));
          boolean ret = upyun.writeFile(filePath, localFile, true);
          
          System.out.println("上传:" + localFile.getName() + "===>" + filePath + isSuccess(ret));
          return VIS_PREFIX + filePath;
      }
      
}
