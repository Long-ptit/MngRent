package com.restaurant.exam.ui.intro

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import restaurant.exam.R


class OnboardAD(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardFM.newInstance(
                context.resources.getString(R.string.str_intro1),
                context.resources.getString(R.string.str_intr02),
                context.resources.getString(R.string.str_intro1),
                R.drawable.img1
            )
            1 -> OnboardFM.newInstance(
                context.resources.getString(R.string.str_intro3),
                context.resources.getString(R.string.str_intro4),
                context.resources.getString(R.string.str_intro1),
                R.drawable.img2
            )
            else -> OnboardFM.newInstance(
                context.resources.getString(R.string.str_intro5),
                context.resources.getString(R.string.str_intro6),
                context.resources.getString(R.string.str_intro1),
                R.drawable.img3
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}