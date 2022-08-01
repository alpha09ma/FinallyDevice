package com.example.finallydevice.ScreenStatics.home.recycleview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Item_decoration(val spancount:Int,val spacing:Int,val includeedge:Boolean):
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spancount
        if (includeedge) {
            outRect.left = spacing - spacing * column / spancount
            outRect.right = (column + 1) * spacing / spancount
            outRect.top = spacing
            outRect.bottom = spacing
            outRect.left = column * spacing / spancount
            outRect.right = spacing - (column + 1) * spacing / spancount
        }
        super.getItemOffsets(outRect, view, parent, state)
    }
}