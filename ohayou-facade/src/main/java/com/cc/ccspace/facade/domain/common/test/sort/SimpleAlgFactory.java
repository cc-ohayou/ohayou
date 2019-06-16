package com.cc.ccspace.facade.domain.common.test.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/29 14:03.
 */
@Slf4j
public class SimpleAlgFactory {
  /**  * describe:
	 * @author CAI.F
	 * @date:  日期:2019/5/29 时间:14:04
	 * @param
	 */
    public static <T extends AlgorithmSuper> T createAlgorithm(Class<T> c) {

        AlgorithmSuper alg=null;
        try {
            alg= (AlgorithmSuper) Class.forName(c.getName()).newInstance();
        }catch(Exception e){
            log.warn(" SimpleAlgFactory class create exception",e);
        }
        return (T)alg;
    }
}
