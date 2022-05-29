package com.restaurant.exam.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.restaurant.exam.utils.CommonUtils
import com.restaurant.exam.view_model.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, BINDING : ViewDataBinding> :
    Fragment() {

    lateinit var viewModel: VM

    lateinit var binding: BINDING
    lateinit var loadingDialog: ProgressDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = ProgressDialog.newInstance();
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, getContentLayout(), container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        initListener()
        observerLiveData()
    }

    abstract fun getContentLayout(): Int

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()

    protected fun paddingStatusBar(view: View){
        CommonUtils.setStatusColorIcon(view)
        view.setPadding(0, CommonUtils.getStatusBarHeight(context!!), 0, 0)
    }

    protected fun paddingBottomNavigationView(view : View){
        view.setPadding(0, 0, 0, 90)
    }
}
