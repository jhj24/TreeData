package com.jhj.datalibrary.multi

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.IBaseTree

/**
 * Created by jhj on 17-9-20.
 */
abstract class BaseMultiListAdapter<T : IBaseTree<T>> : RecyclerView.Adapter<BaseMultiListAdapter<T>.ItemViewHolder>() {
    var dataList: ArrayList<T> = arrayListOf()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder?.itemView?.tag = dataList[position]
        onBindItemHolder(holder, dataList, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return onCreateItemHolder(parent, viewType)
    }

    abstract fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder
    abstract fun onBindItemHolder(holder: ItemViewHolder?, dataList: ArrayList<T>, position: Int)


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as T
                val position = dataList.indexOf(data)
                if (position != -1) {
                    data.isChecked = !data.isChecked
                    notifyItemRangeChanged(position, 1)
                }
            }
        }


    }
}