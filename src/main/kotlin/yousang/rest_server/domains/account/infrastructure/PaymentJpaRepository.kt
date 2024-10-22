package yousang.rest_server.domains.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.account.domain.models.PaymentEntity

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long> {
    fun save(payment: PaymentEntity): PaymentEntity
}