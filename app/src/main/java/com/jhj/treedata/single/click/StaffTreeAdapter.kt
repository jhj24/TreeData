package com.jhj.treedata.single.click

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.tree.single.SlimSingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.StaffTreeBean

/**
 * （简化后的Adapter）,点击事件
 * Created by jhj on 18-5-9.
 */
class StaffTreeAdapter(val mContext: Context) : SlimSingleTreeAdapter<StaffTreeBean>() {


    override val context: Context
        get() = mContext
    override val reminder: String
        get() = "没有员工"

    override fun onCreateLayoutRes(viewType: Int): Int {
        return if (viewType == 1) {
            R.layout.layout_staff_tree_root
        } else {
            R.layout.layout_staff_tree_node
        }
    }

    override fun onBindItemViewHolder(injector: ViewInjector, data: StaffTreeBean, position: Int) {
        injector.text(R.id.tv_name, data.name)
                .text(R.id.tv_id, data.department)
                .gone(R.id.checkbox)
                .with<ImageView>(R.id.iv_tree_mark) {
                    if (data.isRoot) {
                        if (data.isShowChildren) {
                            it.setImageResource(R.drawable.tree_on)
                        } else {
                            it.setImageResource(R.drawable.tree_off)
                        }
                    } else {
                        it.setImageResource(R.drawable.iv_avatar_circle)
                    }
                }
                .clicked {
                    itemViewOnClick(data)
                    if (!data.isRoot) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        val url = Uri.parse("tel:" + data.phone)
                        intent.data = url
                        mContext.startActivity(intent)
                    }
                }
    }

    override fun setDivideLineAttribute(line_tree_divide: View?) {
        super.setDivideLineAttribute(line_tree_divide)
        line_tree_divide?.setBackgroundColor(Color.RED)
        line_tree_divide?.layoutParams?.height = 2


    }

}

