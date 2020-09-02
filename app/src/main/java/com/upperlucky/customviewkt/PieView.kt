package com.upperlucky.customviewkt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * created by yunKun.wen on 2020/9/2
 * desc: 饼图
 */

private val RADIUS = 150f.px
private val OFFSET_LENGTH = 26f.px  // 需要偏移的距离
private var OFFSET_INDEX = 2 // 需要偏移的图形下标

class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val colors = listOf(Color.BLUE, Color.CYAN, Color.GREEN, Color.RED)
    private val angels = floatArrayOf(60f, 90f, 150f, 60f)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in angels.withIndex()) {
            paint.color = colors[index]
            // 直接偏移canvas
            if (index == OFFSET_INDEX) {
                canvas.save()
                canvas.translate(
                    (OFFSET_LENGTH * cos(Math.toRadians((startAngle + angle / 2).toDouble()))).toFloat(),
                    OFFSET_LENGTH * sin(Math.toRadians((startAngle + angle / 2).toDouble())).toFloat()
                )
            }
            canvas.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2 + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            if (index == OFFSET_INDEX) {
                canvas.restore()
            }
            startAngle += angle
        }
    }
}