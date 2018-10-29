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
import kotlinx.android.synthetic.main.activity_tree_data.*
import kotlinx.android.synthetic.main.layout_tree_search_bar.*
import java.util.*

/**
 * 树型数据多选，基础Activity
 * Created by jhj on 17-9-12.
 */
abstract class BaseMultiTreeActivity<T : IBaseTree<T>> : Activity() {
    companion object {
        const val SELECTED_DATA = "selected_data" //进入该界面时，设置选中的 item
    }

    abstract val treeAdapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>
    abstract val listAdapter: BaseMultiListAdapter<T, out RecyclerView.ViewHolder>?

    private var mTreeAdapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>? = null
    private var mListAdapter: BaseMultiListAdapter<T, out RecyclerView.ViewHolder>? = null

    private val list: ArrayList<T> = arrayListOf()
    private var selectedItemList: ArrayList<T>? = null
    private lateinit var character: CharacterUtil
    private lateinit var dataList: ArrayList<T>

    open val isSearch = true
    open val isSort = true
    open val itemDecoration: RecyclerView.ItemDecoration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_data)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        mTreeAdapter = treeAdapter
        mListAdapter = listAdapter
        character = CharacterUtil.getInstance(this)
        selectedItemList = intent.getSerializableExtra(SELECTED_DATA) as? ArrayList<T>

        if (!isSearch) {
            layout_tree_search.visibility = View.GONE
            layout_tree_custom_search.visibility = View.GONE
        } else {
            layout_tree_search.visibility = View.VISIBLE
            layout_tree_custom_search.visibility = View.VISIBLE
            searchBarMask.setOnTouchListener(keyboardState)
        }

        if (itemDecoration != null) {
            recyclerView_tree.addItemDecoration(itemDecoration)
            recyclerView_tree_search.addItemDecoration(itemDecoration)
        }

        recyclerView_tree.adapter = mTreeAdapter
        recyclerView_tree.layoutManager = LinearLayoutManager(this)
        recyclerView_tree_search.adapter = mListAdapter
        recyclerView_tree_search.layoutManager = LinearLayoutManager(this)
    }

    /**
     * 对外公开方法，设置标题
     */
    fun initTopBar(resResource: Int, listener: OnCustomTopbarListener) {
        val view = LayoutInflater.from(this).inflate(resResource, layout_tree_top_bar)
        listener.onLayout(view)
    }

    /**
     * 自定义搜索框，对edittext内容变化监听时，监听的方法：textWatcherListener
     */
    fun customSearchBar(resResource: Int, listener: OnCustomTopbarListener) {
        layout_tree_search.visibility = View.GONE
        layout_tree_custom_search.visibility = View.VISIBLE
        val view = LayoutInflater.from(this).inflate(resResource, layout_tree_custom_search)
        listener.onLayout(view)
    }

    /**
     * 对外公开必须实现的方法，初始化数据
     *
     */
    fun initDataList(dataList: ArrayList<T>) {
        this.dataList = dataList
        if (this.dataList.size == 0) {
            bt_tree_reload.visibility = View.VISIBLE
            tv_tree_load.visibility = View.VISIBLE
            tv_tree_load.text = "没有数据"
            return
        }

        Observable
                .create<ArrayList<T>> {
                    getAllNodeItem(dataList, list)
                    recursionCheckState(dataList)
                    it.onNext(dataList)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    if (isSort) {
                        TreeDealUtil.sort(dataList)
                    }
                    mTreeAdapter?.dataList = list
                    mTreeAdapter?.notifyDataSetChanged()
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
        if (isSort) {
            list.sortWith(Comparator { t: T, t1: T ->
                t.firstLetterSpelling.compareTo(t1.firstLetterSpelling)
            })
        }

    }


    /**
     * 利用递归，当所有的子节点都被选中的时候则父节点被选中
     */
    private fun recursionCheckState(dataList: List<T>): Boolean {
        var allChildrenChecked = true
        dataList.forEach { data ->
            if (!data.isRoot) {
                allChildrenChecked = setBeanCheckState(data) && allChildrenChecked
            } else if (data.children?.isNotEmpty() == true) {
                data.isChecked = recursionCheckState(data.children)
                allChildrenChecked = data.isChecked && allChildrenChecked
            } else {
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
        recyclerView_tree.setOnTouchListener { _, _ ->
            (inputManager as InputMethodManager).hideSoftInputFromWindow(et_search.windowToken, 0)
            if (et_search.text.isNullOrBlank()) {
                searchBarMask.visibility = View.VISIBLE
            }
            false
        }
        recyclerView_tree_search.setOnTouchListener { _, _ ->
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
            recyclerView_tree.visibility = View.VISIBLE
            recyclerView_tree_search.visibility = View.GONE
            mTreeAdapter?.dataList = dataList
            mTreeAdapter?.notifyDataSetChanged()
        } else {
            recyclerView_tree.visibility = View.GONE
            recyclerView_tree_search.visibility = View.VISIBLE
            val dataList = list.filter { data -> TreeDealUtil.isFilter(et_search.text.trim().toString(), data) }
            mListAdapter?.dataList = dataList as ArrayList<T>
            mListAdapter?.notifyDataSetChanged()
        }
    }

}


