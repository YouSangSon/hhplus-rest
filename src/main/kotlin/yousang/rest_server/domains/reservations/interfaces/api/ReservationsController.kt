package yousang.rest_server.domains.reservations.interfaces.api


import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import yousang.rest_server.common.interfaces.dto.Response
import yousang.rest_server.common.utils.logger
import yousang.rest_server.domains.reservations.application.ReservationFacade
import yousang.rest_server.domains.reservations.interfaces.dto.ReservationDomainsRequest
import yousang.rest_server.security.CustomUserDetails

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationsController(
    private val reservationFacade: ReservationFacade
) {
    @PostMapping("/reserve")
    fun reserveSeat(@RequestBody request: ReservationDomainsRequest): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        request.userId = userDetails.userId
        val response = reservationFacade.reserveSeat(request)

        return ResponseEntity.ok(Response("success", data = response))
    }

    @PostMapping("/confirm")
    fun confirmReservation(@RequestBody request: ReservationDomainsRequest): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        request.userId = userDetails.userId
        reservationFacade.confirmReservation(request)

        return ResponseEntity.ok(Response("success"))
    }

    @PostMapping("/cancel")
    fun cancelReservation(@RequestBody request: ReservationDomainsRequest): ResponseEntity<Response<Any>>{
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        request.userId = userDetails.userId
        reservationFacade.cancelReservation(request)

        return ResponseEntity.ok(Response("success"))
    }

    @GetMapping("/seats")
    fun getAvailableSeats(@RequestParam resourceId: Long): ResponseEntity<Response<Any>> {
        val seats = reservationFacade.getAvailableSeats(resourceId)

        return ResponseEntity.ok(Response("success", data = seats))
    }
}