package ru.skillbranch.devintensive.extensions

/**
 * Created by Alexander Shvetsov on 04.07.2019
 */

fun String.truncate(value:Int=16):String{
    var result = this.substring(0,value+1)
    if (result[result.length-1].isWhitespace())
    return result.trim()
    else
    return "$result..."
}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&\\d+;"), "").replace(Regex("\\s+"), " ")