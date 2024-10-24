package yousang.rest_server.domains.accounts.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.accounts.domain.models.PaymentEntity
import yousang.rest_server.domains.accounts.domain.models.PaymentStatus

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
    fun createPayment(userId: Long, reservationId: Long, amount: Long): PaymentEntity {
        try {
            val payment = PaymentEntity(
                amount = amount,
                paymentAt = System.currentTimeMillis(),
                status = PaymentStatus.PENDING,
                userId = userId,
                reservationId = reservationId
            )
            paymentRepository.save(payment)

            return payment
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun completePayment(paymentId: Long) {
        try {
            val payment = paymentRepository.findById(paymentId)
                ?: throw IllegalArgumentException("payment information not found.")
            payment.status = PaymentStatus.COMPLETED
            paymentRepository.save(payment)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }

    fun failPayment(paymentId: Long) {
        try {
            val payment = paymentRepository.findById(paymentId)
                ?: throw IllegalArgumentException("payment information not found.")
            payment.status = PaymentStatus.FAILED
            paymentRepository.save(payment)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }
}