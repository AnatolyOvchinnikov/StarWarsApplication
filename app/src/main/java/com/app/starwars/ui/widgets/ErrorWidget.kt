package com.app.starwars.ui.widgets

import android.content.Context
import android.support.transition.ChangeBounds
import android.support.transition.Slide
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.app.starwars.R
import kotlinx.android.synthetic.main.error_layout.view.*

class ErrorWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.error_layout, this)
        bannerClose.setOnClickListener {
            hideNoConnectionBanner()
        }
    }

    fun showContainer(showed: Boolean) {
        if(showed)
            showNoConnectionBanner()
        else
            hideNoConnectionBanner()
    }

    fun showNoConnectionBanner() {
        TransitionManager.beginDelayedTransition(rootContainer,
            BannerTransition()
        )
        banner.visibility = View.VISIBLE
    }

    fun hideNoConnectionBanner() {
        TransitionManager.beginDelayedTransition(rootContainer,
            BannerTransition()
        )
        banner.visibility = View.GONE
    }

    class BannerTransition : TransitionSet() {
        init {
            init()
        }

        private fun init() {
            ordering = TransitionSet.ORDERING_TOGETHER

            addTransition(ChangeBounds())
                .addTransition(Slide(Gravity.TOP))
        }
    }
}

