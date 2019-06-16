package com.cc.ccspace.facade.domain.common.test.sort;


import lombok.extern.slf4j.Slf4j;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/29 11:58.
 */
@Slf4j
public class SortAlgFactory   extends AlgFactory{

    @Override
    public  <T extends AlgorithmSuper> T createAlgorithm(Class<T> c) {

        AlgorithmSuper alg=null;
        try {
              alg= (AlgorithmSuper) Class.forName(c.getName()).newInstance();
             }catch(Exception e){
                       log.warn(" SortAlgFactory class create exception",e);
             }
        return (T)alg;
    }
}
