package yousang.rest_server.domains.queue.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class QueueDomainsRequest(
    var userId: Long? = null
) {
    fun validateUserId() {
        if (userId == null) {
            throw IllegalArgumentException("userId is required.")
        }
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class QueueDomainsResponse(
    val userId: Long? = null,
    val position: Int? = null,
    val status: String? = null
)