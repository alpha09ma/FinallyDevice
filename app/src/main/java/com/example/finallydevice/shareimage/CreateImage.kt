package com.example.finallydevice.shareimage

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.finallydevice.R.layout

class CreateImage(context: Context): FrameLayout(context)  {
        private val IMAGE_WIDTH = 720
        private val IMAGE_HEIGHT = 1280
        private var tvInfo: TextView? = null
        private fun init() {
            val layout: View = View.inflate(context, com.example.finallydevice.R.layout.share_view_layout, this)
            tvInfo = layout.findViewById(com.example.finallydevice.R.id.tv_info)
        }
        fun setInfo(info: String?) {
            tvInfo!!.text = info
        }
        fun createImage(): Bitmap {
            //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
            val widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY)
            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY)
            measure(widthMeasureSpec, heightMeasureSpec)
            layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT)
            val bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            draw(canvas)
            return bitmap
        }

        init {
            init()
        }
}