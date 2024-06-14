package com.njj.demo.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName GridItemDecoration
 * @Description TODO
 * @Author  Darcy
 * @Date 2022/3/1 19:28
 * @Version 1.0
 */
class GridItemDecoration(var space: Int = 10) : RecyclerView.ItemDecoration (){
    val mSpace = 10

    val mIncludeEdge = true

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        //列数
        val spanCount = gridLayoutManager!!.spanCount
        val position = parent.getChildLayoutPosition(view!!)
        val column = position % spanCount
        if (mIncludeEdge) {
            outRect.left = mSpace - column * mSpace / spanCount
            outRect.right = (column + 1) * mSpace / spanCount
            if (position < spanCount) {
                outRect.top = mSpace
            }
            outRect.bottom = mSpace
        } else {
            outRect.left = column * mSpace / spanCount
            outRect.right = mSpace - (column + 1) * mSpace / spanCount
            if (position >= spanCount) {
                outRect.top = mSpace
            }
        }
    }

}
