package com.example.magnittest.behavior

import android.content.Context
import android.util.AttributeSet
import com.example.magnittest.R

class ScrollingFABBottomBehavior(context: Context, attrs: AttributeSet) : ScrollingFABBehavior(context, attrs) {
    override fun getFABHideAnimationRes(): Int = R.anim.behavior_bottom_out

    override fun getFABShowAnimationRes(): Int = R.anim.behavior_bottom_in
}