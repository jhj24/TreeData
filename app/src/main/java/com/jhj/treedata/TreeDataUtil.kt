package com.jhj.treedata

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jhj.treedata.bean.PersonalTreeBean
import com.jhj.treedata.bean.ChannelTreeBean
import com.jhj.treedata.bean.StaffBean
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by jhj on 17-9-4.
 */
object TreeDataUtil {
    /**
     * 获取省市县信息
     */
    fun getAreaList(context: Context): List<PersonalTreeBean> {
        val inputStream = context.resources.assets.open("stafftree.json")
        val text = inputStream.use {
            val buf = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            buf.readText()
        }
        return Gson().fromJson(text, object : TypeToken<List<PersonalTreeBean>>() {}.type)
    }

    /**
     * 获取客户信息
     */
    fun getChannelGroup(context: Context): List<ChannelTreeBean> {
        val inputStream = context.resources.assets.open("channelgroup.json")
        val text = inputStream.use {
            val buf = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            buf.readText()
        }
        return Gson().fromJson(text, object : TypeToken<List<ChannelTreeBean>>() {}.type)
    }

    fun getStaffTree(context: Context): List<StaffBean> {
        val inputStream = context.resources.assets.open("staff.json")
        val text = inputStream.use {
            val buf = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            buf.readText()
        }
        return Gson().fromJson(text, object : TypeToken<List<StaffBean>>() {}.type)
    }
}