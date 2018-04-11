package com.cc.ccspace.test.spring.aop;

/**
 * @AUTHOR CF  监控摄像系统
 * @DATE Created on 2018/3/1 15:14.
 */
public class SecurityCameraSystem {
  private Worker  worker;
    SecurityCameraSystem(){
    }
  public void monitor(){
      System.out.println("监控到人员工作开始");
  }

  public void playBack(){
      System.out.println("回放工作人员行为");
  }
}
