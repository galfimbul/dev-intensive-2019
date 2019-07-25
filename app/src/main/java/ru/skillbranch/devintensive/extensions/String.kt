package ru.skillbranch.devintensive.extensions

/**
 * Created by Alexander Shvetsov on 04.07.2019
 */

fun String.truncate(value: Int = 16): String {
    val result = this.trim()
    if (result.length <= value) {
        return result
    } else return "${result.substring(0, value).trim()}..."

}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&#\\d+;|&\\w+;"), "").replace(Regex(" +"), " ")