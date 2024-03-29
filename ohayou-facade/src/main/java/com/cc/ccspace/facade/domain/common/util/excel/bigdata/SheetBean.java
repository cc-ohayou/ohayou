package com.cc.ccspace.facade.domain.common.util.excel.bigdata;


import java.util.List;

public class SheetBean {

    private int index;//sheet 位置
    private boolean readonly=false;//是否可编辑
    private boolean displaygridlines=true;//是否显示表格
    private String name;//sheet名字
    private List<RowBean> rowList;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public boolean isReadonly() {
        return readonly;
    }
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    public boolean isDisplaygridlines() {
        return displaygridlines;
    }
    public void setDisplaygridlines(boolean displaygridlines) {
        this.displaygridlines = displaygridlines;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<RowBean> getRowList() {
        return rowList;
    }
    public void setRowList(List<RowBean> rowList) {
        this.rowList = rowList;
    }


}