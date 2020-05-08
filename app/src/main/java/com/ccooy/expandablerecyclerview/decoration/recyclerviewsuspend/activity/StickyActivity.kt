//package com.ccooy.expandablerecyclerview.decoration.recyclerviewsuspend.activity
//
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//
//import com.rmyh.recyclerviewsuspend.ItemDecoration.SectionDecoration
//import com.rmyh.recyclerviewsuspend.R
//
//import java.util.ArrayList
//
//import butterknife.ButterKnife
//import butterknife.InjectView
//
//class StickyActivity : AppCompatActivity() {
//
//    @InjectView(R.id.text_recycler)
//    internal var textRecycler: RecyclerView? = null
//    private val list = ArrayList<String>()
//    private val NameBean = ArrayList<String>()
//
//    protected fun onCreate(savedInstanceState: Bundle) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        ButterKnife.inject(this)
//        getSupportActionBar().hide()
//        initData()
//
//        textRecycler!!.setLayoutManager(LinearLayoutManager(this))
//        val adapter = RecyclerAdapter(list)
//        textRecycler!!.addItemDecoration(
//            SectionDecoration(
//                list,
//                this,
//                object : SectionDecoration.DecorationCallback() {
//                    fun getGroupId(position: Int): String {
//                        return if (NameBean[position] != null) {
//                            NameBean[position]
//                        } else "-1"
//                    }
//
//                    fun getGroupFirstLine(position: Int): String {
//                        return if (NameBean[position] != null) {
//                            NameBean[position]
//                        } else ""
//                    }
//                })
//        )
//        textRecycler!!.setAdapter(adapter)
//    }
//
//    private fun initData() {
//        list.clear()
//        NameBean.clear()
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//        NameBean.add("111111111")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("222222222")
//        NameBean.add("333333333")
//        NameBean.add("333333333")
//        NameBean.add("333333333")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//        NameBean.add("444444444")
//    }
//}
