package dev.michaelobi.clubhouse.data.model

data class RoomMember(
    val name: String,
    val status: RoomMemberStatus
)
enum class RoomMemberStatus {
    listener, speaker, moderator
}