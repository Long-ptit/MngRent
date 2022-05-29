package com.restaurant.exam.utils.custom_view

import android.os.Bundle
import android.view.View
import restaurant.exam.R
import com.restaurant.exam.base.BaseDialog
import restaurant.exam.databinding.LayoutErrorDialogBinding

class ErrorDialog (errorString : String) : BaseDialog<LayoutErrorDialogBinding>() {
    val errorShow = errorString

    override fun getLayoutResource(): Int {
        return R.layout.layout_error_dialog
    }

    override fun init(saveInstanceState: Bundle?, view: View?) {
    }

    override fun setUp(view: View?) {
        binding.tvError.text = errorShow
    }
}