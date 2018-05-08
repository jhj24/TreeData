package com.jhj.datalibrary.single

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.IBaseTree

/**
 * 树型数据基础adapter
 * Created by jhj on 17-9-6.
 */
abstract class BaseSingleListAdapter<T : IBaseTree<T>> : RecyclerView.Adapter<BaseSingleListAdapter<T>.ItemViewHolder>() {

    var dataList: MutableList<T> = mutableListOf()
    var selectedItem: T? = null

    override fun getItemCount(): Int = dataList.size


    override final fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
            onCreateItemHolder(parent, viewType)

    override final fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.tag = dataList[position]
        onBindItemHolder(holder, dataList, position)

    }

    abstract fun onBindItemHolder(holder: ItemViewHolder, dataList: MutableList<T>, position: Int)
    abstract fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as T
                val location = dataList.indexOf(bean)
                bean.isChecked = !bean.isChecked
                notifyItemRangeChanged(location, 1)
                if (bean.isChecked) {
                    selectedItem = bean
                    dataList.forEach {
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
    }
}