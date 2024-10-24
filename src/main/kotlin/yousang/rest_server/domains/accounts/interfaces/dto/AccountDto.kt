package yousang.rest_server.domains.users.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccountDomainsRequest(
    var userId: Long? = null, val amount: Long? = null
) {
    fun validateUserId() {
        if (userId == null) {
            throw IllegalArgumentException("userId is required.")
        }
    }

    fun validateAmount() {
        if (amount == null || amount <= 0) {
            throw IllegalArgumentException("valid amount is required.")
        }
    }
}


@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccountDomainsResponse(
    var userId: Long? = null, var balance: Long? = null, var status: String? = null
)