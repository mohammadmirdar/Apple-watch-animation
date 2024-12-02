package com.mirdar.applewatchanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

private const val TAG = "ArrowAnimation"

class ArrowAnimation(context: Context) : View(context) {
    val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val arcRect = RectF()
    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val circleRadius = 10f
    val arcRadius = 100f
    val animDuration = 5000f
    var elapsedTime = 0L
    var startTime = 0L
    var firstTime = false

    init {
        setWillNotDraw(false)
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 10f
        arcPaint.color = Color.BLUE

        circlePaint.style = Paint.Style.FILL
        circlePaint.color = Color.RED
        circlePaint.strokeWidth = 10f

        startTime = System.currentTimeMillis()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val now = System.currentTimeMillis()
        firstTime = elapsedTime == 0L
        elapsedTime = now - startTime
        val fraction = elapsedTime / animDuration

        val bottom = (measuredHeight / 2f) + arcRadius
        var right = (measuredWidth / 2f) + arcRadius
        arcRect.set(
            (measuredWidth / 2f) - arcRadius,
            (measuredHeight / 2f) - arcRadius,
            right,
            bottom
        )
        val sweepAngle = 360f * fraction * 2
        canvas.drawArc(
            arcRect,
            0f,
            sweepAngle,
            false,
            arcPaint
        )
        val circleCenterX = measuredWidth / 2
        val circleCenterY = measuredHeight / 2
        var startX = circleCenterX + (arcRadius * cos(0f))
        var startY = circleCenterY + (arcRadius * sin(0f))

        val tangentSlope = ((startX - circleCenterX) / (startY - circleCenterY)) * -1
        var y1 = calculateTangentY(tangentSlope, circleCenterX, startX, startY)
//        var y2 = calculateTangentY(tangentSlope, circleCenterX, )
        startY += (fraction * 10).pow(2)
        canvas.drawLine(
            startX.toFloat(),
            startY,
            (startX - 15f).toFloat(),
            startY + 40f,
            circlePaint
        )


        if (fraction > 1f) {
            return
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