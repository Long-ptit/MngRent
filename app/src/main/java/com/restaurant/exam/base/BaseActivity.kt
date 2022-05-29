package com.restaurant.exam.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.restaurant.exam.utils.CommonUtils
import com.restaurant.exam.utils.KeyboardUtils.hideKeyboard
import com.restaurant.exam.utils.custom_view.ErrorDialog
import com.restaurant.exam.view_model.BaseViewModel
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity<VM : BaseViewModel, BINDING : ViewDataBinding> :
    AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: BINDING
    lateinit var loadingDialog: ProgressDialog
    lateinit var errorDialog: ErrorDialog

    val DOUBLE_PRESS_INTERVAL: Long = 1000
    private var mLastClickTime: Long = 0
    var showError = false
    var isSplash = false

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, getContentLayout())
        setContentView(binding.root)
        loadingDialog = ProgressDialog.newInstance();
        initViewModel()
        observerDefaultLiveData()
        initView()
        initListener()
        observerLiveData()
    }

    fun transparentStatusbar2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = 0x00000000
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    abstract fun getContentLayout(): Int

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()

    open fun isDoubleClick(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < DOUBLE_PRESS_INTERVAL) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    private fun observerDefaultLiveData() {
        viewModel.apply {
            isLoading.observe(this@BaseActivity, Observer {
                if(!isSplash){
                    if (it) {
                        if (!loadingDialog.isAdded)
                            loadingDialog.show(supportFragmentManager, null)
                    } else {
                        loadingDialog.dismiss()
                    }
                }

            })
            errorMessage.observe(this@BaseActivity, Observer {
                if (it != null) {
                    showError(it.toInt())
                }
            })
            responseMessage.observe(this@BaseActivity, Observer {
                showError(it.toString())
            })
        }

    }

    fun showError(errorMessage: String) {

        if(showError){
            if(loadingDialog.isAdded)
                loadingDialog.dismiss()
            errorDialog = ErrorDialog(errorMessage)
            errorDialog.show(supportFragmentManager, "error_dialog")
        }
        else{
            val errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
            errorSnackbar.setAction("", null)
            errorSnackbar.show()
        }
    }

    private fun showError(@StringRes id: Int) {
        var errorSnackbar = Snackbar.make(binding.root, id, Snackbar.LENGTH_LONG)
        errorSnackbar.setAction("", null)
        errorSnackbar.show()
    }

    protected fun paddingStatusBar(view: View) {
        view.setPadding(0, CommonUtils.getStatusBarHeight(this), 0, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard(this)
    }
}
