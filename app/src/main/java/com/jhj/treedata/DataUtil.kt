package com.jhj.treedata

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jhj.treedata.bean.Bean
import com.jhj.treedata.bean.PositionBean
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by jhj on 17-9-4.
 */
object DataUtil {
    /**
     * 获取省市县信息
     */
    fun getAreaList(context: Context): List<Bean> {
        val inputStream = context.resources.assets.open("treedata.json")
        val text = inputStream.use {
            val buf = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            buf.readText()
        }
        return Gson().fromJson(text, object : TypeToken<List<Bean>>() {}.type)
    }

    /**
     * 获取客户信息
     */
    fun getChannelGroup(context: Context): List<PositionBean> {
        val inputStream = context.resources.assets.open("channelgroup.json")
        val text = inputStream.use {
            val buf = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            buf.readText()
        }
        return Gson().fromJson(text, object : TypeToken<List<PositionBean>>() {}.type)
    }
}