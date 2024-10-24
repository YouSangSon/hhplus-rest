package yousang.rest_server.domains.users.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.accounts.domain.models.AccountEntity
import yousang.rest_server.domains.accounts.domain.models.PaymentEntity

interface AccountJpaRepository : JpaRepository<AccountEntity, Long> {

}

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long> {

}