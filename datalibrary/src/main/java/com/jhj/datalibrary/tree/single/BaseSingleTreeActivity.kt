package com.jhj.datalibrary.tree.single

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.jhj.datalibrary.R
import com.jhj.datalibrary.interfaces.OnCustomTopbarListener
import com.jhj.datalibrary.model.IBaseTree
import com.jhj.datalibrary.utils.TreeDealUtil
import com.jhj.decodelibrary.CharacterUtil
import kotlinx.android.synthetic.main.activity_base_tree.*
import kotlinx.android.synthetic.main.layout_search_input_bar.*
import java.util.*

/**
 * 单选树型数据基础Activity
 * Created by jhj on 17-9-8.
 */
abstract class BaseSingleTreeActivity<T : IBaseTree<T>> : Activity() {

    companion object {
        val SELECTED_DATA = "selected_data"
    }


    abstract val adapter: BaseSingleTreeAdapter<T, out RecyclerView.ViewHolder>
    abstract val mAdapter: BaseSingleListAdapter<T, out RecyclerView.ViewHolder>


    lateinit var listAdapter: BaseSingleListAdapter<T, out RecyclerView.ViewHolder>
    lateinit var treeAdapter: BaseSingleTreeAdapter<T, out RecyclerView.ViewHolder>

    private var selectedItem: T? = null
    private var list: MutableList<T> = mutableListOf()
    private lateinit var character: CharacterUtil
    private lateinit var dataList: ArrayList<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_tree)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        initParams()
        treeAdapter = adapter
        listAdapter = mAdapter
        character = CharacterUtil.getInstance(this)
        selectedItem = intent.getSerializableExtra(SELECTED_DATA) as? T


        searchBarMask.setOnTouchListener(keyBoardState)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = treeAdapter
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = listAdapter

    }

    /**
     * 对外公开方法，设置标题
     */
    fun initTopBar(resResource: Int, listener: OnCustomTopbarListener) {
        val view = LayoutInflater.from(this).inflate(resResource, layout_topBar)
        listener.onLayout(view)
    }


    /**
     * 自定义搜索框，对edittext内容变化监听时，监听的方法：textWatcherListener
     */
    fun customSearchBar(resResource: Int, listener: OnCustomTopbarListener) {
        layout_search.visibility = View.GONE
        val view = LayoutInflater.from(this).inflate(resResource, layout_custom_search)
        listener.onLayout(view)
    }


    /**
     * 初始化数据，必须执行的方法
     */
    fun initDataList(dataList: ArrayList<T>) {
        this.dataList = dataList
        if (this.dataList.size > 0) {
            getAllNodeItem(this.dataList, list)
            treeAdapter.list = list as ArrayList<T>
            treeAdapter.dataList = this.dataList
            treeAdapter.selectedItem = selectedItem
            TreeDealUtil.sort(this.dataList)
            treeAdapter.notifyDataSetChanged()
        } else {
            bt_reload.visibility = View.VISIBLE
            tv_load.visibility = View.VISIBLE
            tv_load.text = "没有数据"
        }
    }


    /**
     * 获取被选中的数据
     */
    fun getCheckedItem(): T? {
        treeAdapter.list.forEach { data ->
            if (!data.isRoot && data.isChecked) {
                return data
            }
        }
        return null
    }


    /**
     * 获取所有非根节点数据,并获取首字母拼音
     */
    private fun getAllNodeItem(dataList: MutableList<T>, list: MutableList<T>) {
        dataList.forEach { data ->
            data.firstLetterSpelling = character.getFirstSpelling(data.name)
            data.arraySpelling = character.getStringArray(data.name)
            if (!data.isRoot) {
                list.add(data)
            }
            if (data.children != null) {
                getAllNodeItem(data.children, list)
            }
        }
        Collections.sort(list) { o1, o2 ->
            o1.firstLetterSpelling.compareTo(o2.firstLetterSpelling)
        }
    }


    /**
     * 设置键盘可见性
     */
    private val keyBoardState = View.OnTouchListener { _, _ ->
        var inputManager: InputMethodManager? = null
        searchBarMask.visibility = View.GONE
        et_search.addTextChangedListener(textWatcherListener)
        if (inputManager == null) {
            inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(et_search, 0)
        }
        recyclerView.setOnTouchListener { _, _ ->
            (inputManager as InputMethodManager).hideSoftInputFromWindow(et_search.windowToken, 0)
            if (et_search.text.isNullOrBlank()) {
                searchBarMask.visibility = View.VISIBLE
            }
            false
        }
        rv_search.setOnTouchListener { _, _ ->
            (inputManager as InputMethodManager).hideSoftInputFromWindow(et_search.windowToken, 0)
            false
        }
        false
    }

    /**
     * editText内容变化监听器，用于筛选
     */
    val textWatcherListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            filterData(p0.toString())
        }
    }

    /**
     * 筛选方法
     */
    private fun filterData(text: String) {
        val filterList: ArrayList<T>
        if (text.trim().isEmpty()) {
            recyclerView.visibility = View.VISIBLE
            rv_search.visibility = View.GONE
            treeAdapter.selectedItem = listAdapter.selectedItem
            treeAdapter.notifyDataSetChanged()
        } else {
            recyclerView.visibility = View.GONE
            rv_search.visibility = View.VISIBLE
            filterList = list.filter { data -> !data.isRoot && TreeDealUtil.isFilter(text, data) } as ArrayList<T>
            listAdapter.selectedItem = treeAdapter.selectedItem
            listAdapter.dataList = filterList
            listAdapter.notifyDataSetChanged()
        }
    }


    open fun initParams() {}

}