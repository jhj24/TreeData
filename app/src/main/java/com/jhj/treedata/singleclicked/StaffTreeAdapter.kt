package com.jhj.treedata.singleclicked

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.StaffTreeBean
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*
import java.util.*

/**
 * Created by jhj on 18-5-9.
 */
class StaffTreeAdapter(val mContext: Context) : BaseSingleTreeAdapter<StaffTreeBean, StaffTreeAdapter.ItemViewHolder>() {


    var type = ""

    override val context: Context
        get() = mContext
    override val reminder: String
        get() = "没有员工"

    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = if (viewType == 1) {
            inflater.inflate(R.layout.layout_single_tree_root, parent, false)
        } else {
            inflater.inflate(R.layout.layout_single_tree_node, parent, false)
        }
        return ItemViewHolder(view)
    }

    override fun onBindItemHolder(holder: ItemViewHolder?, data: StaffTreeBean, position: Int) {
        val paddingLeft = dataList[position].itemLevels * 2 * dp10
        holder?.itemView?.list_item?.setPadding(paddingLeft, 0, 0, 0)
        holder?.itemView?.let {
            it.tv_name.text = data.name
            it.tv_id.text = data.department
            if (data.isRoot) {
                it.checkbox.visibility = android.view.View.GONE
            } else {
                it.checkbox.visibility = android.view.View.VISIBLE
                //ImageUtil.setCircleImage(data.icon, it.iv_tree_mark)
            }
            if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
                data.isChecked = true
            }
            it.checkbox.visibility = View.GONE
            if (data.isRoot) {
                if (data.isShowChildren) {
                    it.iv_tree_mark.setImageResource(R.drawable.tree_on)
                } else {
                    it.iv_tree_mark.setImageResource(R.drawable.tree_off)
                }
            }
        }
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as StaffTreeBean
                itemViewOnClick(bean)
                if (!bean.isRoot) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    val url = Uri.parse("tel:" + bean.phone)
                    intent.data = url
                    mContext.startActivity(intent)
                }
            }
        }
    }
}

