package com.mirdar.applewatchanimation

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.AccelerateInterpolator

data class RingAnimation(
    private val color : Int,
    private val animDuration : Int,
    private val arcRadius : Float
) {
    var measuredHeight = 0
    var measuredWidth = 0
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcGlowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcRect = RectF()
    private var elapsedTime = 0L
    private var startTime = 0L
    private var firstTime = false
    private val particles = mutableListOf<Particle>()
    private val interpolator = AccelerateInterpolator()

    init {
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 10f
        arcPaint.color = color
        arcPaint.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.INNER)

        arcGlowPaint.style = Paint.Style.STROKE
        arcGlowPaint.strokeWidth = 52f
        arcGlowPaint.color = color
        arcGlowPaint.maskFilter = BlurMaskFilter(68f, BlurMaskFilter.Blur.NORMAL)

        (0..359).forEach { _ ->
            particles.add(
                Particle(mainAnimationDuration = animDuration, color = color)
            )
        }

        startTime = System.currentTimeMillis()
    }

    fun draw(canvas: Canvas) {
        val circleCenterX = measuredWidth / 2
        val circleCenterY = measuredHeight / 2
        val now = System.currentTimeMillis()
        firstTime = elapsedTime == 0L

        elapsedTime = now - startTime
        val fraction = elapsedTime / animDuration.toFloat()

        val bottom = (measuredHeight / 2f) + arcRadius
        val right = (measuredWidth / 2f) + arcRadius
        arcRect.set(
            (measuredWidth / 2f) - arcRadius,
            (measuredHeight / 2f) - arcRadius,
            right,
            bottom
        )

        val sweepAngle = 360f * interpolator.getInterpolation(fraction)
        val sweepAngleTime = sweepAngle * 2
        canvas.drawArc(
            arcRect,
            0f,
            sweepAngleTime,
            false,
            arcPaint
        )
        canvas.drawArc(
            arcRect,
            0f,
            sweepAngleTime,
            false,
            arcGlowPaint
        )
        if (firstTime) {
            particles.forEachIndexed { index, particle ->
                particle.circleCenterX = circleCenterX
                particle.circleCenterY = circleCenterY
                particle.radius = arcRadius.toInt()
                particle.degreeOfDraw = Math.toRadians(index.toDouble()).toFloat()
            }
        }

        particles.forEachIndexed { index, particle ->
            if (index < sweepAngle * 2) {
                particle.draw(canvas)
            }
        }
    }
}