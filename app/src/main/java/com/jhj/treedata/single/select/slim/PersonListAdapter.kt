package com.jhj.treedata.single.select.slim

import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.tree.single.SlimSingleListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean

/**
 * Created by jhj on 17-9-8.
 */
class PersonListAdapter : SlimSingleListAdapter<PersonalTreeBean>() {

    override fun onCreateLayoutRes(viewType: Int): Int {
        return R.layout.layout_single_tree_node
    }


    override fun onBindItemViewHolder(injector: ViewInjector, data: PersonalTreeBean, position: Int) {

        injector.text(R.id.tv_name, data.name)
                .image(R.id.checkbox, if (data.isChecked) R.drawable.icon_choice else R.drawable.icon_choice_no)
                .clicked {
                    itemViewOnClick(data)
                }
        if (selectedItem?.getId() == data.id.toString() && selectedItem?.name == data.name) {
            data.isChecked = true
        }
    }

}