# TreeData

- 不推荐使用addItemDecoration（）添加分割线，若使用addItemDecoration()方法添加，树型数据展开是先展示分割线后出现数据，影响美观。可以在Item的中写一个View作为分割线。
- 简化后的Adapter不用使用addItemDecoration()来添加分割线，也不用在item以View作为分割线，因为adapter中已经在item的下面添加了一个View作为分割线。
- 简化后的Adapter可以直接使用extraPaddingLeft设置二级目录以下目录距左侧的距离
- 实体类可以直接继承BaseTree,若不满足的话可以继承IBaseTree接口
- 默认的Adapter可搜索(isSearch)，有排序(isSort)
- 自定义标题、自定义搜索框



### 1. 引入
```
implementation 'com.github.jhj24:TreeData:v.0.8.3'
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
//item点击事件
fun itemViewOnClick(bean: T)

//多选、单击CheckBox时
fun checkboxOnClick(data: T)
```

当Adapter继承BaseTree时，需要设置分割线以及不同级别树型数据距左边的距离，以满足树型数据的层次感。

当Adapter继承SimpleTree时，此时设置好了分割线以及不同级别距左边的内边距，不满足时可以修改

#### SlimAdapter

- 设置分割线样式
```
override fun setDivideLineAttribute(line_tree_divide: View?) {
     super.setDivideLineAttribute(line_tree_divide)
     line_tree_divide?.setBackgroundColor(Color.RED)
     line_tree_divide?.layoutParams?.height = 2
}
```
- 设置不同level距左边边距(在初始化方法中调用)
```
 override val extraPaddingLeft: Int
        get() = 45
```
### 4. 界面效果
<img src="https://github.com/jhj24/TreeData/blob/master/app/screenshot/multi_selected.gif" width="30%" height="30%" alt=""/><img src="https://github.com/jhj24/TreeData/blob/master/app/screenshot/single_selected.gif" width="30%" height="30%" alt=""/><img src="https://github.com/jhj24/TreeData/blob/master/app/screenshot/single_clicked.gif" width="30%" height="30%" alt=""/>






