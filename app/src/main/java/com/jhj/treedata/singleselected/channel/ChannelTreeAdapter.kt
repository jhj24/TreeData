package com.jhj.treedata.singleselected.channel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.model.IBaseTree
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.R
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*

/**
 * Created by jhj on 17-9-22.
 */
class ChannelTreeAdapter<T : IBaseTree<T>>(private val cont: Context) : BaseSingleTreeAdapter<T, ChannelTreeAdapter.ItemViewHolder>() {
    override fun onBindItemHolder(holder: ItemViewHolder?, data: T, position: Int) {
        holder?.itemView?.let {
            it.tv_name.text = data.name
            if (data.isRoot) {
                it.checkbox.visibility = android.view.View.GONE
            } else {
                it.checkbox.visibility = android.view.View.VISIBLE
            }
            if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
                data.isChecked = true
            }
            if (data.isChecked) {
                it.checkbox.setImageResource(R.drawable.icon_choice)
            } else {
                it.checkbox.setImageResource(R.drawable.icon_choice_no)
            }
            val paddingLeft = dp10 + dataList[position].itemLevels * 2 * dp10
            it.list_item.setPadding(paddingLeft, dp10, dp10, dp10)
        }
    }


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


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as? T
                data?.let {
                    itemViewOnClick(it)
                }

            }
        }
    }


}