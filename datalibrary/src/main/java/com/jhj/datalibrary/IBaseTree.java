package com.jhj.datalibrary;

import java.io.Serializable;
import java.util.List;

/**
 * 数据数据处理接口
 * <p>
 * Created by jhj on 17-9-8.
 */

public interface IBaseTree<T extends IBaseTree<T>> extends Serializable {

    String getId();

    String getName();

    List<T> getChildren();

    boolean isRoot();

    boolean isChecked();

    void setChecked(boolean isChecked);

    int getItemLevels();

    void setItemLevels(int levels);

    boolean isShowChildren();

    void setShowChildren(boolean isShow);

    String getFirstLetterSpelling();

    void setFirstLetterSpelling(String spelling);

    String[] getArraySpelling();

    void setArraySpelling(String[] array);


}
