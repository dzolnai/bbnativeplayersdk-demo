package com.bluebillywig.bbnativeplayersdk_demo

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton


class SquareButton : AppCompatButton {
	constructor(context: Context?) : super(context!!)
	constructor(context: Context?, attrs: AttributeSet?) : super(
		context!!, attrs
	)

	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
		context!!, attrs, defStyleAttr
	)

	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec)
		val width = MeasureSpec.getSize(widthMeasureSpec)
		val height = MeasureSpec.getSize(heightMeasureSpec)
		val size = if (width > height) height else width
		setMeasuredDimension(size, size) // make it square
	}
}