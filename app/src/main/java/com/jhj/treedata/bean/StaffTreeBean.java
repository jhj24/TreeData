package com.jhj.treedata.bean;

import com.jhj.datalibrary.model.BaseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhj on 18-5-18.
 */

public class StaffTreeBean extends BaseTree<StaffTreeBean> {
    private String id;
    private String icon;
    private String department;
    private String content;
    private boolean root;
    private String phone;
    private List<StaffTreeBean> childList;

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return content;
    }

    @Override
    public List<StaffTreeBean> getChildren() {
        return childList;
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<StaffTreeBean> getChildList() {
        if (childList == null) {
            return new ArrayList<StaffTreeBean>();
        } else {
            return childList;
        }

    }

    public void setChildList(List<StaffTreeBean> childList) {
        this.childList = childList;
    }

    public void addChildList(List<StaffTreeBean> list) {
        if (this.childList == null) {
            this.childList = new ArrayList<StaffTreeBean>();
        }
        this.childList.addAll(list);
    }

}
