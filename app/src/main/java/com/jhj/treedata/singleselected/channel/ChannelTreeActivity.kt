package com.jhj.treedata.singleselected.channel

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.LineItemDecoration
import com.jhj.treedata.singleselected.CommonListAdapter
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.ChannelTreeBean
import kotlinx.android.synthetic.main.layout_search_bar.view.*
import kotlinx.android.synthetic.main.layout_top_bar.view.*
import java.util.ArrayList

/**
 * 单选树
 * Created by jhj on 17-9-6.
 */
class ChannelTreeActivity : BaseSingleTreeActivity<ChannelTreeBean>() {


    override val treeAdapter: BaseSingleTreeAdapter<ChannelTreeBean, out RecyclerView.ViewHolder>
        get() = ChannelTreeAdapter(this)

    override val listAdapter: CommonListAdapter<ChannelTreeBean>
        get() = CommonListAdapter()

    override val itemDecoration: RecyclerView.ItemDecoration?
        get() = LineItemDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        customSearchBar(R.layout.layout_search_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.et_search.addTextChangedListener(textWatcherListener)
            }
        })

        val datalist = TreeDataUtil.getChannelGroup(this)
        initDataList(datalist as ArrayList<ChannelTreeBean>)
        initTopBar(R.layout.layout_top_bar, object : OnCustomTopbarListener {
            override fun onLayout(view: View) {
                view.topBar_back.setOnClickListener { finish() }
                view.topBar_title.text = "员工列表"
                view.topBar_right_button.visibility = View.VISIBLE
                view.topBar_right_button.setOnClickListener {
                    val data = getCheckedItem()
                    if (data == null) {
                        Toast.makeText(this@ChannelTreeActivity, "请选择员工", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    setResult(RESULT_OK, intent.putExtra("data", data))
                    finish()

                }
            }
        })
    }
}