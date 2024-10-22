package yousang.rest_server.domains.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.account.domain.PaymentService
import yousang.rest_server.domains.account.domain.models.Payment
import yousang.rest_server.domains.account.domain.models.PaymentStatus
import yousang.rest_server.domains.queue.domain.QueueService
import yousang.rest_server.domains.reservation.domain.SeatService
import yousang.rest_server.domains.reservation.domain.models.SeatStatus
import yousang.rest_server.domains.user.domain.AccountService
import yousang.rest_server.domains.user.domain.UserService
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.interfaces.dto.PaymentRequest
import yousang.rest_server.domains.user.interfaces.dto.PaymentResponse

@Service
class AccountFacade(
    private val userService: UserService,
    private val accountService: AccountService,
    private val seatService: SeatService,
    private val paymentService: PaymentService,
    private val queueService: QueueService
) {
    @Transactional
    fun chargeBalance(userId: Long, amount: Long): User {
        val user = userService.findById(userId)
        val account = user.account ?: throw IllegalArgumentException("don't have account information")
        account.charge(amount)
        accountService.save(user, account)

        return user
    }

    @Transactional
    fun useBalance(userId: Long, amount: Long): User {
        val user = userService.findById(userId)
        val account = user.account ?: throw IllegalArgumentException("don't have account information")
        account.use(amount)
        accountService.save(user, account)

        return user
    }

    @Transactional(readOnly = true)
    fun getBalance(userId: Long): User {
        val user = userService.findById(userId)
        user.account ?: throw IllegalArgumentException("don't have account information")

        return user
    }

    @Transactional
    fun processPayment(request: PaymentRequest): PaymentResponse {
        // 1. 유저와 좌석을 확인
        val user = userService.findById(request.userId)
        val seat = seatService.findById(request.seatId)

        // 2. 좌석이 예약 가능한 상태인지 확인
        if (seat.status != SeatStatus.AVAILABLE) {
            throw IllegalStateException("seat is not available")
        }

        // 3. 결제 처리 (잔액 확인 및 차감)
        if ((user.account?.balance ?: 0L) < request.amount) {
            throw IllegalArgumentException("insufficient balance")
        }
        user.account?.use(request.amount)

        // 4. 결제 내역 생성
        val payment = Payment(
            id = null,
            amount = request.amount,
            status = PaymentStatus.COMPLETED,
            paymentAt = request.paymentAt,
            user = user,
            reservation = seat.reservation  // 좌석과 예약 정보를 연결
        )
        paymentService.save(payment.toEntity(user.toEntity(), seat.toEntity(seat.reservableResource!!.toEntity())))

        // 5. 좌석 소유권 배정
        seat.status = SeatStatus.RESERVED
        seatService.save(seat)

        // 6. 대기열 토큰 만료 처리
        queueService.expireQueueForUser(user)

        // 7. 결제 응답 반환
        return PaymentResponse(
            paymentId = payment.id!!,
            userId = user.id!!,
            seatId = seat.id!!,
            status = payment.status,
            message = "Payment successful, seat reserved."
        )
    }
}