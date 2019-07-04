package ru.skillbranch.devintensive.extensions

/**
 * Created by Alexander Shvetsov on 04.07.2019
 */

fun String.truncate(value:Int=16):String{
    if(this.length-1<=value)
        return this
    val result = this.trimStart().substring(0,value+1)
    if (result[result.length-1].isWhitespace()){
        return result.trim()}
    if(value+2<=this.length-1&&this.trimStart()[value+2].isWhitespace()){
        return result
    }
    else return "$result..."

}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&#\\d+;|&\\w+;"), "").replace(Regex(" +"), " ")