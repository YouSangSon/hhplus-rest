package yousang.rest_server.domains.concert.interfaces.dto

import yousang.rest_server.domains.concert.domain.models.Concert
import yousang.rest_server.domains.reservation.interfaces.dto.SeatDto

//data class ConcertDto(
//    val id: Long?,
//    var name: String,
//    var dateTime: Long,
//    var venue: String,
//    var maxSeats: Int,
//    var seats: List<SeatDto> = listOf()
//) {
//    companion object {
//        fun from(concertModel: Concert): ConcertDto {
//            return ConcertDto(
//                id = concertModel.id,
//                name = concertModel.name,
//                dateTime = concertModel.dateTime,
//                venue = concertModel.venue,
//                maxSeats = concertModel.maxSeats,
//                seats = concertModel.seats.map { SeatDto.from(it) }
//            )
//        }
//    }
//}