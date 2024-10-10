package yousang.rest_server.application

import yousang.rest_server.interfaces.dto.*

//class ConcertFacade(
//    private val tokenService: TokenService,
//    private val seatService: SeatService,
//    private val paymentService: PaymentService,
//    private val userService: UserService
//) {
//     fun issueToken(request: TokenRequest): Response<Token> {
//        val token = tokenService.issueToken(request.userId)
//        return Response("success", token)
//    }
//
//     fun getAvailableDates(): Response<Map<String, List<String>>> {
//        val dates = seatService.getAvailableDates()
//        return Response("success", dates)
//    }
//
//     fun getAvailableSeats(date: String): Response<Map<String, Any>> {
//        val seats = seatService.getAvailableSeats(date)
//        return Response("success", seats)
//    }
//
//     fun reserveSeat(request: SeatReservationRequest): Response<Map<String, Any>> {
//        val reservation = seatService.reserveSeat(request.userId, request.date, request.seat)
//        return Response("success", reservation)
//    }
//
//     fun chargeBalance(userId: String, request: BalanceChargeRequest): Response<Int> {
//        val newBalance = userService.chargeBalance(userId, request.amount)
//        return Response("success", newBalance)
//    }
//
//     fun getBalance(userId: String): Response<Int> {
//        val balance = userService.getBalance(userId)
//        return Response("success", balance)
//    }
//
//     fun makePayment(request: PaymentRequest): Response<Int> {
//        val payment = paymentService.makePayment(request.userId, request.date, request.seat)
//        return Response("success", payment)
//    }
//}