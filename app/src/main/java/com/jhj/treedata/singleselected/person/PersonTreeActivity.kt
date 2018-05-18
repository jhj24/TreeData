package com.jhj.treedata.singleselected.person

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.tree.single.BaseSingleListAdapter
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeAdapter
import com.jhj.treedata.R
import com.jhj.treedata.TreeDataUtil
import com.jhj.treedata.bean.PersonalTreeBean
import com.jhj.treedata.singleselected.CommonListAdapter
import kotlinx.android.synthetic.main.layout_top_bar.view.*
import java.util.*

/**
 * 单选树
 * Created by jhj on 17-9-6.
 */
class PersonTreeActivity : BaseSingleTreeActivity<PersonalTreeBean>() {

    override val adapter: BaseSingleTreeAdapter<PersonalTreeBean, out RecyclerView.ViewHolder>
        get() = PersonTreeAdapter(this)

    override val mAdapter: BaseSingleListAdapter<PersonalTreeBean, out RecyclerView.ViewHolder>
        get() = CommonListAdapter()


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
                    val data = getCheckedItem()
                    if (data == null) {
                        Toast.makeText(this@PersonTreeActivity, "请选择员工", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    setResult(RESULT_OK, intent.putExtra("data", data))
                    finish()

                }
            }
        })
    }


}