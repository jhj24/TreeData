package com.jhj.treedata.multi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.multi.BaseMultiListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-20.
 */
class MultiListAdapter : BaseMultiListAdapter<Bean, MultiListAdapter.ItemViewHolder>() {
    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ItemViewHolder(inflater.inflate(R.layout.layout_multi_tree_node, parent, false))
    }

    override fun onBindItemHolder(holder: ItemViewHolder?, data: Bean, position: Int) {
        holder?.let {
            with(it.itemView) {
                tv_name.text = data.name
                checkbox.isClickable = false
                if (data.isChecked) {
                    checkbox.setImageResource(R.drawable.icon_choice)
                } else {
                    checkbox.setImageResource(R.drawable.icon_choice_no)
                }
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}