package com.jhj.treedata.position

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.IBaseTree
import com.jhj.datalibrary.single.BaseSingleTreeAdapter
import com.jhj.treedata.R
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*

/**
 * Created by jhj on 17-9-22.
 */
class PositionSingleTreeAdapter<T : IBaseTree<T>>(private val cont: Context) : BaseSingleTreeAdapter<T, PositionSingleTreeAdapter<T>.ItemViewHolder>() {


    override val context: Context
        get() = cont

    override val reminder: String
        get() = "没有数据"

    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return if (viewType == 1) {
            ItemViewHolder(inflater.inflate(R.layout.layout_single_tree_root, parent, false))
        } else {
            ItemViewHolder(inflater.inflate(R.layout.layout_single_tree_node, parent, false))
        }
    }

    override fun onBindItemHolder(holder: ItemViewHolder?, dataList: MutableList<T>, position: Int) {
        val bean = dataList[position]
        holder?.itemView?.let {
            it.tv_name.text = bean.name
            if (bean.isRoot) {
                it.checkbox.visibility = android.view.View.GONE
            } else {
                it.checkbox.visibility = android.view.View.VISIBLE
            }
            if (selectedItem?.name == bean.name && selectedItem?.id == bean.id) {
                bean.isChecked = true
            }
            if (bean.isChecked) {
                it.checkbox.setImageResource(R.drawable.icon_choice)
            } else {
                it.checkbox.setImageResource(R.drawable.icon_choice_no)
            }
            val paddingLeft = dp10 + dataList[position].itemLevels * 2 * dp10
            it.list_item.setPadding(paddingLeft, dp10, dp10, dp10)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as T
                itemViewOnClick(data)
            }
        }
    }


}