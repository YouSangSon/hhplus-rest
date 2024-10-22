package yousang.rest_server.domains.reservation.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion

@Entity
@Table(name = "tbl_seat")
class SeatEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "seat_number", nullable = false) val seatNumber: Int,
    @Enumerated(EnumType.STRING) @Column(
        name = "status", nullable = false
    ) var status: SeatStatus = SeatStatus.AVAILABLE,
    @Column(name = "reserved_until", nullable = true) var reservedUntil: Long? = null,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservable_resource_id", nullable = false)
    var reservableResource: ReservableResourceEntity? = null

    fun toModel(): Seat {
        return Seat(id, seatNumber, status, reservedUntil, reservableResource?.toModel(), deletion)
    }
}

data class Seat(
    val id: Long?,
    val seatNumber: Int,
    var status: SeatStatus,
    var reservedUntil: Long?,
    val reservableResource: ReservableResource?,
    var deletion: Deletion
) {
    fun softDelete() {
        deletion.delete()
    }

    fun softRestore() {
        deletion.restore()
    }

    fun toEntity(reservableResource: ReservableResourceEntity): SeatEntity {
        return SeatEntity(id, seatNumber, status, reservedUntil, deletion).apply {
            this.reservableResource = reservableResource
        }
    }

    fun reserveForLimitedTime(timeLimit: Long) {
        status = SeatStatus.RESERVED
        reservedUntil = System.currentTimeMillis() + timeLimit
    }

    fun cancelReservation() {
        status = SeatStatus.AVAILABLE
        reservedUntil = null
    }
}

enum class SeatStatus {
    AVAILABLE, RESERVED
}