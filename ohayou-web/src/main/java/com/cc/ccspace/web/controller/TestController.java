package com.cc.ccspace.web.controller;

import com.alibaba.fastjson.JSON;
import com.cc.ccspace.facade.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/2/26 16:42.
 */
@Controller
@RequestMapping(value = "/test", produces = "application/json;charset=utf-8")
public class TestController extends BaseController {
    @Resource
    TestService testService;

    @ResponseBody
    @RequestMapping(value = "/test")
    public ModelAndView test01(HttpServletRequest req, HttpServletResponse res) {
        String context = req.getContextPath();
        String str = req.getRequestURI();

       /* try {
            //req.getRequestDispatcher("/file/uploadFile.html").forward(req, res);
            //两种方式都可以讲要显示的页面返回给请求端
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return new ModelAndView("/static/file/uploadFile.html");
//		response.sendRedirect("/scm_system/index.htm");
    }




    /**
     * describe: 根据传入表名 找到对应的实体Bean 返回JSON格式的创表语句
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/5/7 时间:11:57
     */
    @ResponseBody
    @RequestMapping(value = "/bnnDemo")
    public String bnnDemo(HttpServletRequest req, HttpServletResponse response) {
        String beanName = req.getParameter("tableName");
        String tablebSql = testService.createTableStr(beanName);
        Map result = new HashMap();
        result.put("result", tablebSql);
        return JSON.toJSONString(result);
    }

    /**
     * describe: xa事务测试
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/5/30 时间:20:26
     */
    @ResponseBody
    @RequestMapping(value = "/xaTest", method = RequestMethod.POST)
    public String test(HttpServletRequest req) {
        String mode = req.getParameter("mode");
        try {
            testService.test(Integer.parseInt(mode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * describe: ModelAndView测试
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/5/30 时间:20:27
     */
    @ResponseBody
    @RequestMapping(value = "/mvtest")
    public ModelAndView modelViewTest(HttpServletRequest req) {
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName()
                + ":" + req.getServerPort() + req.getContextPath()
                + "/";
        String ss = "/static/common/test.html";
        ModelAndView mav = new ModelAndView(ss);
        mav.addObject("cc", basePath + ss);

        return mav;
    }

    /**
     * describe: mq生产消费测试
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/5/30 时间:22:50
     */
    @ResponseBody
    @RequestMapping(value = "/mqProduce")
    public String mqProduce(HttpServletRequest req) {
        int count = Integer.parseInt(req.getParameter("count"));
        testService.produce(count);
        return "";

    }

    /**
     * describe: mq生产消费测试
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/5/30 时间:22:50
     */
    @ResponseBody
    @RequestMapping(value = "/mqConsume")
    public String mqConsume(HttpServletRequest req) {
        int count = Integer.parseInt(req.getParameter("count"));
        testService.consume(count);
        return "";

    }

    @ResponseBody
    @RequestMapping(value = "/httptest")
    public String testHttp(HttpServletRequest req) {
        String result = testService.httpTest();
        return "";

    }

}
