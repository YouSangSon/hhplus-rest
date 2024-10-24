package yousang.rest_server.domains.users.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.accounts.domain.AccountRepository
import yousang.rest_server.domains.accounts.domain.models.AccountEntity


@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    fun createAccount(userId: Long): AccountEntity {
        try {
            val account = AccountEntity(
                userId = userId
            )
            return accountRepository.save(account)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun deposit(userId: Long, amount: Long) {
        try {
            val account = getAccountByUserId(userId)
            account.deposit(amount)
            accountRepository.save(account)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }

    fun withdraw(userId: Long, amount: Long) {
        try {
            val account = getAccountByUserId(userId)
            account.withdraw(amount)
            accountRepository.save(account)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }

    fun getBalance(userId: Long): Long {
        try {
            val account = getAccountByUserId(userId)
            return account.balance
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }

    fun deleteAccountByUserId(userId: Long) {
        try {
            val account = getAccountByUserId(userId)
            account.softDelete()
            accountRepository.delete(account)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }

    private fun getAccountByUserId(userId: Long): AccountEntity {
        try {
            return accountRepository.findByUserId(userId) ?: throw IllegalArgumentException("account not found")
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (e: QueryException) {
            throw QueryException(e.message)
        }
    }
}