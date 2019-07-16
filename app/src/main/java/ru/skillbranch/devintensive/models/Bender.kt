package ru.skillbranch.devintensive.models

/**
 * Created by Alexander Shvetsov on 15.07.2019
 */
class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var count:Int = 0
    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validate = question.validation(answer)
        if (validate!=null){
            return "$validate\n${question.question}" to status.color
        }
        if(question == Question.IDLE){
            question = question.nextQuestion()
            return "На этом все, вопросов больше нет" to status.color}
            else{
        if(question.answers.contains(answer.toLowerCase())){
            if(question == Question.IDLE){
                question = question.nextQuestion()
                return "На этом все, вопросов больше нет" to status.color
            }else{
                question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
            }
        }else{
            if (status==Status.CRITICAL){

                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }else {
                status = status.nextStatus()
                return "Это неправильный ответ\n${question.question}" to status.color
            }
        }}

    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus():Status{
            return if(this.ordinal<values().lastIndex){
                values()[this.ordinal+1]
            }
            else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun validation(answer: String): String? {
                if (answer.isEmpty()||!answer[0].isUpperCase())
                    return "Имя должно начинаться с заглавной буквы"
                else return null
            }

            override fun nextQuestion(): Question = PROFESSION

        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun validation(answer: String): String? {
                if(answer.isEmpty()||!answer[0].isLowerCase())
                    return "Профессия должна начинаться со строчной буквы"
                else
                    return null
            }

            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun validation(answer: String): String? {
                if (answer.isEmpty()||answer.contains(Regex("\\d")))
                    return "Материал не должен содержать цифр"
                else return null
            }

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun validation(answer: String): String? {
                if(answer.isEmpty()||answer.contains(Regex("\\D")))
                    return "Год моего рождения должен содержать только цифры"
                else return null
            }

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun validation(answer: String): String? {
                if (answer.isEmpty()||!answer.contains(Regex("\\d{7}")))
                    return "Серийный номер содержит только цифры, и их 7"
                else return null
            }
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun validation(answer: String): String? = null

            override fun nextQuestion(): Question = IDLE
        };
        abstract fun nextQuestion() : Question
        abstract fun validation(answer: String) : String?
    }
}