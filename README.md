# TreeData

封装树型数据基础类


### 1. 引入
```
implementation 'com.github.jhj24:TreeData:v.0.8.3'

//当出现Unable to merge dex 时，可采用以下方式引入（包引入冲突）
implementation('com.github.jhj24:TreeData:v.0.8.3') {
     exclude group: 'com.github.ReactiveX'
}

```
### 2. activity中的对外公开方法

- 2.1 初始化数据
```
fun initDataList(dataList: ArrayList<T>)
```

- 2.2 自定义标题布局
```
fun initTopBar(resResource: Int, listener: OnCustomTopbarListener)
```
- 2.3 自定义搜索框布局（有默认）
```
fun customSearchBar(resResource: Int, listener: OnCustomTopbarListener)
```
- 2.4 获取选中的数据
```
fun getSelectedItems()
```

### 3. adapter中的方法

数据已经在`onBindViewHolder(holder: H, position: Int)`中设置到tag中 ，当在ViewHolder子类中需要数据，可以通过getTag()获取 。

点击时，需要运行的方法
```
fun itemViewOnClick(bean: T)

//多选、单击CheckBox时
fun checkboxOnClick(data: T)
```

当Adapter继承BaseTree时，需要设置分割线以及不同级别树型数据距左边的距离，以满足树型数据的层次感。

当Adapter继承SimpleTree时，此时设置好了分割线以及不同级别距左边的内边距，不满足时可以修改

- 设置分割线样式
```
setDivideLineAttribute(line_divide: View) {}
```
- 设置不同level距左边边距(在初始化方法中调用)
```
init {
    extraPaddingLeft = 100
}
```
### 4. 界面效果
![多选](https://github.com/jhj24/TreeData/blob/master/app/screenshot/multi_selected.gif)
![单选](https://github.com/jhj24/TreeData/blob/master/app/screenshot/single_selected.gif)
![单击](https://github.com/jhj24/TreeData/blob/master/app/screenshot/single_clicked.gif)





