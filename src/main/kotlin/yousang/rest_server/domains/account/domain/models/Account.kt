package yousang.rest_server.domains.account.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.domain.models.UserEntity

@Entity
@Table(name = "tbl_account")
class AccountEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "balance", nullable = false) val balance: Long = 0L,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null

    fun toModel(): Account {
        return Account(id, balance, user?.toModel(), deletion)
    }
}

data class Account(
    val id: Long?, var balance: Long, val user: User?, var deletion: Deletion
) {
    fun softDelete() {
        deletion.delete()
    }

    fun softRestore() {
        deletion.restore()
    }

    fun toEntity(user: UserEntity): AccountEntity {
        return AccountEntity(id, balance, deletion).apply {
            this.user = user
        }
    }

    fun charge(amount: Long) {
        require(amount > 0) { "the charge amount must be positive" }
        this.balance += amount
    }

    fun use(amount: Long) {
        require(amount > 0) { "the subtraction amount must be positive" }
        if (balance < amount) {
            throw IllegalArgumentException("insufficient balance")
        }

        balance -= amount
    }
}