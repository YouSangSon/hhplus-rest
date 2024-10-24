package yousang.rest_server.domains.accounts.domain.models

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
@Table(name = "tbl_payment")
class PaymentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "amount", nullable = false) var amount: Long,
    @Column(name = "payment_at", nullable = false) var paymentAt: Long,
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false) var status: PaymentStatus,
    @Column(name = "user_id", nullable = false) var userId: Long, // store id of UserEntity
    @Column(name = "reservation_id", nullable = false) var reservationId: Long // store id of ReservationEntity
) : BaseEntity() {
    fun complete() {
        status = PaymentStatus.COMPLETED
    }

    fun fail() {
        status = PaymentStatus.FAILED
    }
}

enum class PaymentStatus {
    PENDING, COMPLETED, FAILED
}