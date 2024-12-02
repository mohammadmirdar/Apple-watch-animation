package com.mirdar.applewatchanimation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class Particles(
    val circleCenterX : Int,
    val circleCenterY : Int,
    val radius : Int,
    val degreeOfDraw : Float,
    val maximumSize : Int,
    val duration : Int,
    val startTime : Long
) {
    var elapsedTime = 0L
    val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    init {
        linePaint.strokeWidth = 10f
        linePaint.style = Paint.Style.FILL
        linePaint.color = Color.RED
    }
    fun draw(canvas: Canvas) {
        val now = System.currentTimeMillis()
        elapsedTime = now - startTime
        val fraction = elapsedTime / duration

        if (fraction > 1) return

        val startX = circleCenterX + (radius * cos(degreeOfDraw))
        val startY = circleCenterY + (radius * sin(degreeOfDraw))

        val tangentSlope = ((startX - circleCenterX) / (startY - circleCenterY)) * -1

        val deltaX = maximumSize / sqrt(1 + tangentSlope.pow(2))
        val deltaY = deltaX * tangentSlope

        val endX = startX + deltaX
        val endY = startY + deltaY

        canvas.drawLine(startX, startY, endX, endY, linePaint)
    }
}