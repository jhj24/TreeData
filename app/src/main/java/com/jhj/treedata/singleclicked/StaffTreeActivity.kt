package com.jhj.treedata.singleclicked

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.single.BaseSingleListAdapter
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.StaffBean
import com.jhj.treedata.bean.StaffTreeBean
import kotlinx.android.synthetic.main.layout_search_bar.view.*
import kotlinx.android.synthetic.main.layout_top_bar1.view.*
import java.util.ArrayList

/**
 * 不带checkbox的树型数据点击事件，进入新的界面
 * </p>
 * 对非理想的数据进行格式化
 * Created by jhj on 18-5-18.
 */

class StaffTreeActivity : BaseSingleTreeActivity<StaffTreeBean>() {

    override val adapter: BaseSingleTreeAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = StaffTreeAdapter(this)
    override val mAdapter: BaseSingleListAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = StaffTreeListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTopBar(R.layout.layout_top_bar1, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.topBar_title.text = "点击事件"
                view.topBar_back.setOnClickListener { v ->
                    finish()
                }
            }
        })

        /**
         *
         */
        customSearchBar(R.layout.layout_search_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.et_search.addTextChangedListener(textWatcherListener)
            }
        })

        val list = ArrayList<StaffTreeBean>()
        val staff = TreeDataUtil.getStaffTree(this)
        val staffTree = parseTreeData(staff, list)
        initDataList(staffTree)


    }


    private fun parseTreeData(dataList: List<StaffBean>, dataTree: ArrayList<StaffTreeBean>): ArrayList<StaffTreeBean> {
        for (bean in dataList) {
            val treeBean = StaffTreeBean()
            treeBean.content = bean.departName
            treeBean.id = bean.departId
            treeBean.isRoot = true
            dataTree.add(treeBean)
            if (bean.userInfo != null && bean.userInfo.size > 0) {
                val list = ArrayList<StaffTreeBean>()
                for (userInfoBean in bean.userInfo) {
                    val treeBean1 = StaffTreeBean()
                    treeBean1.id = userInfoBean.userGuid
                    treeBean1.icon = userInfoBean.photo
                    treeBean1.phone = userInfoBean.uname
                    treeBean1.content = userInfoBean.username
                    treeBean1.department = userInfoBean.department + "-" + userInfoBean.post
                    treeBean1.isRoot = false
                    list.add(treeBean1)
                }
                treeBean.childList = list
            }
            if (bean.childs != null && bean.childs.size > 0) {
                var list = ArrayList<StaffTreeBean>()
                list = parseTreeData(bean.childs, list)
                treeBean.addChildList(list)
            }
        }
        return dataTree
    }

}
