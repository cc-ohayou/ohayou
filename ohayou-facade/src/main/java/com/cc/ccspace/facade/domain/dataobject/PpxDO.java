package com.cc.ccspace.facade.domain.dataobject;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ppx_test")
public class PpxDO implements Serializable {
    @Id
    @Column(name = "ppx_id")
    private Integer ppxId;

    private String name;

    private Integer age;

    /**
     * 1代表男性 2代表女性
     */
    private Boolean sex;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "time_ymd")
    private Date timeYmd;

    private static final long serialVersionUID = 1L;

    /**
     * @return ppx_id
     */
    public Integer getPpxId() {
        return ppxId;
    }

    /**
     * @param ppxId
     */
    public void setPpxId(Integer ppxId) {
        this.ppxId = ppxId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取1代表男性 2代表女性
     *
     * @return sex - 1代表男性 2代表女性
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置1代表男性 2代表女性
     *
     * @param sex 1代表男性 2代表女性
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return time_ymd
     */
    public Date getTimeYmd() {
        return timeYmd;
    }

    /**
     * @param timeYmd
     */
    public void setTimeYmd(Date timeYmd) {
        this.timeYmd = timeYmd;
    }
}