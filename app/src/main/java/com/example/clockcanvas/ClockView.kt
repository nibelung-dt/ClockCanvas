package com.example.clockcanvas

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class ClockView(context: Context, val attr: AttributeSet): View(context, attr) {

    private var radius = 0
    private var centreX = 0
    private var centreY = 0
    private val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    private var isInit = false
    private lateinit var paint: Paint
    private var path: Path? = null // что это?
    private lateinit var rect: Rect

    private var secondArrowColor = 0
    private var minuteArrowColor = 0
    private var hourArrowColor = 0

    private var padding = 0
    private var fontSize = 0
    private val numeralSpacing = 0

/*
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
 */

    private fun init() {
        centreX = width/2
        centreY = height/2
        padding = numeralSpacing + 50

        val minimum = min(height, width)
        radius = minimum / 2 - padding

        fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20F, resources.displayMetrics).toInt()

        paint = Paint()
        path = Path()
        rect = Rect()
        isInit = true

        val taped: TypedArray = context.obtainStyledAttributes(attr, R.styleable.ClockView)
        secondArrowColor = taped.getColor(R.styleable.ClockView_second_arrow_color, Color.BLUE)
        minuteArrowColor = taped.getColor(R.styleable.ClockView_minute_arrow_color, Color.RED)
        hourArrowColor = taped.getColor(R.styleable.ClockView_hour_arrow_color, Color.BLACK)
    }

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            init()
        }
        drawCircle(canvas)
        drawNumbers(canvas)
        drawHands(canvas!!)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawCircle(canvas: Canvas?){
        paint.reset()
        paint.color = Color.BLACK
        paint.strokeWidth = 5F
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        canvas?.drawCircle((centreX).toFloat(), (centreY).toFloat(),
            (radius + padding - 10).toFloat(), paint)
    }

    private fun drawNumbers(canvas: Canvas?) {
        paint.textSize = fontSize.toFloat()

        for (number in numbers){
            val tmp = number.toString()
            paint.getTextBounds(tmp,0,tmp.length,rect)
            val angle = Math.PI/6 * (number-3)
            val x = ((centreX + cos(angle) * radius - rect.width() / 2).toFloat())
            val y = ((centreY + sin(angle) * radius + rect.height() / 2).toFloat())
            canvas?.drawText(tmp,x,y, paint)
        }
    }

    private fun drawHands(canvas: Canvas){
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) hour - 12 else hour
        paint.color = hourArrowColor
        drawHand(canvas, ((hour + calendar.get(Calendar.MINUTE) / 60) * 5f), "hour")
        paint.color = secondArrowColor
        drawHand(canvas, calendar.get(Calendar.MINUTE).toFloat(), "minute")
        paint.color = minuteArrowColor
        drawHand(canvas, calendar.get(Calendar.SECOND).toFloat(), "second")
    }

    private fun drawHand(canvas: Canvas?, loc: Float, typeOfHand: String){
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = when(typeOfHand) {
            "hour" -> (radius / 1.5).toFloat()
            "minute" -> (radius / 1.85).toFloat()
            else -> radius
        }

        canvas!!.drawLine((centreX).toFloat(), (centreY).toFloat(),
            (centreX+cos(angle)*handRadius.toFloat()).toFloat(), (centreY+ sin(angle)*handRadius.toFloat()).toFloat(),
            paint)
    }
}