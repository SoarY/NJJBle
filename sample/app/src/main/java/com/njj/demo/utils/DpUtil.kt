package com.njj.demo.utils

import android.content.Context
import kotlin.math.roundToInt

object DpUtil {
    fun dp2px(context: Context, value: Int): Int {
        return (context.resources.displayMetrics.density * value).roundToInt()
    }
}