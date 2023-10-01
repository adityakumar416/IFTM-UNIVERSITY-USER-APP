package com.example.iftm.infiniteAutoScrollRecyclerView.extensions


import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.rubensousa.decorator.LinearMarginDecoration

fun Resources.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        displayMetrics
    ).toInt()
}


fun Context.getListHorizontalMarginDecoration(
    @Px leftMargin: Int,
    @Px topMargin: Int,
    @Px rightMargin: Int,
    @Px bottomMargin: Int,
) = LinearMarginDecoration(
    leftMargin = resources.dpToPx(leftMargin),
    topMargin = resources.dpToPx(topMargin),
    rightMargin = resources.dpToPx(rightMargin),
    bottomMargin = resources.dpToPx(bottomMargin),
    orientation = RecyclerView.HORIZONTAL
)