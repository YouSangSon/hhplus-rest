package yousang.rest_server.domains.accounts.domain

import yousang.rest_server.domains.accounts.domain.models.AccountEntity
import yousang.rest_server.domains.accounts.domain.models.PaymentEntity

interface AccountRepository {
    fun findById(accountId: Long): AccountEntity?
    fun findByUserId(userId: Long): AccountEntity?
    fun save(accountEntity: AccountEntity): AccountEntity
    fun delete(accountEntity: AccountEntity)
}

interface PaymentRepository {
    fun findById(paymentId: Long): PaymentEntity?
    fun findByUserId(userId: Long): List<PaymentEntity>
    fun save(paymentEntity: PaymentEntity): PaymentEntity
}