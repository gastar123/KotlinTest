package com.example.magnittest

import android.content.Context
import android.util.AttributeSet

class ScrollingFABBottomBehavior(context: Context, attrs: AttributeSet) : AbsScrollingFABBehavior(context, attrs) {
    override fun getFABHideAnimationRes(): Int = R.anim.behavior_bottom_out

    override fun getFABShowAnimationRes(): Int = R.anim.behavior_bottom_in
}