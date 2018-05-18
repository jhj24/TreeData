package com.jhj.datalibrary.utils

import com.jhj.datalibrary.model.IBaseTree
import java.util.*

/**
 * 树型数据处理：排序、筛选
 * Created by jhj on 17-9-7.
 */
object TreeDealUtil {

    /**
     * @sortData 要排序的集合
     * @isNodeFirst 同一等级,子节点是否在更节点上方
     */
    fun <T : IBaseTree<T>> sort(data: List<T>) {
        Collections.sort(data) { o1, o2 ->
            o1.firstLetterSpelling.compareTo(o2.firstLetterSpelling)
        }
        Collections.sort(data) { o1, o2 ->
            o2.isRoot.compareTo(o1.isRoot)
        }
    }


    /**
     * 筛选拼音
     */
    fun <T : IBaseTree<T>> isFilterSpelling(filter: String, data: T): Boolean {
        val array = data.arraySpelling
        array.forEach { s ->
            var string = ""
            for (index in array.indexOf(s) until array.size) {
                string += array[index]
            }
            if (string.toUpperCase().startsWith(filter.toUpperCase())) {
                return true
            }
        }
        return false
    }

    fun <T : IBaseTree<T>> isFilter(filter: String, data: T): Boolean {
        return isFilterSpelling(filter, data) || data.name.contains(filter)
    }

}