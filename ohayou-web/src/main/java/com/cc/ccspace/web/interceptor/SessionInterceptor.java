package com.cc.ccspace.web.interceptor;


import com.alibaba.fastjson.JSON;
import com.cc.ccspace.core.manager.RedisManager;
import com.cc.ccspace.facade.domain.bizobject.common.Result;
import com.cc.ccspace.facade.domain.common.constants.RedisConstants;
import com.cc.ccspace.facade.domain.common.exception.ErrorEnum;
import com.cc.ccspace.facade.domain.common.util.HandlerAnnotationUtil;
import com.cc.ccspace.web.common.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 只会拦截spring mvc注解的Controller
 *
 * @AUTHOR CF
 * @DATE Created on 2017/6/15 14:19.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Resource
    RedisManager redisManager;

    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    // 会话 过期时间  ms  5天 每次打开app自动更新会话有效时间
    private final long expired = 5 * 24 * 60 * 60 * 1000;
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if(ipUaUrlLimitTrue((HandlerMethod) handler,request,response)){
            return false;
        }
        if (HandlerAnnotationUtil.annotationJudgePassed((HandlerMethod) handler)) {
            return true;
        }
//        request.setCharacterEncoding("utf-8");
      /*  String requestUri = request.getRequestURI();
        String lastPath = requestUri.substring(requestUri.lastIndexOf("/"));
        logger.info(String.format("----收到请求:%s,请求方式:%s", requestUri, request.getMethod()));
        String noneAuth = initNoneAuthPath();
        if (!noneAuth.contains(lastPath)) {
            if (StringUtils.isEmpty(request.getHeader(HeaderKeys.SID))) {
                returnErrorMessage(response, ErrorEnum.AUTH_FAILED.getCode(), ErrorEnum.AUTH_FAILED.getMsg());
                return false;
            }
            boolean sessionValid = checkSessionId(request);
            if (!sessionValid) {
                returnErrorMessage(response, ErrorEnum.AUTH_FAILED.getCode(), ErrorEnum.AUTH_FAILED.getMsg());
                return false;
            }
        } else if (APP_START_ONCE_VISIT_PATH.equals(lastPath)) {
            //app启动后根据会话是否过期更新会话更新时间
            updateSessionExpireTime(request);
        }*/
        return true;
    }


    private boolean ipUaUrlLimitTrue(HandlerMethod method, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!StringUtils.isEmpty(redisManager.get(RedisConstants.IP_SWITCH))
                && (HandlerAnnotationUtil.rateLimitInterceptOn(method) || specialUrlListContainsReqUrl(request))
                && ipUaInfoReachLimit(request)){
            returnErrorMessage(response, ErrorEnum.VISIT_TOO_MUCH.getCode(), ErrorEnum.VISIT_TOO_MUCH.getMsg());
            return true;
        }
        return  false;
    }

    private boolean specialUrlListContainsReqUrl(HttpServletRequest request) {
        String urls = redisManager.get(RedisConstants.IP_UA_LIMIT_URLS);
        return !StringUtils.isEmpty(urls) && urls.contains("," + request.getRequestURI());
    }

    /**
     * @description  统一ip和ua信息的限制是否达到 使用redis进行特定url的频率拦截
     * @author CF create on 2018/3/23 14:31
     */
    private boolean ipUaInfoReachLimit(HttpServletRequest request) {
        String realIp= HttpUtil.getIpAddress(request);
        String uaInfo = request.getHeader("user-agent");
        String url = request.getRequestURI();
        return redisManager.ipUaInfoReachLimit(uaInfo,realIp,url);
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        logger.info("@####################afterCompletion test");
    }

    /**
     * @param
     * @return
     * @description 返回错误信息
     * @author CF create on 2017/7/12 16:05
     */
    private void returnErrorMessage(HttpServletResponse response, int code, String errorMessage) throws IOException {
        Result rst = new Result(code, null, errorMessage);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String res = JSON.toJSONString(rst);
        out.print(res);
        out.flush();
        out.close();
    }

}
