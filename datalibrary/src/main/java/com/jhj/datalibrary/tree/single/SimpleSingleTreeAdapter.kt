package com.jhj.datalibrary.tree.single

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.R
import com.jhj.datalibrary.model.IBaseTree
import kotlinx.android.synthetic.main.layout_tree_item.view.*

/**
 * 简化后的TreeAdapter
 * Created by jhj on 17-9-22.
 */
abstract class SimpleSingleTreeAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : BaseSingleTreeAdapter<T, H>() {


    var extraPaddingLeft: Int? = null

    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): H {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.layout_tree_item, parent, false)
        setDivideLineAttribute(view.line_divide)
        view.list_item.addView(onCreateItemView(parent, viewType))
        return onCreateItemViewHolder(view)

    }

    override fun onBindItemHolder(holder: H, data: T, position: Int) {
        val padding = extraPaddingLeft ?: (2 * dp10)
        val paddingLeft = data.itemLevels * padding
        holder.itemView?.list_item?.setPadding(paddingLeft, 0, 0, 0)
        onBindItemViewHolder(holder, data, position)
    }

    /**
     * 根据不同的type，返回不同的布局样式
     */
    abstract fun onCreateItemView(parent: ViewGroup?, viewType: Int): View

    /**
     * 返回ItemViewHolder
     */
    abstract fun onCreateItemViewHolder(view: View): H

    /**
     * 设置显示样式
     */
    abstract fun onBindItemViewHolder(holder: H, data: T, position: Int)

    /**
     * 设置分割线属性样式
     */
    open fun setDivideLineAttribute(line_divide: View) {}

}