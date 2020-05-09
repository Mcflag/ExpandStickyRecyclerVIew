package com.ccooy.expandablerecyclerview.decoration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajguan.library.EasyRefreshLayout
import com.ajguan.library.LoadModel

import com.ccooy.expandablerecyclerview.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

import java.util.ArrayList

import com.ccooy.expandablerecyclerview.decoration.GroupModel.getHeader
import com.ccooy.expandablerecyclerview.decoration.widget.*
import com.google.gson.Gson
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout

/**
 * 可展开、收起的列表。
 */
class SampleActivity : AppCompatActivity() {

    private var rvList: RecyclerView? = null
//    private var refreshLayout: SmartRefreshLayout? = null
//    private var easyRefreshLayout: EasyRefreshLayout? = null
    private var twinklingRefreshLayout: TwinklingRefreshLayout? = null
    private var stickyHeaderLayout: StickyHeaderLayout? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var groupList: ArrayList<ExpandableGroupEntity>? = null
    private var displayList: ArrayList<ExpandableGroupEntity>? = null
    private var nameList: ArrayList<String> = ArrayList()
    private var adapter: ExpandableAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var isLoadGroup = false
    private lateinit var mDecoration:SectionDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoration_sample)
        rvList = findViewById(R.id.rv_list)
        stickyHeaderLayout = findViewById(R.id.sticky_layout)
        floatingActionButton = findViewById(R.id.fab)
        twinklingRefreshLayout = findViewById(R.id.refreshLayout)
        linearLayoutManager = LinearLayoutManager(this)
        rvList!!.layoutManager = linearLayoutManager
        val animator = DefaultItemAnimator()
        animator.moveDuration = 70
        animator.changeDuration = 70
        animator.addDuration = 70
        animator.removeDuration = 70
        animator.setAnimEndListener {
//            stickyHeaderLayout!!.updateStickyDelayed()
        }
        rvList!!.itemAnimator = animator
//        nameList = changeDisplayListToNameList()
//        mDecoration = SectionDecoration(
//            nameList,
//            this,
//            object : SectionDecoration.DecorationCallback {
//                override fun getGroupId(position: Int): String {
//                    return if (nameList.size>0) {
//                        nameList[position]
//                    } else "-1"
//                }
//
//                override fun getGroupFirstLine(position: Int): String {
//                    return if (nameList.size>0) {
//                        nameList[position]
//                    } else ""
//                }
//            })
//        rvList!!.addItemDecoration(mDecoration)
        rvList!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                //                    stickyHeaderLayout.updateStickyDelayed();
                //                }
                if (null != linearLayoutManager) {
                    val position = linearLayoutManager!!.findFirstVisibleItemPosition()
                    val firstVisiableChildView = linearLayoutManager!!.findViewByPosition(position)
                    val itemHeight = firstVisiableChildView!!.height
                    val flag = position * itemHeight - firstVisiableChildView.top
                    if (flag >= itemHeight) {
                        floatingActionButton!!.visibility = View.VISIBLE
                    } else {
                        floatingActionButton!!.visibility = View.GONE
                    }
                }
            }

//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//            }
        })

        floatingActionButton!!.setOnClickListener { rvList!!.smoothScrollToPosition(0) }

//        refreshLayout!!.setRefreshHeader(RefreshLoadingHeader(this))
//        refreshLayout!!.setOnRefreshListener { refreshLayout ->
//            refresh()
//            refreshLayout.finishRefresh(true)
//        }
//        refreshLayout!!.setOnLoadMoreListener {
//            if (null != linearLayoutManager) {
//                if (isLoadGroup) {
//                    val last = linearLayoutManager!!.findLastVisibleItemPosition()
//                    val lastGroup = adapter!!.getGroupPositionForPosition(last)
//                    if (adapter!!.getGroupCount() - lastGroup <= 5) {
//                        loadMoreTime()
//                        adapter!!.notifyDataSetChanged()
//                        refreshLayout!!.finishLoadMore(true)
//                    }
//                } else {
//                    val last = linearLayoutManager!!.findLastVisibleItemPosition()
//                    val lastGroup = adapter!!.getGroupPositionForPosition(last)
//                    if (adapter!!.getChildrenCount(lastGroup) - adapter!!.getChildPositionForPosition(lastGroup, last) <= 5) {
//                        loadMoreData(lastGroup)
//                        adapter!!.notifyDataSetChanged()
//                        refreshLayout!!.finishLoadMore(true)
//                    }
//                }
//            }
//        }

//        easyRefreshLayout!!.addEasyEvent(object: EasyRefreshLayout.EasyEvent{
//            override fun onLoadMore() {
//                if (null != linearLayoutManager) {
//                    if (isLoadGroup) {
//                        loadMoreTime()
//                        adapter!!.notifyDataSetChanged()
//                        easyRefreshLayout!!.loadMoreComplete()
//                    } else {
//                        val last = linearLayoutManager!!.findLastVisibleItemPosition()
//                        val lastGroup = adapter!!.getGroupPositionForPosition(last)
//                        loadMoreData(lastGroup)
//                        adapter!!.notifyDataSetChanged()
//                        easyRefreshLayout!!.loadMoreComplete()
//                    }
//                }
//            }
//
//            override fun onRefreshing() {
//                refresh()
//                easyRefreshLayout!!.refreshComplete()
//            }
//        })
//        easyRefreshLayout!!.setLoadMoreModel(LoadModel.COMMON_MODEL)

        twinklingRefreshLayout!!.setOnRefreshListener(object: RefreshListenerAdapter(){
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                if (null != linearLayoutManager) {
                    if (isLoadGroup) {
                        loadMoreTime()
                        adapter!!.notifyDataSetChanged()
                        twinklingRefreshLayout!!.finishLoadmore()
                    } else {
                        val last = linearLayoutManager!!.findLastVisibleItemPosition()
                        val lastGroup = adapter!!.getGroupPositionForPosition(last)
                        loadMoreData(lastGroup)
                        adapter!!.notifyDataSetChanged()
                        twinklingRefreshLayout!!.finishLoadmore()
                    }
                }
            }

            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                refresh()
                twinklingRefreshLayout!!.finishRefreshing()
            }

            override fun onPullDownReleasing(
                refreshLayout: TwinklingRefreshLayout?,
                fraction: Float
            ) {
                super.onPullDownReleasing(refreshLayout, fraction)
                stickyHeaderLayout!!.isSticky=false
            }

            override fun onPullingDown(refreshLayout: TwinklingRefreshLayout?, fraction: Float) {
                super.onPullingDown(refreshLayout, fraction)
                stickyHeaderLayout!!.isSticky=false
            }

//            override fun onPullingUp(refreshLayout: TwinklingRefreshLayout?, fraction: Float) {
//                super.onPullingUp(refreshLayout, fraction)
//                stickyHeaderLayout!!.updateStickyView()
//            }
//
//            override fun onPullUpReleasing(
//                refreshLayout: TwinklingRefreshLayout?,
//                fraction: Float
//            ) {
//                super.onPullUpReleasing(refreshLayout, fraction)
//                stickyHeaderLayout!!.updateStickyView()
//            }

            override fun onFinishRefresh() {
                super.onFinishRefresh()
                stickyHeaderLayout!!.isSticky=true
                stickyHeaderLayout!!.updateStickyView()
            }

            override fun onRefreshCanceled() {
                super.onRefreshCanceled()
                stickyHeaderLayout!!.isSticky=true
                stickyHeaderLayout!!.updateStickyView()
            }

//            override fun onLoadmoreCanceled() {
//                super.onLoadmoreCanceled()
//                stickyHeaderLayout!!.isSticky=true
//                stickyHeaderLayout!!.updateStickyView()
//            }
//
//            override fun onFinishLoadMore() {
//                super.onFinishLoadMore()
//                stickyHeaderLayout!!.isSticky=true
//                stickyHeaderLayout!!.updateStickyView()
//            }
        })

        groupList = ArrayList()
        displayList = ArrayList()
        adapter = ExpandableAdapter(this, displayList)
        adapter!!.setOnHeaderClickListener { adapter, holder, groupPosition ->
            val expandableAdapter = adapter as ExpandableAdapter
            val tempPosition = expandableAdapter.getPositionForGroup(groupPosition)
            if (expandableAdapter.isExpand(groupPosition)) {
                groupList!![groupPosition].isNeedToLoad = false
                groupList!![groupPosition].isExpand = false
                deleteGroupChild(groupPosition)
                expandableAdapter.collapseGroup(groupPosition, false)
            } else {
                groupList!![groupPosition].isExpand = true
                groupList!![groupPosition].isNeedToLoad = true
                val count = adapter.getGroupCount() - groupPosition
                loadMoreData(groupPosition)
                adapter.notifyGroupRangeRemoved(groupPosition, count)
                expandableAdapter.expandGroup(groupPosition, false)
                smoothMoveToPosition(rvList!!, tempPosition)
            }
        }
        adapter!!.setOnChildClickListener { adapter, holder, groupPosition, childPosition -> }
        rvList!!.adapter = adapter
        stickyHeaderLayout!!.setRecyclerView(rvList)
        refresh()
    }

    private fun loadMoreData(index: Int) {
        val temp = groupList!![index].children
        val remote = GroupModel.getChild()
        val total = Integer.parseInt(groupList!![index].header.count)
        if (total > temp.size + remote.size) {
            groupList!![index].isAll = false
            temp.addAll(remote)
        } else {
            groupList!![index].isAll = true
            val childrenSize = groupList!![index].children.size
            var i = 0
            while (i < total - childrenSize && i < remote.size) {
                temp.add(remote[i])
                i++
            }
        }
        groupList!![index].children = temp
        copyToDisplay()
    }

    private fun loadMoreTime() {
        val tempGroups = ArrayList<ExpandableGroupEntity>()
        val headers = getHeader()
        for (i in headers.indices) {
            tempGroups.add(
                ExpandableGroupEntity(
                    headers[i],
                    "", false, ArrayList()
                )
            )
        }
        groupList!!.addAll(tempGroups)
        copyToDisplay()
    }

    private fun deleteGroupChild(index: Int) {
        groupList!![index].children = ArrayList()
        copyToDisplay()
    }

    private fun refresh() {
        groupList!!.clear()
        val headers = getHeader()
        for (i in headers.indices) {
            groupList!!.add(
                ExpandableGroupEntity(
                    headers[i],
                    "", false, ArrayList()
                )
            )
        }
        groupList!![0].isExpand = true
        loadMoreData(0)
        copyToDisplay()
        adapter!!.notifyDataSetChanged()
    }

    private fun copyToDisplay() {
        val tempList = ArrayList<ExpandableGroupEntity>()
        var onlyOneChildToLoad = true
        if (groupList!!.size > 0) {
            for (item in groupList!!) {
                if (!onlyOneChildToLoad) {
                    isLoadGroup = false
                    break
                } else {
                    isLoadGroup = true
                }
                if (!item.isAll && item.isNeedToLoad) {
                    onlyOneChildToLoad = false
                }
                tempList.add(item)
            }
        }

        displayList!!.clear()
        displayList!!.addAll(tempList)
//        nameList = changeDisplayListToNameList()
//        Log.d("cccccc", Gson().toJson(nameList))
//        mDecoration.setDataList(nameList)
    }

    private fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem =
            mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position)
        }
    }

    companion object {

        fun openActivity(context: Context) {
            val intent = Intent(context, SampleActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun changeDisplayListToNameList():ArrayList<String>{
        var nameList: ArrayList<String> = ArrayList()
        if(displayList != null && displayList!!.size>0) {
            for (i in 0 until displayList!!.size) {
                nameList.add(""+i)
                if(displayList!!.get(i).children.size>0) {
                    for (t in displayList!!.get(i).children) {
                        nameList.add("" + i)
                    }
                }
                nameList.add(""+i)
            }
        }
        return nameList
    }
}
