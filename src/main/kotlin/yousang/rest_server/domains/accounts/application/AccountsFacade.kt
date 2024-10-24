package yousang.rest_server.domains.users.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.accounts.domain.PaymentService
import yousang.rest_server.domains.queue.domain.QueueService
import yousang.rest_server.domains.reservations.domain.ReservationService
import yousang.rest_server.domains.users.domain.AccountService
import yousang.rest_server.domains.users.interfaces.dto.AccountDomainsRequest
import yousang.rest_server.domains.users.interfaces.dto.AccountDomainsResponse
import yousang.rest_server.domains.users.interfaces.dto.PaymentDomainsResponse

@Service
class AccountsFacade(
    private val accountService: AccountService,
    private val paymentService: PaymentService,
    private val queueService: QueueService,
    private val reservationService: ReservationService,
) {
    fun createAccount(userId: Long) {
        accountService.createAccount(userId)
    }

    @Transactional
    fun deposit(request: AccountDomainsRequest): AccountDomainsResponse {
        request.validateUserId()
        request.validateAmount()
        accountService.deposit(request.userId!!, request.amount!!)
        val balance = accountService.getBalance(request.userId!!)

        return AccountDomainsResponse(userId = request.userId, balance = balance)
    }

    @Transactional
    fun withdraw(request: AccountDomainsRequest): AccountDomainsResponse {
        request.validateUserId()
        request.validateAmount()
        accountService.withdraw(request.userId!!, request.amount!!)
        val balance = accountService.getBalance(request.userId!!)

        return AccountDomainsResponse(userId = request.userId, balance = balance)
    }

    fun getBalance(userId: Long): AccountDomainsResponse {
        val balance = accountService.getBalance(userId)

        return AccountDomainsResponse(userId = userId, balance = balance)
    }

    @Transactional
    fun deleteAccountByUserId(userId: Long) {
        accountService.deleteAccountByUserId(userId)
    }

    // payment processing logic
    @Transactional
    fun processPayment(userId: Long, reservationId: Long, amount: Long): PaymentDomainsResponse {
        val payment = paymentService.createPayment(
            userId = userId, reservationId = reservationId, amount = amount
        )

        try {
            accountService.withdraw(payment.userId, payment.amount)
            paymentService.completePayment(payment.id!!)
        } catch (e: Exception) {
            paymentService.failPayment(payment.id!!)
            throw Exception(e.message)
        }

        return PaymentDomainsResponse(
            paymentId = payment.id,
            userId = payment.userId,
            reservationId = payment.reservationId,
            amount = payment.amount,
            status = payment.status.name
        )
    }
}