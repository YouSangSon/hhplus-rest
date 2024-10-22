package yousang.rest_server.domains.account.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.reservation.domain.models.Reservation
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.domain.models.UserEntity

@Entity
@Table(name = "tbl_payment")
class PaymentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "amount", nullable = false) val amount: Long,
    @Enumerated(EnumType.STRING) @Column(
        name = "status", nullable = false
    ) val status: PaymentStatus = PaymentStatus.PENDING,
    @Column(name = "payment_at", nullable = false) val paymentAt: Long,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    var reservation: ReservationEntity? = null

    fun toModel(): Payment {
        return Payment(id, amount, status, paymentAt, user?.toModel(), reservation?.toModel(), deletion)
    }
}

data class Payment(
    val id: Long?,
    val amount: Long,
    var status: PaymentStatus,
    val paymentAt: Long,
    val user: User?,
    val reservation: Reservation?,
    var deletion: Deletion
) {
    fun softDelete() {
        deletion.delete()
        reservation?.softDelete()
    }

    fun softRestore() {
        deletion.restore()
        reservation?.softRestore()
    }

    fun toEntity(user: UserEntity, reservation: ReservationEntity): PaymentEntity {
        return PaymentEntity(id, amount, status, paymentAt, deletion).apply {
            this.user = user
            this.reservation = reservation
        }
    }
}

enum class PaymentStatus {
    PENDING, COMPLETED, FAILED
}