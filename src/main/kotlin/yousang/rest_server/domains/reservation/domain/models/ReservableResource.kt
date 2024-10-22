package yousang.rest_server.domains.reservation.domain.models

import jakarta.persistence.*
import yousang.rest_server.common.domain.models.BaseEntity
import yousang.rest_server.common.domain.models.Deletion

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class ReservableResourceEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) open val id: Long? = null,
    @Column(name = "name", nullable = false) open val name: String,
    @Column(name = "date_time", nullable = false) open val dateTime: Long,
    @Embedded open val deletion: Deletion = Deletion()
) : BaseEntity() {
    @OneToMany(mappedBy = "reservableResource", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var seats: MutableList<SeatEntity> = mutableListOf()

    abstract fun toModel(): ReservableResource
}

open class ReservableResource(
    open val id: Long?,
    open var name: String,
    open var dateTime: Long,
    open var seats: MutableList<Seat> = mutableListOf(),
    open var deletion: Deletion
) {
    open fun softDelete() {
        deletion.delete()
        seats.forEach { it.softDelete() }
    }

    open fun softRestore() {
        deletion.restore()
        seats.forEach { it.softRestore() }
    }

    open fun toEntity(): ReservableResourceEntity {
        throw NotImplementedError("toEntity() must be overridden in subclasses")
    }
}