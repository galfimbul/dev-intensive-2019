package ru.skillbranch.devintensive.extension

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}
fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) :Date{
    var time = this.time
    time+=when(units){
        TimeUnits.SECOND-> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY
    }
    this.time = time
    return this
}
fun Date.humanizeDiff(date: Date = Date()): String {
    var value:Long = (date.time - this.time)
    var result: String =""
    var inFuture:Boolean = false
    if(value<0) {
        value = -value
        inFuture = true
    }
    when (value / SECOND) {
        in 0..1-> return "только что"
        in 1..45-> result = "несколько секунд"
        in 45..75-> result = "минуту"
        in 75..45 * 60->
            when (value / MINUTE%10){
                1L->if (value / MINUTE%100!= 11L){
                    result = "${(value / MINUTE)} минуту"
                }
                2L,3L,4L->if (value / MINUTE%100 !in 12L..14L){
                    result = "${(value / MINUTE)} минуты"
                }
                else -> result = "${(value / MINUTE)} минут"
            }
        in 45*60..75*60-> result = "час"
        in 75*60..22* HOUR/ SECOND-> when (value / HOUR%10){
            1L->if (value / HOUR%100!= 11L){
                result = "${(value / HOUR)} час"
            }
            2L,3L,4L->if (value / HOUR%100 !in 12L..14L){
                result = "${(value / HOUR)} часа"
            }
            else -> result = "${(value / HOUR)} часов"
        }
        in 22* HOUR / SECOND..26 * HOUR / SECOND -> result = "день"
        in 26 * HOUR / SECOND .. 360* DAY/ SECOND-> when (value / DAY%10){
            1L->if (value / DAY%100!= 11L){
                result = "${(value / DAY)} день"
            }
            2L,3L,4L->if (value / DAY%100 !in 12L..14L){
                result = "${(value / DAY)} дня"
            }
            else -> result = "${(value / DAY)} дней"
        }
    }
    when(inFuture){
        true-> if (value/ DAY<=360) {return "через $result"} else{return "более чем через год"}
        false -> if (value/ DAY<=360) {return "$result назад"} else{return "более года назад"}
    }

}
enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}