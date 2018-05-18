package com.jhj.treedata.bean;


import com.jhj.datalibrary.model.BaseTree;

import java.util.List;

/**
 * Created by jhj on 17-9-8.
 */

public class PersonalTreeBean extends BaseTree<PersonalTreeBean> {

    public int id;
    public String name;
    public List<PersonalTreeBean> children;
    public int isStaff;

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PersonalTreeBean> getChildren() {
        return children;
    }

    @Override
    public boolean isRoot() {
        return isStaff == 0;
    }
}
