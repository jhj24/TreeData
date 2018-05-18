package com.jhj.treedata.multi

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.multi.BaseMultiListAdapter
import com.jhj.datalibrary.tree.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.tree.multi.BaseMultiTreeAdapter
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.Bean
import kotlinx.android.synthetic.main.layout_top_bar.view.*

/**
 * Created by jhj on 17-9-6.
 */
class MultiActivity : BaseMultiTreeActivity<Bean>() {

    override val adapter: BaseMultiTreeAdapter<Bean, out RecyclerView.ViewHolder>
        get() = MultiAdapter(this)
    override val mAdapter: BaseMultiListAdapter<Bean, out RecyclerView.ViewHolder>
        get() = MultiListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val datalist = TreeDataUtil.getAreaList(this)
        initDataList(datalist as ArrayList<Bean>)
        initTopBar(R.layout.layout_top_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.topBar_back.setOnClickListener { finish() }
                view.topBar_title.text = "员工列表"
                view.topBar_right_button.visibility = View.VISIBLE
                view.topBar_right_button.setOnClickListener {
                    val data = getSelectedItems()
                    if (data.size == 0) {
                        Toast.makeText(this@MultiActivity, "请选择员工", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    setResult(RESULT_OK, intent.putExtra("data", data))
                    finish()

                }
            }
        })
    }
}