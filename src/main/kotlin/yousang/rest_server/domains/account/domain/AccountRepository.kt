package yousang.rest_server.domains.user.domain

import yousang.rest_server.domains.account.domain.models.AccountEntity


interface AccountRepository {
    fun save(accountEntity: AccountEntity): AccountEntity
}