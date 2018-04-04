package com.cc.ccspace.web.interceptor;

import com.cc.ccspace.core.common.aop.ControllerLogAspect;
import com.cc.ccspace.core.common.util.BeanUtil;
import com.cc.ccspace.facade.domain.common.exception.*;
import com.cc.ccspace.web.holder.HeaderInfo;
import com.cc.ccspace.web.holder.HeaderInfoHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ExceptionInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Map headerInfoMap=new HashMap();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headerInfoMap.put(key, value);
        }
        HeaderInfo info=new HeaderInfo();
        try {
            info= BeanUtil.mapToObject(headerInfoMap,HeaderInfo.class);
            HeaderInfoHolder.setHeaderInfo(info);
        } catch (Exception e) {
            log.error("Header map transfer to bean error!",e);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ApiResponse apiResponse=ApiResponse.error();
        if (ex != null) {
            // 记录输入输出日志, 该部分日志跳出了切面日志捕获 - add by Shangdu Lin - 20171013
            ControllerLogAspect aspect=new ControllerLogAspect();
            log.error(ex.getMessage());
            if(ex instanceof BusinessException){
                BusinessException exception=(BusinessException) ex;
                ErrorEnum errorEnum=exception.getErrorEnum();
                if (errorEnum != null) {
                    apiResponse.setCode(errorEnum.getCode()).setMsg(errorEnum.getMsg());
                } else {
                    apiResponse.setCode(Integer.valueOf(exception.getErrorCode())).setMsg(exception.getErrorMessage());
                }
                if(exception.getData()!=null){
                    apiResponse.setData(exception.getData());
                }
                // 记录输入输出日志, 该部分日志跳出了切面日志捕获 - add by Shangdu Lin - 20171013
                aspect.logProxy(request,apiResponse.getMsg(),null);
            }
            else if(ex instanceof SystemException){
                String errorCode = ((SystemException) ex).getErrorCode();
                String errorMsg = ((SystemException) ex).getErrorMessage();
                apiResponse.setCode(Integer.valueOf(errorCode)).setMsg(errorMsg);

                // 记录输入输出日志, 该部分日志跳出了切面日志捕获 - add by Shangdu Lin - 20171013
                aspect.logProxy(request,apiResponse.getMsg(),null);
            } else if (ex instanceof ParamException) {
                String errorCode = ((ParamException) ex).getErrorCode();
                String errorMsg = ((ParamException) ex).getErrorMessage();
                apiResponse.setCode(Integer.valueOf(errorCode)).setMsg(errorMsg);

                // 记录输入输出日志, 该部分日志跳出了切面日志捕获 - add by Shangdu Lin - 20171013
                aspect.logProxy(request,apiResponse.getMsg(),null);
            }
            else{
                log.error(ex.getMessage(), ex);
                ErrorEnum errorEnum=ErrorEnum.SYSTEM_ERROR;
                apiResponse.setCode(errorEnum.getCode()).setMsg(errorEnum.getMsg());

                // 记录输入输出日志, 该部分日志跳出了切面日志捕获 - add by Shangdu Lin - 20171013
                aspect.logProxy(request,apiResponse.getMsg(),ex);
            }
            ObjectMapper mapper=new ObjectMapper();
            String json=mapper.writeValueAsString(apiResponse);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter out =response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        }
    }
}