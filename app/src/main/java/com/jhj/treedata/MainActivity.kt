package com.jhj.treedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.jhj.datalibrary.tree.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.treedata.bean.ChannelTreeBean
import com.jhj.treedata.bean.PersonalTreeBean
import com.jhj.treedata.multi.MultiTreeActivity
import com.jhj.treedata.singleclicked.StaffTreeActivity
import com.jhj.treedata.singleselected.channel.ChannelTreeActivity
import com.jhj.treedata.singleselected.person.PersonTreeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var bean: PersonalTreeBean? = null
    private var positionBean: ChannelTreeBean? = null
    private var multiBean: ArrayList<PersonalTreeBean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        person.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.SELECTED_DATA, bean)
            startActivityForResult(intent, 1001)
        }

        position.setOnClickListener {
            val intent = Intent(this@MainActivity, ChannelTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.SELECTED_DATA, positionBean)
            startActivityForResult(intent, 1002)
        }

        multi.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiTreeActivity::class.java)
            if (multiBean != null) {
                intent.putExtra(BaseMultiTreeActivity.SELECTED_DATA, multiBean)
            }
            startActivityForResult(intent, 1003)
        }
        test.setOnClickListener {
            val intent = Intent(this@MainActivity, StaffTreeActivity::class.java);
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            bean = data?.getSerializableExtra("data") as? PersonalTreeBean
            Toast.makeText(this@MainActivity, bean?.name, Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 1002 && resultCode == RESULT_OK) {
            positionBean = data?.getSerializableExtra("data") as ChannelTreeBean
            Toast.makeText(this@MainActivity, positionBean?.name, Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 1003 && resultCode == RESULT_OK) {
            multiBean = data?.getSerializableExtra("data") as? ArrayList<PersonalTreeBean>?
            if (multiBean != null) {
                Toast.makeText(this@MainActivity, multiBean?.get(0)?.name, Toast.LENGTH_SHORT).show()
            }

        }
    }

}
