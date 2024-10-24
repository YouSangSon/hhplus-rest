package yousang.rest_server.domains.queue.domain.models

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
@Table(name = "tbl_queue")
class QueueEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "user_id", nullable = false, unique = true) var userId: Long,
    @Column(name = "position", nullable = false) var position: Int,
    @Enumerated(EnumType.STRING) @Column(
        name = "status",
        nullable = false
    ) var status: QueueStatus = QueueStatus.WAITING,
    @Column(name = "issued_at", nullable = false) var issuedAt: Long = System.currentTimeMillis()
) : BaseEntity() {

    fun activate() {
        status = QueueStatus.ACTIVE
    }

    fun expire() {
        status = QueueStatus.EXPIRED
    }
}

enum class QueueStatus {
    WAITING, ACTIVE, EXPIRED
}