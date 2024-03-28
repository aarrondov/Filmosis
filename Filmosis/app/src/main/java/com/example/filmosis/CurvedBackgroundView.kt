package com.example.filmosis

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat

class CurvedBackgroundView : View {

    private val backgroundColor = Color.parseColor("#FF9800") // Color de fondo naranja
    val typedValue = TypedValue()
    private val curveColorAttr = context.theme.resolveAttribute(com.google.android.material.R.attr.colorSurface, typedValue, true) // Color de las curvas blanco
    private val curveColor = ContextCompat.getColor(context, typedValue.resourceId)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            super.onDraw(canvas)
        }
        canvas?.apply {
            drawBackgroundColor()
            drawCurves()
        }
    }

    private fun Canvas.drawBackgroundColor() {
        drawColor(backgroundColor)
    }

    private fun Canvas.drawCurves() {
        paint.color = curveColor
        paint.style = Paint.Style.FILL

        val curveHeight = height / 4f

        val path = android.graphics.Path()
        path.moveTo(0f, height.toFloat())
        path.lineTo(0f, height - curveHeight)
        path.cubicTo(
            width / 4f, height - curveHeight,
            width / 2f, height.toFloat(),
            3 * width / 4f, height - curveHeight
        )
        path.lineTo(width.toFloat(), height - curveHeight)
        path.lineTo(width.toFloat(), height.toFloat())
        path.close()

        drawPath(path, paint)
    }
}