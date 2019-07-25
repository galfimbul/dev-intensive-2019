package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Alexander Shvetsov on 16.07.2019
 */
fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this.currentFocus != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}


fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val root = this.window.decorView
    root.getWindowVisibleDisplayFrame(r)
    return root.height - r.bottom > 0

}

fun Activity.isKeyboardClosed(): Boolean {
    /*val  r = Rect()
    val root = this.window.decorView
    root.getWindowVisibleDisplayFrame(r)
    return root.height-r.bottom==0*/
    return this.isKeyboardOpen().not()
}