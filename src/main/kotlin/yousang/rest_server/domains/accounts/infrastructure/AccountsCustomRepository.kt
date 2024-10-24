package yousang.rest_server.domains.users.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.accounts.domain.models.AccountEntity
import yousang.rest_server.domains.accounts.domain.models.PaymentEntity
import yousang.rest_server.domains.accounts.domain.models.QAccountEntity
import yousang.rest_server.domains.accounts.domain.models.QPaymentEntity

@Repository
class AccountCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(accountId: Long): AccountEntity? {
        val account = QAccountEntity.accountEntity

        return jpaQueryFactory.selectFrom(account)
            .where(account.id.eq(accountId).and(account.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findByUserId(userId: Long): AccountEntity? {
        val account = QAccountEntity.accountEntity

        return jpaQueryFactory.selectFrom(account)
            .where(account.userId.eq(userId).and(account.deletion.deletedAt.isNull)).fetchOne()
    }
}

@Repository
class PaymentCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(paymentId: Long): PaymentEntity? {
        val payment = QPaymentEntity.paymentEntity

        return jpaQueryFactory.selectFrom(payment)
            .where(payment.id.eq(paymentId).and(payment.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findByUserId(userId: Long): List<PaymentEntity> {
        val payment = QPaymentEntity.paymentEntity

        return jpaQueryFactory.selectFrom(payment)
            .where(payment.userId.eq(userId).and(payment.deletion.deletedAt.isNull)).fetch()
    }
}