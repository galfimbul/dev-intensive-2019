package ru.skillbranch.devintensive.models

/**
 * Created by Alexander Shvetsov on 15.07.2019
 */
class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var count = 0
    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if(question.answers.contains(answer)){
            question = question.nextQuestion()
            if(question.question.equals(Question.IDLE)){
                return "Отлично - ты справился\n На этом все, вопросов больше нет" to status.color
            }else{
            return "Отлично - ты справился\n ${question.question}" to status.color
            }
        }else{
            count++
            status = status.nextStatus()
            if (count>3){
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            return "Это не правильный ответ!\n ${question.question}" to status.color
        }

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
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };
        abstract fun nextQuestion() : Question
    }
}