package yousang.rest_server.domains.user.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.account.domain.models.AccountEntity
import yousang.rest_server.domains.user.domain.AccountRepository

@Repository
class AccountRepositoryImpl(
    private val queryFactory: JPAQueryFactory, private val accountJpaRepository: AccountJpaRepository
) : AccountRepository {
    override fun save(accountEntity: AccountEntity): AccountEntity {
        return accountJpaRepository.save(accountEntity)
    }
}

