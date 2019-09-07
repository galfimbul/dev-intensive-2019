package ru.skillbranch.devintensive.models.data

import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun unreadableMessageCount(): Int {
        return if (id == "-1") ChatRepository.unreadableArchiveMessageCount.value?:0 else messages.count { !it.isReaded }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        val  message = if(id =="-1") ChatRepository.lastArchiveMessage.value else messages.lastOrNull()
        return when(message){
            null ->null
            else ->message.date
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String,String>{
        val result:String
        val message = if(id =="-1") ChatRepository.lastArchiveMessage.value else messages.lastOrNull()
        result = when(message){
            is TextMessage -> message.text!!
            is ImageMessage -> "${message?.from?.firstName} - отправил фото"
            else -> "Сообщений еще нет"
        }
        return result to message?.from?.firstName.toString()
    }

    private fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                if (id=="-1") ChatType.ARCHIVE else ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}

enum class ChatType{
    SINGLE,
    GROUP,
    ARCHIVE
}



