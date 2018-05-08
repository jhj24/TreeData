package com.jhj.datalibrary.single

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.IBaseTree
import com.jhj.datalibrary.R
import kotlinx.android.synthetic.main.layout_tree.view.*

/**
 * 通用adapter
 * Created by jhj on 17-9-22.
 */
abstract class SingleTreeAdapter<T : IBaseTree<T>> : BaseSingleTreeAdapter<T, SingleTreeAdapter<T>.ItemViewHolder>() {


    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_tree, parent, false)
        view.list_item.addView(onCreateHolder(parent, viewType))
        return ItemViewHolder(view)
    }


    override fun onBindItemHolder(holder: ItemViewHolder?, dataList: MutableList<T>, position: Int) {
        val paddingLeft = dataList[position].itemLevels * 2 * dp10
        holder?.itemView?.list_item?.setPadding(paddingLeft, 0, 0, 0)
        onBindHolder(holder, dataList, position)
    }

    /**
     * 使用LayoutInflater.from(parent?.context).inflate(R.layout.view,parent,false)返回自定义布局
     */
    abstract fun onCreateHolder(parent: ViewGroup?, viewType: Int): View

    /**
     * 设置显示样式
     */
    abstract fun onBindHolder(holder: ItemViewHolder?, dataList: MutableList<T>, position: Int)


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as T
                itemViewOnClick(data)
            }
        }
    }


}