package yousang.rest_server.domains.queue.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.reservation.domain.models.ReservableResource
import yousang.rest_server.domains.reservation.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.domain.models.UserEntity

@Entity
@Table(name = "tbl_queue")
class QueueEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "queue_position", nullable = false) val queuePosition: Int,
    @Column(name = "expired_at", nullable = false) val expiredAt: Long,
    @Enumerated(EnumType.STRING) @Column(
        name = "status", nullable = false
    ) val status: QueueStatus = QueueStatus.ACTIVE,
    @Embedded val deletion: Deletion = Deletion()
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservable_resource_id", nullable = false)
    var reservableResource: ReservableResourceEntity? = null

    fun toModel(): Queue {
        return Queue(id, queuePosition, expiredAt, status, user?.toModel(), reservableResource?.toModel(), deletion)
    }
}

data class Queue(
    val id: Long?,
    var queuePosition: Int,
    val expiredAt: Long,
    var status: QueueStatus,
    val user: User?,
    val reservableResource: ReservableResource?,
    var deletion: Deletion
) {
    fun softDelete() {
        deletion.delete()
    }

    fun softRestore() {
        deletion.restore()
    }

    fun toEntity(user: UserEntity, reservableResource: ReservableResourceEntity): QueueEntity {
        return QueueEntity(id, queuePosition, expiredAt, status, deletion).apply {
            this.user = user
            this.reservableResource = reservableResource
        }
    }
}

enum class QueueStatus {
    ACTIVE, EXPIRED
}