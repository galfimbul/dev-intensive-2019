package ru.skillbranch.devintensive.extensions

import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.devintensive.R

/**
 * Created by Alexander Shvetsov on 07.09.2019
 */
fun Snackbar.setDecoration():Snackbar{
    val theme = this.context.theme

    val colorAccent = TypedValue()
    val backgroundColor = TypedValue()
    val textColor = TypedValue()

    theme.resolveAttribute(R.attr.colorAccent, colorAccent, true)
    theme.resolveAttribute(R.attr.titleColor, backgroundColor, true)
    theme.resolveAttribute(R.attr.bgColor, textColor, true)

    this.setActionTextColor(colorAccent.data)
    this.view.setBackgroundColor(backgroundColor.data)
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(textColor.data)
    return this
}