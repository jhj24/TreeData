package com.jhj.treedata.multi

import android.support.v7.widget.RecyclerView
import com.jhj.datalibrary.multi.BaseMultiListAdapter
import com.jhj.datalibrary.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.multi.BaseMultiTreeAdapter
import com.jhj.treedata.DataUtil
import com.jhj.treedata.bean.Bean
import java.util.*

/**
 * Created by jhj on 17-9-6.
 */
class MultiActivity : BaseMultiTreeActivity<Bean>() {

    private lateinit var beanList: List<Bean>


    override val title: String
        get() = "员工"
    override val dataList: ArrayList<Bean>
        get() = beanList as ArrayList<Bean>
    override val adapter: BaseMultiTreeAdapter<Bean, out RecyclerView.ViewHolder>
        get() = MultiAdapter(this)
    override val mAdapter: BaseMultiListAdapter<Bean>
        get() = MultiListAdapter()


    override fun initParams() {
        super.initParams()
        beanList = DataUtil.getAreaList(this)
    }

}