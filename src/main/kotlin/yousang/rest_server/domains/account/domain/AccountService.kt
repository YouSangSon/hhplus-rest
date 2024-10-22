package yousang.rest_server.domains.user.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.account.domain.models.Account
import yousang.rest_server.domains.user.domain.models.User


@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {
    fun save(user: User, account: Account): Account {
        return accountRepository.save(account.toEntity(user.toEntity())).toModel()
    }
}