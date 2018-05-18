package com.jhj.treedata.bean;


import com.jhj.datalibrary.model.BaseTree;

import java.util.List;

/**
 * Created by jhj on 17-9-8.
 */

public class Bean extends BaseTree<Bean> {

    public int id;
    public String name;
    public List<Bean> children;
    public int isStaff;
    public String phone;
    public int PId;
    public int memberId;

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Bean> getChildren() {
        return children;
    }

    @Override
    public boolean isRoot() {
        return isStaff == 0;
    }
}
