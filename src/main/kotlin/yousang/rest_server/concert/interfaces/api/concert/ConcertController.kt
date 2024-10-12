package yousang.rest_server.concert.interfaces.api.concert

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yousang.rest_server.concert.interfaces.dto.*
import yousang.rest_server.common.dto.Response

@RestController
@RequestMapping("/api/v1")
class ConcertController(
//    private val concertFacade: ConcertFacade
) {

    @PostMapping("/token")
    fun issueToken(@RequestBody request: TokenRequest): ResponseEntity<Any> {
        val token = Token(
            token = "mock_jwt_token",
            queuePosition = 1,
            estimatedWaitTime = 120
        )

        val response = Response(
            result = "success",
            body = token
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/seats/dates")
    fun getAvailableDates(): ResponseEntity<Any> {
        val dates = mapOf("dates" to listOf("2024-10-15", "2024-10-16", "2024-10-17"))

        val response = Response(
            result = "success",
            body = dates
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/seats/available")
    fun getAvailableSeats(@RequestParam date: String): ResponseEntity<Any> {
        val seats = mapOf(
            "date" to date,
            "availableSeats" to listOf(1, 2, 3, 4, 5, 10, 20)
        )

        val response = Response(
            result = "success",
            body = seats
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/seats/reserve")
    fun reserveSeat(@RequestBody request: SeatRequest): ResponseEntity<Any> {
        val reservation = mapOf(
            "userId" to request.userId,
            "date" to request.date,
            "seat" to request.seat
        )

        val response = Response(
            result = "success",
            body = reservation
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/users/{userId}/balance/charge")
    fun chargeBalance(
        @PathVariable userId: String,
        @RequestBody request: BalanceChargeRequest
    ): ResponseEntity<Any> {
        val newBalance = 1500

        val response = Response(
            result = "success",
            body = newBalance
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}/balance")
    fun getBalance(@PathVariable userId: String): ResponseEntity<Any> {
        val balance = 1000

        val response = Response(
            result = "success",
            body = balance
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/seats/payment")
    fun makePayment(@RequestBody request: PaymentRequest): ResponseEntity<Any> {
        val payment = 700

        val response = Response(
            result = "success",
            body = payment
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/seats/cancel")
    fun cancelSeatReservation(@RequestBody request: SeatRequest): ResponseEntity<Any> {
        val response = Response(
            result = "success",
            body = mapOf(
                "status" to "canceled",
                "seatNumber" to request.seat,
                "canceledAt" to "2024-10-15T10:02:00Z"
            )
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/concerts")
    fun getAvailableConcerts(): ResponseEntity<Any> {
        val concerts = mapOf(
            "concerts" to listOf(
                mapOf(
                    "concertId" to "c1",
                    "title" to "Spring Festival",
                    "description" to "A spring concert for all",
                    "availableDates" to listOf("2024-10-15", "2024-10-16")
                )
            )
        )

        val response = Response(
            result = "success",
            body = concerts
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/concerts/{concertId}/reservations")
    fun getUserReservations(
        @PathVariable concertId: String,
        @RequestParam userId: String
    ): ResponseEntity<Any>{
        val reservations = mapOf(
            "concertId" to concertId,
            "userId" to userId,
            "reservations" to listOf(
                mapOf(
                    "seatNumber" to 5,
                    "status" to "paid",
                    "reservedAt" to "2024-10-15T10:00:00Z",
                    "expiresAt" to "2024-10-15T10:05:00Z"
                )
            )
        )

        val response = Response(
            result = "success",
            body = reservations
        )

        return ResponseEntity.ok(response)
    }
}