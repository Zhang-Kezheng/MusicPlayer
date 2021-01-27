package com.example.musicplayer.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author 章可政
 * @date 2020/10/28 21:14
 */
class CustomPageView : ViewPager {
    var isScroll = false

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (isScroll) {
            false
        } else {
            super.onTouchEvent(arg0)
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (isScroll) {
            false
        } else {
            super.onInterceptTouchEvent(arg0)
        }
    }
}