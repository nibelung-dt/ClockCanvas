package com.example.clockcanvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*

class ClockView: View {


// https://github.com/lxq666/CanvasClock


    // compose: https://github.com/beslimir/CanvasLiveClock/tree/master/app/src/main/java/com/example/canvasliveclock
    // https://www.ssaurel.com/blog/learn-to-draw-an-analog-clock-on-android-with-the-canvas-2d-api/

    // https://medium.com/@mayurjajoomj/custom-analog-clock-using-custom-view-android-429cc180f6a3
// https://www.ssaurel.com/blog/learn-to-draw-an-analog-clock-on-android-with-the-canvas-2d-api/
    // // https://github.com/tami-sub/Andersen_HW4/tree/master/app/src/main/java/com/example/andersen_hw4


    private var height = 0
    private var width = 0
    private var radius = 0
    private var angle = 0.0
    private var centreX = 0
    private var centreY = 0
    private var padding = 0
    private var isInit = false
    //private var paint: Paint? = null
    private lateinit var paint: Paint
    private var path: Path? = null
    private var rect: Rect? = null
    private var numbers = listOf<Int>()     //: IntArray
    private var minimum = 0
    private var mHour = 0f
    private var mMinute = 0f
    private var mSecond = 0f
    private var hourHandSize = 0
    private var handSize = 0
/*
    constructor (context: Context, AttributeSet: attrs)  {
        super(context)
    }
*/

    constructor(context: Context?) : super(context) {
        //initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        //initPaint()
    }

    private fun init() {
        // height = height
        // width = width
        centreX = width/2
        centreY = height/2
        padding = 50
        centreX = width / 2
        centreY = height / 2
        minimum = Math.min(height, width)
        radius = minimum / 2 - padding
        angle = (Math.PI / 30 - Math.PI / 2).toFloat().toDouble()

        paint = Paint()
        path = Path()
        rect = Rect()
        hourHandSize = radius - radius / 2
        handSize = radius - radius / 4
        numbers = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        isInit = true
    }

    // drawLine(), drawCircle(), drawPath()
    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            init()
        }

        drawCircle(canvas)
        // drawNumeral(canvas)
        // drawHands(canvas!!)
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

    // рисуем стрелки
    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) hour - 12 else hour
        //paint.color = hourArrowColor
        //drawHand(canvas, ((hour + calendar.get(Calendar.MINUTE) / 60) * 5f), true)
        drawHand(canvas, ((hour + calendar.get(Calendar.HOUR)) * 5f), true) // проверить
        //paint.color = secondArrowColor
        drawHand(canvas, calendar.get(Calendar.MINUTE).toFloat(), false)
       // paint.color = minuteArrowColor
        drawHand(canvas, calendar.get(Calendar.SECOND).toFloat(), false)
    }


    private fun drawHand(canvas: Canvas?, loc: Float, isHour: Boolean){
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = if (isHour) radius - handTruncation - hourHandTruncation else radius - handTruncation
        canvas!!.drawLine((centreX).toFloat(), (centreY).toFloat(),
            (centreX+cos(angle)*handRadius).toFloat(), (centreY + sin(angle) * handRadius).toFloat(),
            paint)
    }

    // создание чисел на циферблате
    private fun drawNumeral(canvas: Canvas) {

    }

}


