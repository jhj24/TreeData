package com.jhj.treedata.person

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.single.SingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*

/**
 *
 * Created by jhj on 17-9-22.
 */
class PersonSingleTreeAdapter(private val cont: Context) : SingleTreeAdapter<Bean, PersonSingleTreeAdapter.ItemViewHolder>() {

    override val context: Context
        get() = cont
    override val reminder: String
        get() = "就是没数据"


    override fun onCreateItemView(parent: ViewGroup?, viewType: Int): View {
        val inflater = LayoutInflater.from(parent?.context)
        return if (viewType == 1) {
            inflater.inflate(R.layout.layout_single_tree_root, parent, false)
        } else {
            inflater.inflate(R.layout.layout_single_tree_node, parent, false)
        }
    }

    override fun onCreateItemViewHolder(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }


    override fun onBindItemViewHolder(holder: ItemViewHolder?, data: Bean, position: Int) {
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
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as Bean
                itemViewOnClick(bean)
            }
        }

    }
}