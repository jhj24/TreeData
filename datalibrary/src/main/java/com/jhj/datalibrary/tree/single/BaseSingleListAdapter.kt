package com.jhj.datalibrary.tree.single

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jhj.datalibrary.model.IBaseTree

/**
 * 列表类数据基础adapter
 * Created by jhj on 17-9-6.
 */
abstract class BaseSingleListAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    var dataList: MutableList<T> = mutableListOf()
    var allList: MutableList<T> = mutableListOf()
    var selectedItem: T? = null

    override fun getItemCount(): Int = dataList.size


    override final fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H =
            onCreateItemHolder(parent, viewType)

    override final fun onBindViewHolder(holder: H, position: Int) {
        val data = dataList[position]
        holder.itemView?.tag = data
        onBindItemHolder(holder, data, position)

    }

    abstract fun onBindItemHolder(holder: H, data: T, position: Int)
    abstract fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): H

    /**
     * 对外公开方法，设置itemView点击事件时，执行该方法
     */
    fun itemViewOnClick(bean: T) {
        val position = dataList.indexOf(bean)
        bean.isChecked = !bean.isChecked
        notifyItemRangeChanged(position, 1)
        if (bean.isChecked) {
            selectedItem = bean
            allList.forEach {
                if (it.isChecked && it.id != bean.id && it.name != bean.name) {
                    it.isChecked = false
                    if (dataList.indexOf(it) != -1) {
                        notifyItemRangeChanged(dataList.indexOf(it), 1)
                    }

                }
            }
        } else {
            selectedItem = null
        }
    }
}