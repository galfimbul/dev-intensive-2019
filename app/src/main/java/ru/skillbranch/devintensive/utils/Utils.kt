package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{
        // TO DO FIX ME!!!!
        val parts:List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName

    }

    fun transliteration(payload: String, divider:String = " "): String {
        return "LOL"

    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val result:String?
        if (firstName.isNullOrBlank()&&lastName.isNullOrBlank()){
            result = null
        }
        else if (!firstName.isNullOrBlank()&&lastName.isNullOrBlank()){
            result = "${firstName[0].toUpperCase()}"
        }
        else if (firstName.isNullOrBlank()&&!lastName.isNullOrBlank()){
            result = "${lastName[0].toUpperCase()}"
        }
        else result = "${firstName?.get(0)?.toUpperCase()}${lastName?.get(0)?.toUpperCase()}"


        return result

    }
}