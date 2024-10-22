package yousang.rest_server.common.domain.models

import jakarta.persistence.*
import yousang.rest_server.domains.user.domain.models.UserEntity
import java.util.*

@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Long = System.currentTimeMillis()
        protected set

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Long = System.currentTimeMillis()
        protected set

    @PrePersist
    protected fun onCreate() {
        createdAt = System.currentTimeMillis()

        // UserEntity의 경우 uuid 설정
        if (this is UserEntity && this.uuid == null) {
            this.uuid = UUID.randomUUID()
        }
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedAt = System.currentTimeMillis()
    }
}

@Embeddable
open class Deletion {
    @Column(name = "deleted_at")
    var deletedAt: Long? = null
        protected set

    fun delete() {
        deletedAt = System.currentTimeMillis()
    }

    fun restore() {
        deletedAt = null
    }

    val isDeleted: Boolean
        get() = deletedAt != null
}