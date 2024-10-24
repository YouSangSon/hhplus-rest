package yousang.rest_server.domains.accounts.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.BaseEntity

const val USER_MAX_POINTS: Long = 1_000_000_000L

@Entity
@Table(name = "tbl_account")
class AccountEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "user_id", nullable = false) var userId: Long,
    @Column(name = "balance", nullable = false) var balance: Long = 0L
) : BaseEntity() {
    fun deposit(amount: Long) {
        require(amount > 0) { "charge amount must be positive" }
        balance += amount
    }
    
    fun withdraw(amount: Long) {
        require(amount > 0) { "charge amount must be positive." }
        if (balance < amount) {
            throw IllegalStateException("insufficient balance")
        }
        balance -= amount
    }
}