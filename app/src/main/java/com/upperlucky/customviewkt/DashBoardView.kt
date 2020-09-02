package com.upperlucky.customviewkt

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * created by yunKun.wen on 2020/9/2
 * desc: 仪表盘
 */

private const val DASH_ANGLE = 120f
private val RADIUS = 150f.px
private val LINE_LENGTH = 120f.px
private val DASH_WIDTH = 2f.px // 仪表盘上刻度
private val DASH_HEIGHT = 10f.px
private const val DASH_NUMBER = 20 // 仪表盘上的刻度数

class DashBoardView(context: Context?, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dash = Path()
    private var currentDash = 10

    private lateinit var pathEffect: PathDashPathEffect
    private val pathMeasure = PathMeasure()

    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        dash.addRect(0f, DASH_HEIGHT, DASH_WIDTH, 0f, Path.Direction.CW)
        path.addArc(
            width / 2f - RADIUS,
            height / 2 - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + DASH_ANGLE / 2,
            360f - DASH_ANGLE
        )

        pathMeasure.setPath(path, false)
        pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - DASH_WIDTH) / DASH_NUMBER,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画弧
        canvas.drawPath(path, paint)

        // 画刻度
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        // 某个刻度所对应的角度就等于起始弧点的角度加上（弧总共的角度 / 总刻度数）* 第几个刻度
        var angle =
            Math.toRadians((90 + DASH_ANGLE / 2 + ((360 - DASH_ANGLE) / DASH_NUMBER) * currentDash).toDouble())
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LINE_LENGTH * cos(angle.toFloat()),
            height / 2 + LINE_LENGTH * sin(angle.toFloat()),
            paint
        )

    }
}