package yousang.rest_server.domains.user.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.account.domain.models.Account
import yousang.rest_server.domains.account.domain.models.AccountEntity
import yousang.rest_server.domains.reservation.domain.models.Reservation
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity
import java.util.*

@Entity
@Table(name = "tbl_users")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "uuid", nullable = false, unique = true) var uuid: UUID? = null,
    @Column(name = "username", nullable = false, unique = true) val username: String,
    @Column(name = "email", nullable = false, unique = true) val email: String,
    @Column(name = "password", nullable = false) val password: String,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    @OneToOne(
        mappedBy = "user", cascade = [CascadeType.ALL]
    )
    var account: AccountEntity? = null

    @OneToMany(
        mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY
    )
    var reservations: MutableList<ReservationEntity> = mutableListOf()

    fun toModel(): User {
        return User(
            id, uuid!!, username, email, password, account?.toModel(), reservations.map { it.toModel() }, deletion
        )
    }
}

data class User(
    val id: Long?,
    val uuid: UUID,
    var username: String,
    var email: String,
    var password: String,
    var account: Account?,
    var reservations: List<Reservation>,
    var deletion: Deletion
) {
    fun softDelete() {
        deletion.delete()
        reservations.forEach { it.softDelete() }
        account?.softDelete()
    }

    fun softRestore() {
        deletion.restore()
        reservations.forEach { it.softRestore() }
        account?.softRestore()
    }

    fun toEntity(): UserEntity {
        val entity = UserEntity(id, uuid, username, email, password, deletion)

        entity.account = account?.toEntity(entity)

        entity.reservations = reservations.mapNotNull { reservation ->
            val seatEntity = reservation.seat.let { seat ->
                val reservableResourceEntity = seat.reservableResource?.toEntity() ?: return@mapNotNull null
                seat.toEntity(reservableResourceEntity)
            }
            reservation.toEntity(entity, seatEntity)
        }.toMutableList()

        return entity
    }
}