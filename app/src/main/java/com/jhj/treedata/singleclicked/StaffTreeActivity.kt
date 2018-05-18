package com.jhj.treedata.singleclicked

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.jhj.datalibrary.tree.single.BaseSingleListAdapter
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.StaffBean
import com.jhj.treedata.bean.StaffTreeBean
import java.util.ArrayList

/**
 * Created by jhj on 18-5-18.
 */

class StaffTreeActivity : BaseSingleTreeActivity<StaffTreeBean>() {

    override val adapter: BaseSingleTreeAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val mAdapter: BaseSingleListAdapter<StaffTreeBean, out RecyclerView.ViewHolder>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val staff = TreeDataUtil.getStaffTree(this)


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
