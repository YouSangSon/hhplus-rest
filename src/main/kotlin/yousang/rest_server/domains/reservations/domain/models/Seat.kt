package yousang.rest_server.domains.reservations.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.BaseEntity

const val SEAT_RESERVE_TIME = 5 * 60 * 1000L  // 5 min

@Entity
@Table(name = "tbl_seat")
class SeatEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "seat_number", nullable = false) var seatNumber: Int,
    @Column(name = "resource_id", nullable = false) var resourceId: Long,
    @Column(name = "is_reserved", nullable = false) var isReserved: Boolean = false
) : BaseEntity() {
    fun reserve() {
        if (isReserved) {
            throw IllegalStateException("The seat is already reserved")
        }
        isReserved = true
    }

    fun release() {
        isReserved = false
    }

    fun isAvailable(): Boolean {
        return !isReserved
    }
}