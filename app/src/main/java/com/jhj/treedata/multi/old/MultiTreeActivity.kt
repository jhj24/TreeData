package com.jhj.treedata.multi.old

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.multi.BaseMultiListAdapter
import com.jhj.datalibrary.tree.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.tree.multi.BaseMultiTreeAdapter
import com.jhj.treedata.LineItemDecoration
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.StaffBean
import com.jhj.treedata.bean.StaffTreeBean
import kotlinx.android.synthetic.main.layout_top_bar.view.*


/**
 * 多选树
 * Created by jhj on 17-9-6.
 */
class MultiTreeActivity : BaseMultiTreeActivity<StaffTreeBean>() {

    override val treeAdapter: BaseMultiTreeAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = MultiTreeAdapter(this)
    override val listAdapter: BaseMultiListAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = MultiListAdapter()
    override val isSort: Boolean
        get() = false
    override val isSearch: Boolean
        get() = false
    override val itemDecoration: RecyclerView.ItemDecoration?
        get() = LineItemDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = java.util.ArrayList<StaffTreeBean>()
        val staff = TreeDataUtil.getStaffTree(this)
        val staffTree = parseTreeData(staff, list)
        initDataList(staffTree)

        initTopBar(R.layout.layout_top_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.topBar_back.setOnClickListener { finish() }
                view.topBar_title.text = "员工列表"
                view.topBar_right_button.visibility = View.VISIBLE
                view.topBar_right_button.setOnClickListener {
                    val data = getSelectedItems()
                    if (data.size == 0) {
                        Toast.makeText(this@MultiTreeActivity, "请选择员工", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    setResult(RESULT_OK, intent.putExtra("data", data))
                    finish()

                }
            }
        })
    }


    private fun parseTreeData(dataList: List<StaffBean>, dataTree: java.util.ArrayList<StaffTreeBean>): java.util.ArrayList<StaffTreeBean> {
        for (bean in dataList) {
            val treeBean = StaffTreeBean()
            treeBean.content = bean.departName
            treeBean.id = bean.departId
            treeBean.isRoot = true
            dataTree.add(treeBean)
            if (bean.userInfo != null && bean.userInfo.size > 0) {
                val list = java.util.ArrayList<StaffTreeBean>()
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
                var list = java.util.ArrayList<StaffTreeBean>()
                list = parseTreeData(bean.childs, list)
                treeBean.addChildList(list)
            }
        }
        return dataTree
    }
}