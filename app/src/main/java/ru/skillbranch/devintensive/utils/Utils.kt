package ru.skillbranch.devintensive.utils

import android.content.Context
import android.content.res.Resources

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.trimStart()?.trim()?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(parts.size - 1)
        if (firstName.isNullOrBlank()) firstName = null
        if (lastName.isNullOrBlank() || firstName.equals(lastName)) lastName = null
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {

        var string = payload.trim()
        var result = StringBuilder()
        val translit = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya"
        )
        if (payload.trim().isNullOrEmpty()) return ""
        else {
            for (symbol in 0..string.length - 1) {
                if(string[symbol].isWhitespace()){
                    result.append(divider)
                }else
                if ("${string[symbol]}" in translit) {
                    result.append(translit.get("${string[symbol]}"))
                } else if ("${string[symbol].toLowerCase()}" in translit) {
                    result.append(translit.get("${string[symbol].toLowerCase()}")?.capitalize())
                } else if (translit.containsValue("${string[symbol].toLowerCase()}")) {
                    result.append("${string[symbol]}")
                } else{result.append("${string[symbol]}")}
            }

            return result.toString()
        }

    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val result: String?
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
            result = null
        } else if (!firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
            result = "${firstName[0].toUpperCase()}"
        } else if (firstName.isNullOrBlank() && !lastName.isNullOrBlank()) {
            result = "${lastName[0].toUpperCase()}"
        } else result = "${firstName?.get(0)?.toUpperCase()}${lastName?.get(0)?.toUpperCase()}"


        return result

    }
    fun convertDpToPixels(dp:Int):Int{
        return dp*Resources.getSystem().displayMetrics.density.toInt()
    }

    fun convertPixelsToDp(pixels:Int):Int{
        return pixels/Resources.getSystem().displayMetrics.density.toInt()
    }
    fun convertSpToPx(context: Context, sp: Int): Int {
        return sp * context.resources.displayMetrics.scaledDensity.toInt()
    }
    fun isRepoValid(link:String):Boolean{
                    return (link!!.isEmpty())||link!!.matches(Regex("^(https:\\/\\/)?(www\\.)?(github\\.com\\/)(?!(${getRegexExceptions()})(?=\\/|\$))[a-zA-Z\\d](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){0,38}(\\/)?\$"))}
    fun getRegexExceptions(): String {
        val exceptions = arrayOf(
            "enterprise", "features", "topics", "collections", "trending", "events", "marketplace", "pricing",
            "nonprofit", "customer-stories", "security", "login", "join"
        )
        return exceptions.joinToString("|")
    }
}