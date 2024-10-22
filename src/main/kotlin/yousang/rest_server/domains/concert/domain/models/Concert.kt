package yousang.rest_server.domains.concert.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.Deletion
import yousang.rest_server.domains.reservation.domain.models.ReservableResource
import yousang.rest_server.domains.reservation.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.reservation.domain.models.Seat

@Entity
@Table(name = "tbl_concert")
class ConcertEntity(
    id: Long? = null,
    name: String,
    dateTime: Long,
    @Column(name = "venue", nullable = false) val venue: String,
    @Column(name = "max_seats", nullable = false) val maxSeats: Int,
    deletion: Deletion = Deletion()
) : ReservableResourceEntity(id, name, dateTime, deletion) {
    override fun toModel(): Concert {
        return Concert(id, name, dateTime, venue, maxSeats, seats.map { it.toModel() }.toMutableList(), deletion)
    }
}

data class Concert(
    override var id: Long?,
    override var name: String,
    override var dateTime: Long,
    var venue: String,
    var maxSeats: Int,
    override var seats: MutableList<Seat> = mutableListOf(),
    override var deletion: Deletion
) : ReservableResource(id, name, dateTime, seats, deletion) {
    override fun toEntity(): ConcertEntity {
        val entity = ConcertEntity(id, name, dateTime, venue, maxSeats, deletion)
        entity.seats = seats.map { it.toEntity(entity) }.toMutableList()

        return entity
    }
}

//@Entity
//@Table(name = "tbl_musical")
//class MusicalEntity(
//    id: Long? = null,
//    name: String,
//    dateTime: Long,
//    val director: String,  // 뮤지컬만의 고유 필드
//    deletion: Deletion = Deletion()
//) : ReservableResourceEntity(id, name, dateTime, deletion) {
//
//    override fun toModel(): Musical {
//        return Musical(id, name, dateTime, director, seats.map { it.toModel() }.toMutableList(), deletion)
//    }
//}

//@Entity
//@Table(name = "tbl_match")
//class MatchEntity(
//    id: Long? = null,
//    name: String,
//    dateTime: Long,
//    val teamA: String,  // 경기 팀 A
//    val teamB: String,  // 경기 팀 B
//    deletion: Deletion = Deletion()
//) : ReservableResourceEntity(id, name, dateTime, deletion) {
//
//    override fun toModel(): Match {
//        return Match(id, name, dateTime, teamA, teamB, seats.map { it.toModel() }.toMutableList(), deletion)
//    }
//}

//data class Musical(
//    val id: Long?,
//    var name: String,
//    var dateTime: Long,
//    var director: String,
//    var seats: MutableList<Seat> = mutableListOf(),
//    var deletion: Deletion
//) : ReservableResource(id, name, dateTime, seats, deletion)
//
//data class Match(
//    val id: Long?,
//    var name: String,
//    var dateTime: Long,
//    var teamA: String,
//    var teamB: String,
//    var seats: MutableList<Seat> = mutableListOf(),
//    var deletion: Deletion
//) : ReservableResource(id, name, dateTime, seats, deletion)