package com.jhj.treedata.multi.old

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.multi.BaseMultiTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.StaffTreeBean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*

/**
 * Created by jhj on 17-9-19.
 */
class MultiTreeAdapter(private val cont: Context) : BaseMultiTreeAdapter<StaffTreeBean, MultiTreeAdapter.ItemViewHolder>() {


    override val context: Context
        get() = cont

    override val reminder: String
        get() = "暂无数据"

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            ItemViewHolder(inflater.inflate(R.layout.layout_multi_tree_root, parent, false))
        } else {
            ItemViewHolder(inflater.inflate(R.layout.layout_multi_tree_node, parent, false))
        }
    }

    override fun onBindItemHolder(holder: ItemViewHolder, data: StaffTreeBean, position: Int) {
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
            val paddingLeft = dp10 + dataList[position].itemLevels * 2 * dp10
            list_item.setPadding(paddingLeft, dp10, dp10, dp10)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as StaffTreeBean
                itemViewOnClick(data)
            }
            itemView.checkbox.setOnClickListener {
                val data = itemView.tag as StaffTreeBean
                checkboxOnClick(data)
            }
        }
    }

}