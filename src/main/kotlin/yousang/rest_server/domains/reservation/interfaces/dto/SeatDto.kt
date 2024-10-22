package yousang.rest_server.domains.reservation.interfaces.dto

import yousang.rest_server.domains.reservation.domain.models.SeatStatus

data class SeatDto(
    val seatNumber: Int, val status: SeatStatus
)