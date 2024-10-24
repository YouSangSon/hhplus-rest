package yousang.rest_server.domains.reservations.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReservationDomainsRequest(
    var userId: Long? = null, val seatId: Long? = null, val reservationId: Long? = null, val amount: Long? = null
) {
    fun validateUserId() {
        if (userId == null) {
            throw IllegalArgumentException("userId is required.")
        }
    }

    fun validateSeatId() {
        if (seatId == null) {
            throw IllegalArgumentException("seatId is required.")
        }
    }

    fun validateReservationId() {
        if (reservationId == null) {
            throw IllegalArgumentException("reservationId is required.")
        }
    }

    fun validateAmount() {
        if (amount == null) {
            throw IllegalArgumentException("amount is required.")
        }
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReservationDomainsResponse(
    val reservationId: Long? = null,
    val userId: Long? = null,
    val seatId: Long? = null,
    val status: String? = null,
    val seats: List<SeatInfo>? = null
) {
    data class SeatInfo(
        val seatId: Long?, val seatNumber: Int, val isReserved: Boolean
    )
}