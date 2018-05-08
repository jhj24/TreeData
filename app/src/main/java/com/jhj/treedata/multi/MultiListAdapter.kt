package com.jhj.treedata.multi

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jhj.datalibrary.multi.BaseMultiListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-20.
 */
class MultiListAdapter : BaseMultiListAdapter<Bean>() {
    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ItemViewHolder(inflater.inflate(R.layout.layout_multi_tree_node, parent, false))
    }

    override fun onBindItemHolder(holder: ItemViewHolder?, dataList: ArrayList<Bean>, position: Int) {
        val bean = dataList[position]
        holder?.let {
            with(it.itemView) {
                tv_name.text = bean.name
                checkbox.isClickable = false
                if (bean.isChecked) {
                    checkbox.setImageResource(R.drawable.icon_choice)
                } else {
                    checkbox.setImageResource(R.drawable.icon_choice_no)
                }
            }
        }

    }
}