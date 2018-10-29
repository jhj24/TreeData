package com.jhj.treedata.multi.simple

import com.jhj.datalibrary.holder.ViewInjector
import com.jhj.datalibrary.tree.multi.SlimMultiListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.bean.PersonalTreeBean

/**
 * Created by jhj on 17-9-20.
 */
class PersonMultiListAdapter : SlimMultiListAdapter<PersonalTreeBean>() {

    override fun onBindItemViewHolder(injector: ViewInjector, data: PersonalTreeBean, position: Int) {
        injector.text(R.id.tv_name, data.name)
                .clickable(R.id.checkbox, false)
                .image(R.id.checkbox, if (data.isChecked) R.drawable.icon_choice else R.drawable.icon_choice_no)
                .clicked {
                    itemViewOnClick(data)
                }
    }

    override fun onCreateLayoutRes(viewType: Int): Int {
        return R.layout.layout_multi_tree_node
    }

}