package yousang.rest_server.domains.queue.interfaces.dto

data class QueueDto(
    val queuePosition: Int, val expiredAt: Long
)