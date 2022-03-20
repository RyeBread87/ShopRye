package com.application.shoprye

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout

class ScrollingOffsetFixListener(
    private val nestedScrollView: ViewPager2
): AppBarLayout.OnOffsetChangedListener {

    private var originalHeight = 0
    private var firstOffset = true

    override fun onOffsetChanged(layout: AppBarLayout?, offset: Int) {
        if(firstOffset) {
            firstOffset = false
            originalHeight = nestedScrollView.measuredHeight
        }

        val params = nestedScrollView.layoutParams
        params.height = originalHeight + (offset * -1)
        nestedScrollView.layoutParams = params
    }
}