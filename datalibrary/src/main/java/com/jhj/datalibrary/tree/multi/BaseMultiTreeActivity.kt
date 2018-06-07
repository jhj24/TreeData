package com.jhj.datalibrary.tree.multi

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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_tree.*
import kotlinx.android.synthetic.main.layout_search_input_bar.*
import java.util.*

/**
 * 树型数据多选，基础Activity
 * Created by jhj on 17-9-12.
 */
abstract class BaseMultiTreeActivity<T : IBaseTree<T>> : Activity() {
    companion object {
        const val SELECTED_DATA = "selected_data" //进入该界面时，设置选中的 item
    }

    abstract val adapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>
    abstract val mAdapter: BaseMultiListAdapter<T, out RecyclerView.ViewHolder>

    lateinit var treeAdapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>
    lateinit var listAdapter: BaseMultiListAdapter<T, out RecyclerView.ViewHolder>

    val list: ArrayList<T> = arrayListOf()
    private var selectedItemList: ArrayList<T>? = null
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
        selectedItemList = intent.getSerializableExtra(SELECTED_DATA) as? ArrayList<T>
        searchBarMask.setOnTouchListener(keyboardState)

        recyclerView.adapter = treeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = listAdapter
        rv_search.layoutManager = LinearLayoutManager(this)
    }

    /**
     * 对外公开方法，设置标题
     */
    fun initTopBar(resResource: Int, listener: OnCustomTopbarListener) {
        val view = LayoutInflater.from(this).inflate(resResource, layout_tree_top_bar, false)
        listener.onLayout(view)
        layout_tree_top_bar.addView(view)
    }

    /**
     * 自定义搜索框，对edittext内容变化监听时，监听的方法：textWatcherListener
     */
    fun customSearchBar(resResource: Int, listener: OnCustomTopbarListener) {
        layout_search.visibility = View.GONE
        val view = LayoutInflater.from(this).inflate(resResource, layout_tree_custom_search, false)
        listener.onLayout(view)
    }

    /**
     * 对外公开必须实现的方法，初始化数据
     *
     */
    fun initDataList(dataList: ArrayList<T>) {
        this.dataList = dataList
        Observable
                .create<ArrayList<T>> {
                    getAllNodeItem(dataList, list)
                    recursionCheckState(dataList)
                    it.onNext(dataList)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    TreeDealUtil.sort(dataList)
                    treeAdapter.dataList = list
                    treeAdapter.notifyDataSetChanged()
                }

    }

    /**
     * 对外公开方法，获取所有被选中的 items
     *
     */
    fun getSelectedItems(): ArrayList<T> {
        val list: ArrayList<T> = arrayListOf()
        parseSelectedItem(dataList, list)
        return list
    }

    /**
     * 获取所有被选中的item
     */
    private fun parseSelectedItem(dataList: List<T>, list: ArrayList<T>) {
        dataList.forEach { data ->
            if (data.isRoot && data.children?.isNotEmpty() == true) {
                parseSelectedItem(data.children, list)
            }
            if (!data.isRoot && data.isChecked && list.indexOf(data) == -1) {
                list.add(data)
            }
        }
    }


    /**
     * 递归遍历列表,对列表的name进行处理,并对list排序
     */
    private fun getAllNodeItem(dataList: List<T>, list: ArrayList<T>) {
        dataList.forEach { data ->
            data.firstLetterSpelling = character.getFirstSpelling(data.name)
            data.arraySpelling = character.getStringArray(data.name)
            if (!data.isRoot) {
                list.add(data)
            }
            if (data.isRoot && data.children?.isNotEmpty() == true) {
                getAllNodeItem(data.children, list)
            }
        }
        list.sortWith(Comparator { t: T, t1: T ->
            t.firstLetterSpelling.compareTo(t1.firstLetterSpelling)
        })
    }


    /**
     * 利用递归，当所有的子节点都被选中的时候则父节点被选中
     */
    private fun recursionCheckState(dataList: List<T>): Boolean {
        var allChildrenChecked = true
        dataList.forEach { data ->
            if (!data.isRoot) {
                allChildrenChecked = setBeanCheckState(data) && allChildrenChecked
            } else if(data.children?.isNotEmpty() == true){
                data.isChecked = recursionCheckState(data.children)
                allChildrenChecked = data.isChecked && allChildrenChecked
            }else{
                data.isChecked = false
                allChildrenChecked = data.isChecked && allChildrenChecked
            }
        }
        if (dataList.isEmpty()) {
            allChildrenChecked = false
        }
        return allChildrenChecked
    }

    /**
     * 当被选中的list中包含data时，设置data被选中
     */
    private fun setBeanCheckState(data: T): Boolean {
        selectedItemList?.forEach { item ->
            if (item.id == data.id && item.name == data.name) {
                data.isChecked = true
                return true
            }
        }
        return false
    }

    private val keyboardState = View.OnTouchListener { _, _ ->
        searchBarMask.visibility = View.GONE
        et_search.addTextChangedListener(textChangedListener)
        var inputManager: InputMethodManager? = null
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
    private val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            filterData()
        }

    }

    private fun filterData() {
        if (et_search.text.isNullOrBlank()) {
            recyclerView.visibility = View.VISIBLE
            rv_search.visibility = View.GONE
            treeAdapter.dataList = dataList
            treeAdapter.notifyDataSetChanged()
        } else {
            recyclerView.visibility = View.GONE
            rv_search.visibility = View.VISIBLE
            val dataList = list.filter { data -> TreeDealUtil.isFilter(et_search.text.trim().toString(), data) }
            listAdapter.dataList = dataList as ArrayList<T>
            listAdapter.notifyDataSetChanged()
        }
    }

    open fun initParams() {
    }
}


