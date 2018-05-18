package com.jhj.treedata.singleselected

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.model.IBaseTree
import com.jhj.datalibrary.tree.single.BaseSingleListAdapter
import com.jhj.treedata.R
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-8.
 */
class CommonListAdapter<T : IBaseTree<T>> : BaseSingleListAdapter<T, CommonListAdapter<T>.ItemViewHolder>() {


    override fun onBindItemHolder(holder: ItemViewHolder, data: T, position: Int) {
        with(holder.itemView) {
            tv_name.text = data.name
            if (selectedItem?.id == data.id.toString() && selectedItem?.name == data.name) {
                data.isChecked = true
            }
            if (data.isChecked) {
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

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as T
                itemViewOnClick(bean)
            }
        }
    }
}