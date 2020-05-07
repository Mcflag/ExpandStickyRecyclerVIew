//package cn.lcsw.lcpay.ui.traderecord
//
//
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder
//import cn.bingoogolapple.refreshlayout.BGARefreshLayout
//import cn.lcsw.lcpay.R
//import cn.lcsw.lcpay.data.bean.response.TradeListItem
//import cn.lcsw.lcpay.data.remote.Status
//import cn.lcsw.lcpay.di.module.ViewModelModuleFactory
//import cn.lcsw.lcpay.event.ReceiveTradeEvent
//import cn.lcsw.lcpay.event.RefreshTradeRecordEvent
//import cn.lcsw.lcpay.ui.base.BaseFragment
//import cn.lcsw.lcpay.ui.traderecord.orderdetail.OrderDetailActivity
//import cn.lcsw.lcpay.util.Serializer
//import cn.lcsw.lcpay.widget.MultiStateView
//import cn.lcsw.lcpay.widget.pinnedheader.PinnedHeaderItemDecoration
//import com.elvishew.xlog.XLog
//import kotlinx.android.synthetic.main.fragment_trade_record.*
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import timber.log.Timber
//import javax.inject.Inject
//
//
//class TradeRecordFragment : BaseFragment(), BGARefreshLayout.BGARefreshLayoutDelegate {
//
//    @Inject
//    lateinit var factory: ViewModelModuleFactory
//
//    lateinit var viewModel: TradeRecordViewModel
//
//    @Inject
//    lateinit var serializer: Serializer
//
//    private lateinit var adapter: TradeRecordPinnedHeaderAdatper
//
//    override fun getLayoutId(): Int {
//        viewModel = ViewModelProviders.of(this, factory)[TradeRecordViewModel::class.java]
//        return R.layout.fragment_trade_record
//    }
//
//
//    companion object {
//
//        @JvmStatic
//        fun newInstance() =
//            TradeRecordFragment().apply {
//                arguments = Bundle().apply {
//                }
//            }
//    }
//
//    override fun initializeUI(savedInstanceState: Bundle?) {
//        initEmptyAndFailure()
//        initAdapter()
//        initRefreshLayout()
//    }
//
//    /**
//     * 适配器初始化
//     */
//    private fun initAdapter() {
//        rv_trade_record.layoutManager = LinearLayoutManager(context)
//        rv_trade_record.addItemDecoration(PinnedHeaderItemDecoration())
//        adapter = TradeRecordPinnedHeaderAdatper()
//        adapter.retryListener = object : TradeRecordPinnedHeaderAdatper.OnRetryListener {
//            override fun onRetry() {
//                adapter.setNetworkState(null)
//                viewModel.retry()
//            }
//        }
//        rv_trade_record.adapter = adapter
//
//        viewModel.dataList.observe(this, Observer {
//            XLog.tag("wade").d("dataList:" + it)
//            adapter.submitList(it)
//        })
//
//        adapter.onItemClickListener = object : TradeRecordPinnedHeaderAdatper.OnItemClickListener {
//            override fun onItemClick(item: TradeListItem) {
//                item.out_trade_no?.let {
//                    item.pay_type?.let { it1 ->
//                        context?.let { it2 ->
//                            OrderDetailActivity.tradeListStart(
//                                it2,
//                                it, it1
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//        viewModel.loadMoreState.observe(this, Observer {
//            if (it.status == Status.FAILED || it.status == Status.ERROR) {
//                it.msg?.let { it1 -> mToastUtil.showToast(it1) }
//            }
//            adapter.setNetworkState(it)
//        })
//
//    }
//
//    private fun showStateView(
//        viewState: MultiStateView.ViewState,
//        onClickListener: View.OnClickListener? = View.OnClickListener {
//            refresh()
//        },
//        message: String? = ""
//    ) {
//        when (viewState) {
//            MultiStateView.ViewState.CONTENT -> msv_trade_record.viewState =
//                MultiStateView.ViewState.CONTENT
//            MultiStateView.ViewState.LOADING -> {
//            }
//            MultiStateView.ViewState.ERROR -> {
//                msv_trade_record.viewState = MultiStateView.ViewState.ERROR
//                val errorView = msv_trade_record.getView(MultiStateView.ViewState.ERROR)
//                errorView?.findViewById<TextView>(R.id.tv_error_retry)
//                    ?.setOnClickListener(onClickListener)
//                errorView?.findViewById<TextView>(R.id.tv_error_message)?.text = message
//            }
//            MultiStateView.ViewState.EMPTY -> msv_trade_record.viewState =
//                MultiStateView.ViewState.EMPTY
//            MultiStateView.ViewState.FAIL -> {
//                msv_trade_record.viewState = MultiStateView.ViewState.FAIL
//                val failView = msv_trade_record.getView(MultiStateView.ViewState.FAIL)
//                failView?.findViewById<TextView>(R.id.tv_fail_message)?.text = message
//            }
//        }
//    }
//
//
//    /**
//     * 错误和空布局
//     */
//    private fun initEmptyAndFailure() {
//        viewModel.refreshStatus.observe(this, Observer {
//            when (it.status) {
//                Status.FAILED -> {
//                    showStateView(MultiStateView.ViewState.FAIL, message = it.msg)
//                }
//                Status.ERROR -> {
//                    showStateView(MultiStateView.ViewState.ERROR, message = it.msg)
//                }
//                Status.EMPTY -> {
//                    showStateView(MultiStateView.ViewState.EMPTY)
//                }
//                else -> showStateView(MultiStateView.ViewState.CONTENT)
//            }
//        })
//    }
//
//    /**
//     * 加载数据
//     */
//    override fun loadData() {
//        viewModel.getData()
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEventBusMessage(event: ReceiveTradeEvent) {
//        if (rv_trade_record.computeVerticalScrollOffset() == 0) {
//            refresh()
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEventBusMessage(event: RefreshTradeRecordEvent) {
//        refresh()
//    }
//
//    private fun refresh() {
//        adapter.setNetworkState(null)
//        viewModel.refresh()
//    }
//
//    fun storeFilter(storeId: String){
//        adapter.setNetworkState(null)
//        viewModel.storeFilter(
//            storeId
//        )
//    }
//
//    private fun initRefreshLayout() {
//        //设置代理
//        trade_record_refresh.setDelegate(this)
//
//        val refreshViewHolder = RefreshViewHolder(context, false)
//        refreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.refresh)
//        refreshViewHolder.setRefreshingAnimResId(R.drawable.refresh)
//        trade_record_refresh.setRefreshViewHolder(refreshViewHolder)
//
//        viewModel.refreshState.observe(this, Observer {
//            if (it.status == Status.FAILED || it.status == Status.ERROR) {
//                it.msg?.let { it1 -> mToastUtil.showToast(it1) }
//            }
//            if (it.status == Status.LOADING){
//                trade_record_refresh.beginRefreshing()
//            }else{
//                trade_record_refresh.endRefreshing()
//            }
//        })
//
//    }
//
//    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
//        //加载更多数据
//        return false
//    }
//
//    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
//        //加载最新数据
//        refresh()
//    }
//}
