package yousang.rest_server.domains.users.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentDomainsRequest(
    val userId: Long? = null, val reservationId: Long? = null, val amount: Long? = null
) {
    fun validateUserId() {
        if (userId == null) {
            throw IllegalArgumentException("userId is required.")
        }
    }

    fun validateReservationId() {
        if (reservationId == null) {
            throw IllegalArgumentException("reservationId is required.")
        }
    }

    fun validateAmount() {
        if (amount == null || amount <= 0) {
            throw IllegalArgumentException("Valid amount is required.")
        }
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentDomainsResponse(
    var paymentId: Long? = null,
    var userId: Long? = null,
    var reservationId: Long? = null,
    var amount: Long? = null,
    var status: String? = null
)