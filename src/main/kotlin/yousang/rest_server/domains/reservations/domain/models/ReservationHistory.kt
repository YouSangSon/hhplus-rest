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

@Entity
@Table(name = "tbl_reservation_history")
class ReservationHistoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "reservation_id", nullable = false) var reservationId: Long,
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false) var status: ReservationStatus,
    @Column(name = "timestamp", nullable = false) var timestamp: Long = System.currentTimeMillis()
) : BaseEntity()