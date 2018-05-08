package com.jhj.treedata.person

import android.support.v7.widget.RecyclerView
import com.jhj.datalibrary.single.BaseSingleListAdapter
import com.jhj.datalibrary.single.BaseSingleTreeActivity
import com.jhj.datalibrary.single.BaseSingleTreeAdapter
import com.jhj.treedata.DataUtil
import com.jhj.treedata.bean.Bean
import com.jhj.treedata.position.CommonTreeListAdapter
import java.util.*

/**
 * Created by jhj on 17-9-6.
 */
class PersonSingleTreeActivity : BaseSingleTreeActivity<Bean>() {

    private lateinit var beanList: List<Bean>


    override val title: String
        get() = "员工啊"

    override val dataList: ArrayList<Bean>
        get() = beanList as ArrayList<Bean>

    override val adapter: BaseSingleTreeAdapter<Bean, out RecyclerView.ViewHolder>
        get() = PersonSingleTreeAdapter(this)

    override val mAdapter: BaseSingleListAdapter<Bean>
        get() = CommonTreeListAdapter()

    override fun initParams() {
        super.initParams()
        beanList = DataUtil.getAreaList(this)
    }

}