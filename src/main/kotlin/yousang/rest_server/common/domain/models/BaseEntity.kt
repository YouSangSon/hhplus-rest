package yousang.rest_server.common.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate

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
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedAt = System.currentTimeMillis()
    }

    @Embedded
    var deletion: Deletion = Deletion()

    open fun softDelete() {
        deletion.delete()
    }

    open fun softRestore() {
        deletion.restore()
    }
}

@Embeddable
class Deletion {
    @Column(name = "deleted_at")
    var deletedAt: Long? = null
        private set

    fun delete() {
        deletedAt = System.currentTimeMillis()
    }

    fun restore() {
        deletedAt = null
    }

    val isDeleted: Boolean
        get() = deletedAt != null
}