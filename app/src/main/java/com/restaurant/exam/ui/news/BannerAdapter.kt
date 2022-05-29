package com.restaurant.exam.ui.news

import com.restaurant.exam.data.model.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import kotlinx.android.synthetic.main.item_banner.view.*
import restaurant.exam.R


class BannerAdapter(private val corner : Int) : BaseBannerAdapter<BannerResponse>() {
    override fun bindData(
        holder: BaseViewHolder<BannerResponse>,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder.itemView.img_banner.setRoundCorner(corner)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }
}