package com.jhj.treedata

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jhj.datalibrary.tree.multi.BaseMultiTreeActivity
import com.jhj.datalibrary.tree.single.BaseSingleTreeActivity
import com.jhj.treedata.bean.ChannelTreeBean
import com.jhj.treedata.bean.PersonalTreeBean
import com.jhj.treedata.bean.StaffTreeBean
import com.jhj.treedata.multi.old.MultiTreeActivity
import com.jhj.treedata.multi.simple.PersonMultiTreeActivity
import com.jhj.treedata.single.click.StaffTreeActivity
import com.jhj.treedata.single.select.old.ChannelTreeActivity
import com.jhj.treedata.single.select.slim.PersonTreeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var channelTreeBean: ChannelTreeBean? = null
    private var multiBean1: ArrayList<StaffTreeBean>? = null
    private var multiBean2: ArrayList<PersonalTreeBean>? = null
    private var personTreeBean: PersonalTreeBean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_one.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiTreeActivity::class.java)
            if (multiBean1 != null) {
                intent.putExtra(BaseMultiTreeActivity.SELECTED_DATA, multiBean1)
            }
            startActivityForResult(intent, 1001)
        }

        btn_two.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonMultiTreeActivity::class.java)
            if (multiBean2 != null) {
                intent.putExtra(BaseMultiTreeActivity.SELECTED_DATA, multiBean2)
            }
            startActivityForResult(intent, 1002)
        }

        btn_three.setOnClickListener {
            val intent = Intent(this@MainActivity, ChannelTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.SELECTED_DATA, channelTreeBean)
            startActivityForResult(intent, 1003)
        }

        btn_four.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonTreeActivity::class.java)
            intent.putExtra(BaseSingleTreeActivity.SELECTED_DATA, personTreeBean)
            startActivityForResult(intent, 1004)
        }

        btn_five.setOnClickListener {
            val intent = Intent(this@MainActivity, StaffTreeActivity::class.java);
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            multiBean1 = data?.getSerializableExtra("data") as? ArrayList<StaffTreeBean>?
            if (multiBean1 != null) {
                val s = multiBean1?.map { it.name }.toString()
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == 1002 && resultCode == RESULT_OK) {
            multiBean2 = data?.getSerializableExtra("data") as? ArrayList<PersonalTreeBean>?
            if (multiBean2 != null) {
                val s = multiBean2?.map { it.name }.toString()
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == 1003 && resultCode == RESULT_OK) {
            channelTreeBean = data?.getSerializableExtra("data") as ChannelTreeBean
            Toast.makeText(this@MainActivity, channelTreeBean?.name, Toast.LENGTH_SHORT).show()
        }

        if (requestCode == 1004 && resultCode == RESULT_OK) {
            personTreeBean = data?.getSerializableExtra("data") as PersonalTreeBean
            Toast.makeText(this@MainActivity, personTreeBean?.name, Toast.LENGTH_SHORT).show()
        }

    }

}
