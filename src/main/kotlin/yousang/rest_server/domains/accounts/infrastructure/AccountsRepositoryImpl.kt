package yousang.rest_server.domains.users.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.accounts.domain.AccountRepository
import yousang.rest_server.domains.accounts.domain.PaymentRepository
import yousang.rest_server.domains.accounts.domain.models.AccountEntity
import yousang.rest_server.domains.accounts.domain.models.PaymentEntity

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository, private val accountCustomRepository: AccountCustomRepository
) : AccountRepository {
    override fun save(accountEntity: AccountEntity): AccountEntity {
        return accountJpaRepository.save(accountEntity)
    }

    override fun delete(accountEntity: AccountEntity) {
        accountJpaRepository.delete(accountEntity)
    }

    override fun findByUserId(userId: Long): AccountEntity? {
        return accountCustomRepository.findByUserId(userId)
    }

    override fun findById(accountId: Long): AccountEntity? {
        return accountCustomRepository.findById(accountId)
    }
}

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository, private val paymentCustomRepository: PaymentCustomRepository
) : PaymentRepository {
    override fun findById(paymentId: Long): PaymentEntity? {
        return paymentCustomRepository.findById(paymentId)
    }

    override fun save(paymentEntity: PaymentEntity): PaymentEntity {
        return paymentJpaRepository.save(paymentEntity)
    }

    override fun findByUserId(userId: Long): List<PaymentEntity> {
        return paymentCustomRepository.findByUserId(userId)
    }
}