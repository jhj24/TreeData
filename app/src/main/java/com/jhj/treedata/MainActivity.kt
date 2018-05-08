package com.jhj.treedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.jhj.datalibrary.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.single.BaseSingleTreeActivity
import com.jhj.treedata.multi.MultiActivity
import com.jhj.treedata.bean.Bean
import com.jhj.treedata.person.PersonSingleTreeActivity
import com.jhj.treedata.bean.PositionBean
import com.jhj.treedata.position.PositionSingleTreeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var bean: Bean? = null
    private var positionBean: PositionBean? = null
    private var multiBean: ArrayList<Bean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        person.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonSingleTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.RETURN_ITEM, bean)
            startActivityForResult(intent, 1001)
        }

        position.setOnClickListener {
            val intent = Intent(this@MainActivity, PositionSingleTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.RETURN_ITEM, positionBean)
            startActivityForResult(intent, 1002)
        }

        multi.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            if (multiBean != null) {
                intent.putExtra(BaseMultiTreeActivity.RETURN_DATA, multiBean)
            }
            startActivityForResult(intent, 1003)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            bean = data?.getSerializableExtra(BaseSingleTreeActivity.SELECTED_DATA) as? Bean
            Toast.makeText(this@MainActivity, bean?.name, Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 1002 && resultCode == RESULT_OK) {
            positionBean = data?.getSerializableExtra(BaseSingleTreeActivity.SELECTED_DATA) as PositionBean
            Toast.makeText(this@MainActivity, positionBean?.name, Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 1003 && resultCode == RESULT_OK) {
            multiBean = data?.getSerializableExtra(BaseMultiTreeActivity.SELECTED_ITEM) as? ArrayList<Bean>?
        }
    }

}
