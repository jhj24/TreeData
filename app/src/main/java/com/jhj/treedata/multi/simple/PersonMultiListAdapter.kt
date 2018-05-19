package com.jhj.treedata.multi.simple

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.multi.SimpleMultiListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-20.
 */
class PersonMultiListAdapter : SimpleMultiListAdapter<PersonalTreeBean, PersonMultiListAdapter.ItemViewHolder>() {
    override fun onCreateItemView(parent: ViewGroup?, viewType: Int): View {
        return LayoutInflater.from(parent?.context).inflate(R.layout.layout_multi_tree_node, parent, false)
    }

    override fun onCreateItemViewHolder(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder, data: PersonalTreeBean, position: Int) {
        with(holder.itemView) {
            tv_name.text = data.name
            checkbox.isClickable = false
            if (data.isChecked) {
                checkbox.setImageResource(R.drawable.icon_choice)
            } else {
                checkbox.setImageResource(R.drawable.icon_choice_no)
            }
        }
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as PersonalTreeBean
                itemViewOnClick(bean)
            }
        }
    }
}