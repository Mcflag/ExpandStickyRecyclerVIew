package cn.lcsw.lcpay.ui.traderecord

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.lcsw.lcpay.R
import cn.lcsw.lcpay.data.bean.response.TradeListItem
import cn.lcsw.lcpay.data.remote.NetworkState
import cn.lcsw.lcpay.data.remote.Status
import cn.lcsw.lcpay.extensions.fen2YuanString
import cn.lcsw.lcpay.extensions.payStatusCodeDesc
import cn.lcsw.lcpay.extensions.payTypeListIcon
import cn.lcsw.lcpay.widget.pinnedheader.PinnedHeaderAdapter
import com.elvishew.xlog.XLog

class TradeRecordPinnedHeaderAdatper :
    PinnedHeaderAdapter<TradeRecordSection, RecyclerView.ViewHolder>(
        COMPARATOR
    ) {

    var retryListener: OnRetryListener? = null
    var onItemClickListener: OnItemClickListener? = null
    private var loadMoreState: NetworkState? = null

    private val VIEW_TYPE_ITEM_HEADER = 0
    private val VIEW_TYPE_ITEM_CONTENT = 1
    private val VIEW_TYPE_ITEM_LOADMORE = 2

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<TradeRecordSection>() {
            override fun areItemsTheSame(
                oldItem: TradeRecordSection,
                newItem: TradeRecordSection
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TradeRecordSection,
                newItem: TradeRecordSection
            ): Boolean = false

        }
    }


    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) {
            return VIEW_TYPE_ITEM_LOADMORE
        }
        return if (getItem(position)!!.isHeader) VIEW_TYPE_ITEM_HEADER else VIEW_TYPE_ITEM_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM_HEADER) {
            HeaderHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_trade_record_header,
                    parent,
                    false
                )
            )
        } else if (viewType == VIEW_TYPE_ITEM_CONTENT) {
            ContentHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_trade_record_item,
                    parent,
                    false
                )
            )
        } else {
            LoadmoreHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.page_loadmore,
                    parent,
                    false
                )
            )
        }
    }

    override fun isPinnedPosition(position: Int): Boolean {
        if (position == -1) return false
        return getItemViewType(position) == VIEW_TYPE_ITEM_HEADER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_HEADER) {
            val item = getItem(position)
            val titleHolder = holder as HeaderHolder
            titleHolder.tvDate.text = item?.tradeSummaryListItem?.trade_date.toString()
            titleHolder.tvCount.text = item?.tradeSummaryListItem?.date_trade_count.toString()
            titleHolder.tvNum.text =
                "+" + item?.tradeSummaryListItem?.date_trade_num.toString().fen2YuanString()
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_CONTENT) {
            val item = getItem(position)
            val contentHolder = holder as ContentHolder
            contentHolder.tvResult.text = item?.t?.pay_status_code.toString().payStatusCodeDesc()

            when(item?.t?.pay_status_code) {
                "1" -> {
                    contentHolder.tvSum.text = "+" + item?.t?.total_fee.toString().fen2YuanString()
                }
                "5" -> {
                    contentHolder.tvSum.text = "-" + item?.t?.refund_fee.toString().fen2YuanString()
                }
                else -> {
                    contentHolder.tvSum.text = item?.t?.total_fee.toString().fen2YuanString()
                }
            }

            contentHolder.tvTime.text = item?.t?.settle_time.toString()
            item?.t?.pay_type?.payTypeListIcon()?.let {
                contentHolder.ivType.setImageResource(
                    it
                )
            }
            contentHolder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(item!!.t)
            }
            if ("1" == item?.t?.is_have_discount) {
                contentHolder.state.text = "有优惠"
                contentHolder.state.setTextColor(Color.parseColor("#FFFD670D"))
                contentHolder.state.visibility = View.VISIBLE
            }
            if ("1" == item?.t?.is_have_refund) {
                contentHolder.state.text = "有退款"
                contentHolder.state.setTextColor(Color.parseColor("#FF15B58B"))
                contentHolder.state.visibility = View.VISIBLE
            }
            if ("0" == item?.t?.is_have_discount && "0" == item?.t?.is_have_refund) {
                contentHolder.state.visibility = View.GONE
            }
            if (item?.isLastOfDay!!) {
                contentHolder.vDivider.visibility = View.INVISIBLE
            } else {
                contentHolder.vDivider.visibility = View.VISIBLE
            }
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_LOADMORE) {
            loadMoreState?.let {
                val loadmoreHolder = holder as LoadmoreHolder
                loadmoreHolder.itemView.setOnClickListener {
                    retryListener?.onRetry()
                }

                if (it.status == Status.LOADING) {
                    loadmoreHolder.pbLoadmore.visibility = View.VISIBLE
                } else {
                    loadmoreHolder.pbLoadmore.visibility = View.GONE
                }
                if (it.status == Status.FAILED || it.status == Status.ERROR) {
                    loadmoreHolder.tvLoadmore.visibility = View.VISIBLE
                } else {
                    loadmoreHolder.tvLoadmore.visibility = View.GONE
                }
                if (it.status == Status.LASTPAGE) {
                    loadmoreHolder.tvLoadmore.visibility = View.VISIBLE
                    loadmoreHolder.tvLoadmore.text = "已经全部加载完毕"
                    loadmoreHolder.tvLoadmore.textSize = 15f
                    loadmoreHolder.tvLoadmore.setTextColor(
                        holder.itemView.context.resources.getColor(
                            R.color.black_666666
                        )
                    )
                    loadmoreHolder.itemView.setOnClickListener {
                    }
                }
            }
        }
    }

    internal class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvSum: TextView = itemView.findViewById(R.id.tv_trade_record_item_sum)
        var tvResult: TextView = itemView.findViewById(R.id.tv_trade_record_item_result)
        var tvTime: TextView = itemView.findViewById(R.id.tv_trade_record_item_time)
        var state: TextView = itemView.findViewById(R.id.tv_trade_record_item_state)
        var ivType: ImageView = itemView.findViewById(R.id.iv_trade_record_item_type)
        var vDivider: View = itemView.findViewById(R.id.v_trade_record_item_divider)

    }

    internal class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDate: TextView = itemView.findViewById(R.id.tv_trade_record_header_date)
        var tvCount: TextView = itemView.findViewById(R.id.tv_trade_record_header_count)
        var tvNum: TextView = itemView.findViewById(R.id.tv_trade_record_header_num)

    }

    internal class LoadmoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pbLoadmore: ProgressBar = itemView.findViewById(R.id.pb_loadmore)
        var tvLoadmore: TextView = itemView.findViewById(R.id.tv_loadmore)
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        XLog.tag("wade").d("setNetworkState:" + newNetworkState)
        val previousState = this.loadMoreState
        val hadExtraRow = hasExtraRow()
        this.loadMoreState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
        if (newNetworkState?.status == Status.LASTPAGE) {
            notifyItemInserted(itemCount - 1)
        }
    }

    interface OnRetryListener {
        fun onRetry()
    }

    interface OnItemClickListener {
        fun onItemClick(item: TradeListItem)
    }

    private fun hasExtraRow() = loadMoreState != null && loadMoreState != NetworkState.SUCCESS

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }
}