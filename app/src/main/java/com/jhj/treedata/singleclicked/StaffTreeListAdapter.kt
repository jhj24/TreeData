package com.jhj.treedata.singleclicked

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.datalibrary.tree.single.BaseSingleListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.StaffTreeBean
import kotlinx.android.synthetic.main.layout_single_tree_node.view.*

/**
 * Created by jhj on 17-9-8.
 */
class StaffTreeListAdapter(val mContext: Context) : BaseSingleListAdapter<StaffTreeBean, StaffTreeListAdapter.ItemViewHolder>() {


    override fun onBindItemHolder(holder: ItemViewHolder, data: StaffTreeBean, position: Int) {
        with(holder.itemView) {
            tv_name.text = data.name
            tv_id.text = data.department
            iv_tree_mark.setImageResource(R.drawable.iv_avatar_circle)
            if (selectedItem?.id == data.id.toString() && selectedItem?.name == data.name) {
                data.isChecked = true
            }
            checkbox.visibility = View.GONE
        }
    }

    override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return ItemViewHolder(inflater.inflate(R.layout.layout_single_tree_node, parent, false))
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val bean = itemView.tag as StaffTreeBean
                itemViewOnClick(bean)
                val intent = Intent(Intent.ACTION_DIAL)
                val url = Uri.parse("tel:" + bean.phone)
                intent.data = url
                mContext.startActivity(intent)
            }


        }

    }
}