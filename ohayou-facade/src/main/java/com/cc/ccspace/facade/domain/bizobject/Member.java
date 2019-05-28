package com.cc.ccspace.facade.domain.bizobject;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/4/24/024 12:33.
 */
@Data
public class Member implements Serializable {
    private String mid;
    private String name;
    private Integer age;
    private Double salary;

}