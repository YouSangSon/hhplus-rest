package yousang.rest_server.domains.reservations.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.BaseEntity

const val RESERVATION_EXPIRE_TIME = 5 * 60 * 1000L  // 5 min

@Entity
@Table(name = "tbl_reservation")
class ReservationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "user_id", nullable = false) var userId: Long,
    @Column(name = "seat_id", nullable = false) var seatId: Long,
    @Enumerated(EnumType.STRING) @Column(
        name = "status", nullable = false
    ) var status: ReservationStatus = ReservationStatus.RESERVED,
    @Column(name = "reserved_at", nullable = false) var reservedAt: Long = System.currentTimeMillis()
) : BaseEntity() {
    fun confirm() {
        status = ReservationStatus.CONFIRMED
    }

    fun cancel() {
        status = ReservationStatus.CANCELLED
    }

    fun expire() {
        status = ReservationStatus.EXPIRED
    }
}

enum class ReservationStatus {
    RESERVED, CONFIRMED, CANCELLED, EXPIRED
}