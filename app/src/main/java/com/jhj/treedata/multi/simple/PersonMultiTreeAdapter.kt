package com.jhj.treedata.multi.simple

import android.content.Context
import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.tree.multi.SlimMultiTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean

/**
 * 简化后
 * Created by jhj on 17-9-19.
 */
class PersonMultiTreeAdapter(private val cont: Context) : SlimMultiTreeAdapter<PersonalTreeBean>() {


    override val context: Context
        get() = cont

    override val reminder: String
        get() = "暂无数据"

    override fun onCreateLayoutRes(viewType: Int): Int {
        return if (viewType == 1) {
            R.layout.layout_multi_tree_root
        } else {
            R.layout.layout_multi_tree_node
        }

    }

    override fun onBindItemViewHolder(injector: ViewInjector, data: PersonalTreeBean, position: Int) {
        injector.text(R.id.tv_name, data.name)
                .clickable(R.id.checkbox, false)
                .image(R.id.checkbox, if (data.isChecked) R.drawable.icon_choice else R.drawable.icon_choice_no)
                .clicked {
                    itemViewOnClick(data)
                }
                .clicked(R.id.checkbox) {
                    checkboxOnClick(data)
                }
        if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
            data.isChecked = true
        }
    }

}