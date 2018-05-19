package com.jhj.treedata.multi.simple

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.multi.SimpleMultiTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * 简化后
 * Created by jhj on 17-9-19.
 */
class PersonMultiTreeAdapter(private val cont: Context) : SimpleMultiTreeAdapter<PersonalTreeBean, PersonMultiTreeAdapter.ItemViewHolder>() {

    override val context: Context
        get() = cont

    override val reminder: String
        get() = "暂无数据"


    override fun onCreateItemView(parent: ViewGroup?, viewType: Int): View {
        val inflater = LayoutInflater.from(parent?.context)
        return if (viewType == 1) {
            inflater.inflate(R.layout.layout_multi_tree_root, parent, false)
        } else {
            inflater.inflate(R.layout.layout_multi_tree_node, parent, false)
        }
    }

    override fun onCreateItemViewHolder(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder, data: PersonalTreeBean, position: Int) {
        with(holder.itemView) {
            tv_name.text = data.name
            if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
                data.isChecked = true
            }
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
                val data = itemView.tag as PersonalTreeBean
                itemViewOnClick(data)
            }
            itemView.checkbox.setOnClickListener {
                val data = itemView.tag as PersonalTreeBean
                checkboxOnClick(data)
            }
        }
    }

}