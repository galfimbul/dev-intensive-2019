package ru.skillbranch.devintensive.models

/**
 * Created by Alexander Shvetsov on 02.07.2019
 */
class Chat(
    val id: String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
}