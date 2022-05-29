package com.restaurant.exam.ui.news

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import restaurant.exam.R
import com.restaurant.exam.base.BaseFragment
import com.restaurant.exam.data.model.BannerResponse
import com.restaurant.exam.data.model.NewResponse
import restaurant.exam.databinding.FragmentNewsBinding
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.utils.CommonUtils
import com.restaurant.exam.view_model.ViewModelFactory
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.indicator.enums.IndicatorSlideMode

class NewsFragment : BaseFragment<HomeViewModel, FragmentNewsBinding>() {
    private val listBanner : ArrayList<BannerResponse> = ArrayList()

    override fun getContentLayout(): Int {
        return R.layout.fragment_news
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[HomeViewModel::class.java]

    }

    override fun initView() {
        listBanner.clear()
        listBanner.add(BannerResponse())
        listBanner.add(BannerResponse())
        listBanner.add(BannerResponse())
        listBanner.add(BannerResponse())
        listBanner.add(BannerResponse())
        listBanner.add(BannerResponse())

        val mViewPager = binding.banner as BannerViewPager<BannerResponse>
        mViewPager.apply {
            setCanLoop(true)
            setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp10))
            setRevealWidth(resources.getDimensionPixelOffset(R.dimen.dp10), resources.getDimensionPixelOffset(R.dimen.dp10))
            setScrollDuration(800)
            setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            adapter = BannerAdapter(resources.getDimensionPixelOffset(R.dimen.dp10))
            create(listBanner)
        }

        val manager = childFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayoutchangedc, FragmentNewsList())
        transaction.commit()


    }

    override fun initListener() {

    }

    override fun observerLiveData() {

    }

}