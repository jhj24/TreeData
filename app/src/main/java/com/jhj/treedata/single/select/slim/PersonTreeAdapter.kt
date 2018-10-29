package com.jhj.treedata.single.select.slim

import android.content.Context
import android.view.View
import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.tree.single.SlimSingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean

/**
 *
 * Created by jhj on 17-9-22.
 */
class PersonTreeAdapter(private val cont: Context) : SlimSingleTreeAdapter<PersonalTreeBean>() {


    override val context: Context
        get() = cont
    override val reminder: String
        get() = "就是没数据"

    override val extraPaddingLeft: Int
        get() = 45

    override fun onCreateLayoutRes(viewType: Int): Int {
        return if (viewType == 1) {
            R.layout.layout_person_tree_root
        } else {
            R.layout.layout_person_tree_node
        }
    }


    override fun onBindItemViewHolder(injector: ViewInjector, data: PersonalTreeBean, position: Int) {

        injector.text(R.id.tv_name, data.name)
                .clickable(R.id.checkbox, false)
                .image(R.id.checkbox, if (data.isChecked) R.drawable.icon_choice else R.drawable.icon_choice_no)
                .clicked {
                    itemViewOnClick(data)
                }
                .visibility(R.id.checkbox, if (data.isRoot) View.GONE else View.VISIBLE)
        if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
            data.isChecked = true
        }

    }

}