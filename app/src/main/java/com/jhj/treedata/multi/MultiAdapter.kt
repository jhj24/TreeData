package com.jhj.treedata.multi

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.multi.BaseMultiTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_multi_tree_node.view.*
import java.util.*

/**
 * Created by jhj on 17-9-19.
 */
class MultiAdapter(private val cont: Context) : BaseMultiTreeAdapter<Bean, MultiAdapter.ItemViewHolder>() {

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

    override fun onBindItemHolder(holder: ItemViewHolder, dataList: ArrayList<Bean>, position: Int) {
        val bean = dataList[position]
        with(holder.itemView) {
            tv_name.text = bean.name
            if (selectedItem?.name == bean.name && selectedItem?.id == bean.id) {
                bean.isChecked = true
            }
            if (bean.isChecked) {
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
                val data = itemView.tag as Bean
                itemViewOnClick(data)
            }
            itemView.checkbox.setOnClickListener {
                val data = itemView.tag as Bean
                checkboxOnClick(data)
            }
        }
    }

}