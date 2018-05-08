package com.jhj.treedata.person

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.single.SingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*

/**
 *
 * Created by jhj on 17-9-22.
 */
class PersonSingleTreeAdapter(private val cont: Context) : SingleTreeAdapter<Bean>() {


    override val context: Context
        get() = cont
    override val reminder: String
        get() = "就是没数据"

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): View {
        val inflater = LayoutInflater.from(parent?.context)
        return if (viewType == 1) {
            inflater.inflate(R.layout.layout_single_tree_root, parent, false)
        } else {
            inflater.inflate(R.layout.layout_single_tree_node, parent, false)
        }
    }

    override fun onBindHolder(holder: ItemViewHolder?, dataList: MutableList<Bean>, position: Int) {
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
        }
    }
}