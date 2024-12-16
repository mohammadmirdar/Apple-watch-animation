package com.mirdar.applewatchanimation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

private const val TAG = "Particle"
data class Particle(
    var circleCenterX: Int = 0,
    var circleCenterY: Int = 0,
    var radius: Int = 0,
    var degreeOfDraw: Float = 0f,
    var maximumSize: Int = (100..200).random(),
    var duration: Int = (5000..10000).random(),
    var startTime: Long = 0,
    var growthRatio : Double = Random.nextDouble(0.5, 1.0),
    var moveSize : Int = (400..700).random()
) {
    var time = 0L
    val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var isFirstDraw = true

    init {
        linePaint.strokeWidth = 4f
        linePaint.style = Paint.Style.FILL
        linePaint.color = Color.RED
    }

    fun draw(canvas: Canvas) {
        if (isFirstDraw) {
            startTime = System.currentTimeMillis()
            isFirstDraw = false
        }
        time = System.currentTimeMillis() - startTime
        val fraction = time.toFloat() / duration

        if (fraction > 1) return

        val startX = circleCenterX + (radius * cos(degreeOfDraw))
        val startY = circleCenterY + (radius * sin(degreeOfDraw))

        val tangentSlope = ((startX - circleCenterX) / (startY - circleCenterY)) * -1

        var deltaX = 0f
        var deltaY = 0f
        val sizeX = (maximumSize ) * fraction
        deltaX = sizeX / sqrt(1 + tangentSlope.pow(2))
        deltaY = deltaX * tangentSlope
        if (fraction <= 0.5) {
        } else {

        }

        val endX = if (degreeOfDraw <= Math.PI) startX - deltaX else startX + deltaX
        val endY = if (degreeOfDraw <= Math.PI) startY - deltaY else startY + deltaY
        Log.e(TAG, "this: ${this.hashCode()} elapsedTime: $time startX: $startX, startY: $startY, endX: $endX, endY: $endY fraction: $fraction")


        canvas.drawLine(startX, startY, endX, endY, linePaint)
    }
}