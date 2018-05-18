package com.jhj.datalibrary.tree.multi

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jhj.datalibrary.model.IBaseTree
import java.util.*

/**
 *  列表类多选Adapter
 * Created by jhj on 17-9-20.
 */
abstract class BaseMultiListAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {
    var dataList: ArrayList<T> = arrayListOf()


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        val data = dataList[position]
        holder.itemView?.tag = data
        onBindItemHolder(holder, data, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        return onCreateItemHolder(parent, viewType)
    }

    abstract fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): H
    abstract fun onBindItemHolder(holder: H?, data: T, position: Int)

    /**
     * 对外公开方法，设置itemView点击时间
     */
    fun itemViewOnClick(bean: T) {
        val position = dataList.indexOf(bean)
        if (position != -1) {
            bean.isChecked = !bean.isChecked
            notifyItemRangeChanged(position, 1)
        }
    }
}