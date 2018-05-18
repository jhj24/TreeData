package com.jhj.datalibrary.tree.single

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.model.IBaseTree
import com.jhj.datalibrary.R
import kotlinx.android.synthetic.main.layout_tree_item.view.*

/**
 * 通用adapter
 * Created by jhj on 17-9-22.
 */
abstract class SingleTreeAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : BaseSingleTreeAdapter<T, H>() {


    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): H {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_tree_item, parent, false)
        view.list_item.addView(onCreateItemView(parent, viewType))
        return onCreateItemViewHolder(view)

    }


    override fun onBindItemHolder(holder: H?, data: T, position: Int) {
        val paddingLeft = data.itemLevels * 2 * dp10
        holder?.itemView?.list_item?.setPadding(paddingLeft, 0, 0, 0)
        onBindItemViewHolder(holder, data, position)
    }

    /**
     * 返回布局样式
     */
    abstract fun onCreateItemView(parent: ViewGroup?, viewType: Int): View

    /**
     * 返回ItemViewHolder
     */
    abstract fun onCreateItemViewHolder(view: View): H

    /**
     * 设置显示样式
     */
    abstract fun onBindItemViewHolder(holder: H?, data: T, position: Int)

}