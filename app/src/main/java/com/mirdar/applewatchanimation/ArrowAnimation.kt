package com.mirdar.applewatchanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

private const val TAG = "ArrowAnimation"

class ArrowAnimation(context: Context) : View(context) {

    private val redRingAnimation: RingAnimation = RingAnimation(Color.parseColor("#C227DE"), 10000, 400f)
    private val greenRingAnimation: RingAnimation = RingAnimation(Color.parseColor("#8BC34A"), 10000, 300f)
    private val yellowRingAnimation: RingAnimation = RingAnimation(Color.parseColor("#1EABE9"), 10000, 200f)

    init {
        setWillNotDraw(false)
    }

    fun runAnimation() {
        invalidate()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        redRingAnimation.measuredHeight = measuredHeight
        redRingAnimation.measuredWidth = measuredWidth
        greenRingAnimation.measuredHeight = measuredHeight
        greenRingAnimation.measuredWidth = measuredWidth
        yellowRingAnimation.measuredHeight = measuredHeight
        yellowRingAnimation.measuredWidth = measuredWidth
        redRingAnimation.draw(canvas)
        greenRingAnimation.draw(canvas)
        yellowRingAnimation.draw(canvas)
        invalidate()
    }

}