package com.jhj.treedata.position

import android.support.v7.widget.RecyclerView
import com.jhj.datalibrary.single.BaseSingleTreeActivity
import com.jhj.datalibrary.single.BaseSingleTreeAdapter
import com.jhj.treedata.DataUtil
import com.jhj.treedata.bean.PositionBean

/**
 * Created by jhj on 17-9-6.
 */
class PositionSingleTreeActivity : BaseSingleTreeActivity<PositionBean>() {


    lateinit var beanList: List<PositionBean>


    override val title: String
        get() = "员工"

    override val dataList: ArrayList<PositionBean>
        get() = beanList as ArrayList<PositionBean>

    override val adapter: BaseSingleTreeAdapter<PositionBean, out RecyclerView.ViewHolder>
        get() = PositionSingleTreeAdapter(this)

    override val mAdapter: CommonTreeListAdapter<PositionBean>
        get() = CommonTreeListAdapter()

    override fun initParams() {
        super.initParams()
        beanList = DataUtil.getChannelGroup(this)
    }
}