package com.jhj.treedata.position

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jhj.datalibrary.IBaseTree
import com.jhj.datalibrary.single.BaseSingleListAdapter
import com.jhj.treedata.R
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-8.
 */
class CommonTreeListAdapter<T : IBaseTree<T>> : BaseSingleListAdapter<T>() {


    override fun onBindItemHolder(holder: ItemViewHolder, dataList: MutableList<T>, position: Int) {
        val bean = dataList[position]
        with(holder.itemView) {
            tv_name.text = bean.name
            if (selectedItem?.id == bean.id.toString() && selectedItem?.name == bean.name) {
                bean.isChecked = true
            }
            if (bean.isChecked) {
                checkbox.setImageResource(R.drawable.icon_choice)
            } else {
                checkbox.setImageResource(R.drawable.icon_choice_no)
            }
        }
    }

    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ItemViewHolder(inflater.inflate(R.layout.layout_single_tree_node, parent, false))
    }
}