package yousang.rest_server.domains.reservation.interfaces.api


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yousang.rest_server.common.dto.Response
import yousang.rest_server.common.utils.logger
import yousang.rest_server.domains.reservation.application.ReservationFacade

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(
    private val reservationFacade: ReservationFacade
) {
    @PostMapping("/{reservationId}/confirm")
    fun confirmReservation(@PathVariable reservationId: Long): ResponseEntity<Response<Any>> {
        val reservation = reservationFacade.confirmReservation(reservationId)
        logger.debug { "Reservation $reservationId confirmed : $reservation" }

        return ResponseEntity.ok(Response("success", message = "reservation confirmed"))
    }

    @PostMapping("/{reservationId}/cancel")
    fun cancelReservation(@PathVariable reservationId: Long): ResponseEntity<Response<Any>> {
        val reservation = reservationFacade.cancelReservation(reservationId)
        logger.debug { "Reservation $reservationId canceled : $reservation" }

        return ResponseEntity.ok(Response("success", message = "reservation canceled"))
    }

    @PostMapping("/seats/{seatId}/reserve")
    fun reserveSeat(@PathVariable seatId: Long, @RequestParam userId: Long): ResponseEntity<Response<Any>> {
        val reservation = reservationFacade.reserveSeat(userId, seatId)
        logger.debug { "Seat $seatId reserved : $reservation" }

        return ResponseEntity.ok(Response("success", message = "seat reserved"))
    }

    @PostMapping("/seats/{seatId}/cancel")
    fun cancelSeatReservation(@PathVariable seatId: Long): ResponseEntity<Response<Any>> {
        val seat = reservationFacade.cancelSeatReservation(seatId)
        logger.debug { "Seat $seatId reservation canceled : $seat" }

        return ResponseEntity.ok(Response("success", message = "seat reservation canceled"))
    }
}