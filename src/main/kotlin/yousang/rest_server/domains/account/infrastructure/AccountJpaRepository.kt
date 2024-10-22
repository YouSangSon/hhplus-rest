package yousang.rest_server.domains.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.account.domain.models.AccountEntity

interface AccountJpaRepository : JpaRepository<AccountEntity, Long> {
    fun save(accountEntity: AccountEntity): AccountEntity
}