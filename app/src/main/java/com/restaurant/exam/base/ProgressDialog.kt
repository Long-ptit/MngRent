package com.restaurant.exam.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import restaurant.exam.R

class ProgressDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_error_dialog, container, false)
    }

    companion object {
        fun newInstance() =
            ProgressDialog().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
