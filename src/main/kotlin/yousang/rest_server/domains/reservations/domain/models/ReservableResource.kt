package yousang.rest_server.domains.reservations.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.BaseEntity

@Entity
@Table(name = "tbl_reservable_resource")
class ReservableResourceEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "name", nullable = false) var name: String,
    @Column(name = "type", nullable = false) var type: String,
    @Column(name = "venue", nullable = false) var venue: String,
    @Column(name = "date_time", nullable = false) var dateTime: Long,
    @Column(name = "max_seats", nullable = false) var maxSeats: Int
) : BaseEntity()