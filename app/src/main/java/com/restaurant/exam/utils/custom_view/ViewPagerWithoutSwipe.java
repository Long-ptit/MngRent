package com.restaurant.exam.utils.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class ViewPagerWithoutSwipe extends ViewPager {
    private boolean enabled = false;
    public ViewPagerWithoutSwipe(Context context) {
        super(context);
    }
    public ViewPagerWithoutSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !enabled || super.onTouchEvent(event);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled && super.onInterceptTouchEvent(event);
    }
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
