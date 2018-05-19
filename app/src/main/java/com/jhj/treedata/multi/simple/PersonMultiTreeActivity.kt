package com.jhj.treedata.multi.simple

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.multi.BaseMultiListAdapter
import com.jhj.datalibrary.tree.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.tree.multi.BaseMultiTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.PersonalTreeBean
import kotlinx.android.synthetic.main.layout_top_bar.view.*

/**
 * 多选树
 * Created by jhj on 17-9-6.
 */
class PersonMultiTreeActivity : BaseMultiTreeActivity<PersonalTreeBean>() {

    override val adapter: BaseMultiTreeAdapter<PersonalTreeBean, out RecyclerView.ViewHolder>
        get() = PersonMultiTreeAdapter(this)
    override val mAdapter: BaseMultiListAdapter<PersonalTreeBean, out RecyclerView.ViewHolder>
        get() = PersonMultiListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val datalist = TreeDataUtil.getAreaList(this)
        initDataList(datalist as ArrayList<PersonalTreeBean>)
        initTopBar(R.layout.layout_top_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.topBar_back.setOnClickListener { finish() }
                view.topBar_title.text = "员工列表"
                view.topBar_right_button.visibility = View.VISIBLE
                view.topBar_right_button.setOnClickListener {
                    val data = getSelectedItems()
                    if (data.size == 0) {
                        Toast.makeText(this@PersonMultiTreeActivity, "请选择员工", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    setResult(RESULT_OK, intent.putExtra("data", data))
                    finish()

                }
            }
        })
    }
}