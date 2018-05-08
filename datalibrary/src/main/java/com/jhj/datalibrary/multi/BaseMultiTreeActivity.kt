package com.jhj.datalibrary.multi

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jhj.datalibrary.IBaseTree
import com.jhj.datalibrary.R
import com.jhj.datalibrary.TreeDealUtil
import com.jhj.decodelibrary.CharacterUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_tree.*
import kotlinx.android.synthetic.main.layout_search_input_bar.*
import kotlinx.android.synthetic.main.layout_top_bar.*
import java.util.*

/**
 * 树型数据多选
 * Created by jhj on 17-9-12.
 */
abstract class BaseMultiTreeActivity<T : IBaseTree<T>> : Activity() {
    companion object {
        val SELECTED_ITEM = "select_item"
        val RETURN_DATA = "return_data"
    }

    abstract val title: String
    abstract val adapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>
    abstract val mAdapter: BaseMultiListAdapter<T>
    abstract val dataList: ArrayList<T>

    lateinit var treeAdapter: BaseMultiTreeAdapter<T, out RecyclerView.ViewHolder>
    lateinit var listAdapter: BaseMultiListAdapter<T>

    val list: ArrayList<T> = arrayListOf()
    private var selectedItemList: ArrayList<T>? = null
    private lateinit var character: CharacterUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_tree)
        treeAdapter = adapter
        listAdapter = mAdapter
        character = CharacterUtil.getInstance(this)
        selectedItemList = intent.getSerializableExtra(RETURN_DATA) as? ArrayList<T>
        searchBarMask.setOnTouchListener(keyboardState)

        initParams()

        topBar_back.setOnClickListener { finish() }
        topBar_title.text = title
        topBar_right_button.visibility = View.VISIBLE
        topBar_right_button.text = "确认"
        topBar_right_button.setOnClickListener { onItemSelected() }
        recyclerView.adapter = treeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = listAdapter
        rv_search.layoutManager = LinearLayoutManager(this)


        initData()
    }


    private fun onItemSelected() {
        val list: ArrayList<T> = arrayListOf()
        getSelectedItem(dataList, list)
        if (list.isEmpty()) {
            Toast.makeText(this, "请选择" + title, Toast.LENGTH_SHORT).show()
            return
        }
        setResult(RESULT_OK, intent.putExtra(SELECTED_ITEM, list))
        finish()
    }

    /**
     * 获取所有被选中的item
     */
    private fun getSelectedItem(dataList: List<T>, list: ArrayList<T>) {
        dataList.forEach { data ->
            if (data.isRoot && data.children.isNotEmpty()) {
                getSelectedItem(data.children, list)
            }
            if (!data.isRoot && data.isChecked && list.indexOf(data) == -1) {
                list.add(data)
            }
        }
    }


    private fun initData() {
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
     * 递归遍历列表,对列表的name进行处理,并对list排序
     */
    private fun getAllNodeItem(dataList: List<T>, list: ArrayList<T>) {
        dataList.forEach { data ->
            data.firstLetterSpelling = character.getFirstSpelling(data.name)
            data.arraySpelling = character.getStringArray(data.name)
            if (!data.isRoot) {
                list.add(data)
            }
            if (data.isRoot && data.children.isNotEmpty()) {
                getAllNodeItem(data.children, list)
            }
        }
        Collections.sort(list) { t: T, t1: T -> t.firstLetterSpelling.compareTo(t1.firstLetterSpelling) }
    }


    /**
     * 利用递归，当所有的子节点都被选中的时候则父节点被选中
     */
    private fun recursionCheckState(dataList: List<T>): Boolean {
        var allChildrenChecked = true
        dataList.forEach { data ->
            if (!data.isRoot) {
                allChildrenChecked = setBeanCheckState(data) && allChildrenChecked
            } else {
                data.isChecked = recursionCheckState(data.children)
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

    private val keyboardState = View.OnTouchListener { p0, p1 ->
        searchBarMask.visibility = View.GONE
        et_search.addTextChangedListener(textChangedListener)
        var inputManager: InputMethodManager? = null
        if (inputManager == null) {
            inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(et_search, 0)
        }
        recyclerView.setOnTouchListener { view, motionEvent ->
            (inputManager as InputMethodManager).hideSoftInputFromWindow(et_search.windowToken, 0)
            if (et_search.text.isNullOrBlank()) {
                searchBarMask.visibility = View.VISIBLE
            }
            false
        }
        rv_search.setOnTouchListener { view, motionEvent ->
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

    fun filterData() {
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


