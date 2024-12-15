package com.mirdar.applewatchanimation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
    var duration: Int = (1000..3000).random(),
    var startTime: Long = 0,
    var growthRatio : Double = Random.nextDouble(0.5, 1.0)
) {
    var time = 0f
    val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        linePaint.strokeWidth = 4f
        linePaint.style = Paint.Style.FILL
        linePaint.color = Color.RED
    }

    fun draw(canvas: Canvas, elapsedTime : Long) {
        time += elapsedTime
        val fraction = elapsedTime.toFloat() / duration

        Log.e(TAG, "elapsedTime: $elapsedTime time: $time duration: $duration fraction: $fraction")
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


        canvas.drawLine(startX, startY, endX, endY, linePaint)
    }
}