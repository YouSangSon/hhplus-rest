package yousang.rest_server.domains.user.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.account.domain.PaymentRepository
import yousang.rest_server.domains.account.domain.models.PaymentEntity

@Repository
class PaymentRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val paymentJpaRepository: PaymentJpaRepository,
    private val paymentCustomRepository: PaymentCustomRepository
) : PaymentRepository {
    override fun save(payment: PaymentEntity): PaymentEntity {
        return paymentJpaRepository.save(payment)
    }
}

