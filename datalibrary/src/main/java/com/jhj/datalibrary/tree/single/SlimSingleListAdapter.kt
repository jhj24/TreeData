package com.jhj.datalibrary.tree.single

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.R
import com.jhj.datalibrary.holder.SlimViewHolder
import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.model.IBaseTree
import kotlinx.android.synthetic.main.layout_tree_item.view.*

/**
 * 简化后的TreeAdapter
 * Created by jhj on 17-9-6.
 */
abstract class SlimSingleListAdapter<T : IBaseTree<T>> : BaseSingleListAdapter<T, SlimViewHolder>() {


    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): SlimViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_tree_item, parent, false)
        setDivideLineAttribute(view.line_tree_divide)
        val childView = inflater.inflate(onCreateLayoutRes(viewType), view.layout_tree_item, false)
        view.layout_tree_item.addView(childView)
        return SlimViewHolder(view)
    }

    override fun onBindItemHolder(holder: SlimViewHolder, data: T, position: Int) {
        onBindItemViewHolder(holder.viewInjector, data, position)
    }


    /**
     * 根据不同的type，返回不同的布局样式
     */
    abstract fun onCreateLayoutRes(viewType: Int): Int

    /**
     * 设置显示样式
     */
    abstract fun onBindItemViewHolder(injector: ViewInjector, data: T, position: Int)


    open fun setDivideLineAttribute(line_tree_divide: View?){}

}
