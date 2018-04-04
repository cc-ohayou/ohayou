package com.cc.ccspace.facade.domain.common.util.logutil;

/**
 * 关键参数日志
 *
 * @AUTHOR CF
 * @DATE Created on 2017/3/13 15:30.
 */
public class InitParamLog extends PivotalParamLog {
    private String module;//代表哪个模块的   譬如任务模块  初始化模块 业务模块
    private String message;//记录简单的信息

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @description 覆写抽象父类的方法  其实这个就是钩子方法
     * 用于在模板设计模式中实现子类的一些特殊化需求
     * 此处是进行各子类属性值的拼装
     * @author CF create on 2017/7/27 9:42
     */
    @Override
    protected String toKVMiddle() {
        return AppUtils.buildKVStr(this.getClass(), this);
    }

}
