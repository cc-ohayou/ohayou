package com.cc.ccspace.facade.domain.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huzhiyong on 2017/9/20.
 */
public class RegUtil {

    public static boolean isPhoneValid(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(0|86|17951)?1[0-9]{10}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 密码校验:6-16位,必须包含数字和字母
     *
     * @param password
     * @return boolean
     * @author zzg
     */
    public static boolean passwordCheck(String password) {
        boolean flag = false;
        //是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;
        //判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;
        //判断密码长度是否在6-16位
        int k = password.length();
        //不能含有特殊字符
        String regEx = "[ _`~!@#$%^&()+=|{}':;/',\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if (!(i + j < 2 || k < 6 || k > 16 || m.find() == true)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 交易密码校验：必须是6位纯数字
     *
     * @return boolean
     * @author zzg
     */
    public static boolean transPasswordCheck(String transPassword) {
        boolean flag = false;
        //是否为纯数字并且为6位
        if (transPassword.matches("[0-9]{6}") && transPassword.length() == 6) {
            flag = true;
        }
        return flag;
    }

    /**
     * 昵称校验：七位以内，不能有特殊字符
     *
     * @return boolean
     * @author zzg
     */
    public static boolean nickNameCheck(String nickName) {
        boolean flag = false;
        //是否含特殊字符 ，并且小于7位
        String regEx = "[ _`~!@#$%^&()+=|{}':;/',\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(nickName);
        if (m.find() == false && nickName.length() <= 7) {
            flag = true;
        }
        return flag;
    }

    //测试主方法
    public static void main(String[] args) {
        System.out.print(nickNameCheck("你好啊*/"));
    }
}
