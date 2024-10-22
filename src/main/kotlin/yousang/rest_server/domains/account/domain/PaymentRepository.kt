package yousang.rest_server.domains.account.domain

import yousang.rest_server.domains.account.domain.models.PaymentEntity

interface PaymentRepository {
    fun save(payment: PaymentEntity): PaymentEntity
}