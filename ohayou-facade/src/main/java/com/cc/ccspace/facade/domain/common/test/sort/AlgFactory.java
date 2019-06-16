package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/29 11:45.
 */
public abstract  class AlgFactory {
    /**
     * 获取算法实例
     * @param c
     * @param <T>
     * @return
     */
    public abstract   <T extends AlgorithmSuper> T  createAlgorithm(Class<T>  c);


}
