package yousang.rest_server.domains.account.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.account.domain.models.Payment

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
    fun save(payment: Payment): Payment {
        return paymentRepository.save(payment.toEntity()).toModel()
    }
}