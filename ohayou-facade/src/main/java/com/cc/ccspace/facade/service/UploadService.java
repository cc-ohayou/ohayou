package com.cc.ccspace.facade.service;

import java.util.List;

/** 上传服务业务类
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 22:46.
 */
public interface UploadService {
/**  * describe: 上传图片
	 * @author CAI.F
	 * @date:  日期:2017/8/6 时间:22:48
	 * @param
	 */
     String uploadPic(String params);
    /**  * describe: 批量上传图片
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:49
    	 * @param
    	 */
    String uploadPicturesSSI(List list);
    /**  * describe: 上传文件 doc、txt、xmls等等
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:49
    	 * @param
    	 */
    String uploadFiles();
    /**  * describe: 上传视频 mp3等等这类较大的文件 采用上传列表形式 异步线程
    	 * @author CAI.F
    	 * @date:  日期:2017/8/6 时间:22:50
    	 * @param
    	 */
    String uploadVideo();
}
