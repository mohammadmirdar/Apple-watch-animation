package com.mirdar.applewatchanimation

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val TAG = "Particle"

data class Particle(
    var circleCenterX: Int = 0,
    var circleCenterY: Int = 0,
    var radius: Int = 0,
    var degreeOfDraw: Float = 0f,
    var mainAnimationDuration: Int,
    var maximumSize: Int = (20..30).random(),
    var duration: Int = (500..1500).random(),
    var startTime: Long = 0,
    var moveSize: Int = (400..700).random()
) {
    private var time = 0L
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val lineGlowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isFirstDraw = true
    private var sumOfDuration = duration

    init {
        linePaint.strokeWidth = 4f
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.color = Color.WHITE

        lineGlowPaint.strokeWidth = 8f
        lineGlowPaint.style = Paint.Style.STROKE
        lineGlowPaint.strokeCap = Paint.Cap.ROUND
        lineGlowPaint.color = Color.RED
        lineGlowPaint.maskFilter = BlurMaskFilter(14f, BlurMaskFilter.Blur.NORMAL)
    }

    fun draw(canvas: Canvas) {
        if (isFirstDraw) {
            startTime = System.currentTimeMillis()
            isFirstDraw = false
        }
        time = System.currentTimeMillis() - startTime
        val fraction = time.toFloat() / duration

        if (fraction > 1) {
            resetParticle()
            return
        }

        val startX = circleCenterX + (radius * cos(degreeOfDraw))
        val startY = circleCenterY + (radius * sin(degreeOfDraw))

        val tangentSlope = ((startX - circleCenterX) / (startY - circleCenterY)) * -1

        var deltaX = 0f
        var deltaY = 0f

        val sizeX = (maximumSize) * fraction * 2
        deltaX = sizeX / sqrt(1 + tangentSlope.pow(2))
        deltaY = deltaX * tangentSlope

        val endX = if (degreeOfDraw <= Math.PI) startX - deltaX else startX + deltaX
        val endY = if (degreeOfDraw <= Math.PI) startY - deltaY else startY + deltaY

        val newStartX: Float
        val newStartY: Float
        val newEndX: Float
        val newEndY: Float
        if (fraction > 0.1) {
            val moveX = moveSize * (fraction - 0.1f) / sqrt(1 + tangentSlope.pow(2))
            val moveY = moveX * tangentSlope

            newStartX = if (degreeOfDraw <= Math.PI) startX - moveX else startX + moveX
            newStartY = if (degreeOfDraw <= Math.PI) startY - moveY else startY + moveY
            newEndX = if (degreeOfDraw <= Math.PI) endX - moveX else endX + moveX
            newEndY = if (degreeOfDraw <= Math.PI) endY - moveY else endY + moveY
            if (fraction > 0.7) {
                linePaint.alpha = (255 - (fraction - 0.7f) * 255).toInt()
            }

        } else {
            newStartX = startX
            newStartY = startY
            newEndX = endX
            newEndY = endY
        }

        Log.e(
            TAG,
            "this: ${this.hashCode()} elapsedTime: $time startX: $startX, startY: $startY, endX: $endX, endY: $endY fraction: $fraction"
        )


        canvas.drawLine(newStartX, newStartY, newEndX, newEndY, lineGlowPaint)
        canvas.drawLine(newStartX, newStartY, newEndX, newEndY, linePaint)
    }

    private fun resetParticle() {
        maximumSize = (20..30).random()
        duration = getNewDuration()
        moveSize = (400..700).random()
        time = 0
        isFirstDraw = true
    }

    private fun getNewDuration(): Int {
        return if (sumOfDuration < mainAnimationDuration) {
            val randomDuration = (500..1500).random()
            sumOfDuration += randomDuration
            if (sumOfDuration < mainAnimationDuration) {
                randomDuration
            } else {
                0
            }
        } else {
            0
        }
    }
}