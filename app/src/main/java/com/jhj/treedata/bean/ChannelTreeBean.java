package com.jhj.treedata.bean;


import com.jhj.datalibrary.model.BaseTree;

import java.util.List;

/**
 * Created by jhj on 17-9-7.
 */

public class ChannelTreeBean extends BaseTree<ChannelTreeBean> {

    private int id;
    private int pid;
    private int org;
    private int sort;
    private List<ChannelTreeBean> baseChannelgroupList;
    private String name;
    private String pids;
    private int levels;

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ChannelTreeBean> getChildren() {
        return baseChannelgroupList;
    }

    @Override
    public boolean isRoot() {
        return !(baseChannelgroupList == null || baseChannelgroupList.size() == 0);
    }

    public List<ChannelTreeBean> getBaseChannelgroupList() {
        return baseChannelgroupList;
    }

    public void setBaseChannelgroupList(List<ChannelTreeBean> baseChannelgroupList) {
        this.baseChannelgroupList = baseChannelgroupList;
    }
}
