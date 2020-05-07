//package cn.lcsw.lcpay.ui.traderecord
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
//import cn.lcsw.lcpay.repository.BaseRepository
//import cn.lcsw.lcpay.repository.TradeRecordPagingRepository
//import cn.lcsw.lcpay.ui.base.BaseViewModel
//import javax.inject.Inject
//
///**
// * Created by zxk on 2019-07-15.
// * Description:登录界面的ViewModel
// */
//class TradeRecordViewModel @Inject constructor(
//    private val tradeRecordPagingRepository: TradeRecordPagingRepository
//) : BaseViewModel() {
//
//    val result = MutableLiveData<TradeRecordListing<TradeRecordSection>>()
//
//    //列表数据
//    val dataList = Transformations.switchMap(result) {
//        it.pagedList
//    }
//
//    //下拉刷新状态
//    val refreshState = Transformations.switchMap(result) {
//        it.refreshState
//    }
//
//    val refreshStatus = Transformations.map(refreshState) {
//        it
//    }
//
//    //加载更多状态
//    val loadMoreState = Transformations.switchMap(result) {
//        it.loadMoreState
//    }
//
//    override fun getRepository(): ArrayList<out BaseRepository?> {
//        return arrayListOf(tradeRecordPagingRepository)
//    }
//
//    /**
//     * 开始查列表数据
//     */
//    fun getData() {
//        result.value = tradeRecordPagingRepository.getTradeRecordPagingData()
//    }
//
//    /**
//     * 下拉刷新
//     */
//    fun refresh() {
//        result.value?.refresh?.invoke()
//    }
//
//    /**
//     * 重试
//     */
//    fun retry() {
//        result.value?.retry?.invoke()
//    }
//
//    fun storeFilter(storeId: String) {
//        result.value?.filterStore?.invoke(storeId)
//    }
//}