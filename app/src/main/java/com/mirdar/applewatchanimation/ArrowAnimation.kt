package com.mirdar.applewatchanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

private const val TAG = "ArrowAnimation"

class ArrowAnimation(context: Context) : View(context) {
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcRect = RectF()
    val circleRadius = 10f
    private val arcRadius = 400f
    private val animDuration = 10000f
    private var elapsedTime = 0L
    private var startTime = 0L
    private var firstTime = false
    private val particles = mutableListOf<Particle>()

    init {
        setWillNotDraw(false)
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 10f
        arcPaint.color = Color.RED

        (0..359).forEach { _ ->
            particles.add(
                Particle()
            )
        }

        startTime = System.currentTimeMillis()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val circleCenterX = measuredWidth / 2
        val circleCenterY = measuredHeight / 2
        val now = System.currentTimeMillis()
        firstTime = elapsedTime == 0L

        elapsedTime = now - startTime
        val fraction = elapsedTime / animDuration

        val bottom = (measuredHeight / 2f) + arcRadius
        val right = (measuredWidth / 2f) + arcRadius
        arcRect.set(
            (measuredWidth / 2f) - arcRadius,
            (measuredHeight / 2f) - arcRadius,
            right,
            bottom
        )
        val sweepAngle = 360f * fraction
        val sweepAngleTime = sweepAngle * 2
        canvas.drawArc(
            arcRect,
            0f,
            sweepAngleTime,
            false,
            arcPaint
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

        invalidate()
    }

    private fun calculateTangentY(
        tangentSlope: Float,
        circleCenterX: Int,
        startX: Float,
        startY: Float
    ) = (tangentSlope * (circleCenterX - startX)) - startY

    // list of particles
    // each particle with the degree of drawing
    // return the slope and line formula
    // progress of 0 to 2 0-1 for view and 1-2 hiding
    // maximum size
    // duration
    // start time
}