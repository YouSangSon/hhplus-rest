package yousang.rest_server.domains.reservation.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.domain.models.UserEntity

@Entity
@Table(name = "reservations")
class ReservationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "reservation_at", nullable = false) val reservationAt: Long,
    @Enumerated(EnumType.STRING) @Column(
        name = "status", nullable = false
    ) var status: ReservationStatus = ReservationStatus.PENDING,
    @Column(name = "expired_at", nullable = false) var expiredAt: Long,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "seat_id", nullable = false) val seat: SeatEntity,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    fun toModel(): Reservation {
        return Reservation(
            id, reservationAt, status, expiredAt, user.toModel(), seat.toModel(), deletion
        )
    }
}

data class Reservation(
    val id: Long?,
    val reservationAt: Long,
    var status: ReservationStatus,
    var expiredAt: Long,
    val user: User,
    val seat: Seat,
    var deletion: Deletion
) {
    fun softDelete() {
        this.deletion.delete()
    }

    fun softRestore() {
        this.deletion.restore()
    }

    fun toEntity(userEntity: UserEntity, seatEntity: SeatEntity): ReservationEntity {
        return ReservationEntity(id, reservationAt, status, expiredAt, userEntity, seatEntity, deletion)
    }
}

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}