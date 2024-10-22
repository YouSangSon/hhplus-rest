package yousang.rest_server.domains.concert.interfaces.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yousang.rest_server.common.dto.Response
import yousang.rest_server.domains.concert.application.ConcertFacade
import yousang.rest_server.domains.reservation.interfaces.dto.SeatDto

@RestController
@RequestMapping("/api/v1/concerts")
class ConcertController(
    private val concertFacade: ConcertFacade
) {
//    @GetMapping
//    fun getAllConcerts(): ResponseEntity<Response<List<ConcertDto>>> {
//        return ResponseEntity.ok(Response("Success", concertFacade.getAllConcerts().map { ConcertDto.from(it) }))
//    }
//
//    @GetMapping("/available")
//    fun getAvailableConcerts(@RequestParam now: Long): ResponseEntity<Response<List<ConcertDto>>> {
//        return ResponseEntity.ok(
//            Response("Success", concertFacade.getAvailableConcerts(now).map { ConcertDto.from(it) })
//        )
//    }
//
//    @GetMapping("/{concertId}")
//    fun getConcertById(@PathVariable concertId: Long): ResponseEntity<Response<ConcertDto>> {
//        return ResponseEntity.ok(Response("Success", ConcertDto.from(concertFacade.getConcertById(concertId))))
//    }

    // get a list of available dates for a specific concert
    @GetMapping("/{concertId}/available-dates")
    fun getAvailableDates(@PathVariable concertId: Long): ResponseEntity<Response<List<Long>>> {
        return ResponseEntity.ok(Response("Success", concertFacade.getAvailableDatesByConcert(concertId)))
    }

    // get a list of available seats for a specific concert on a specific date
    @GetMapping("/{concertId}/available-seats")
    fun getAvailableSeats(
        @PathVariable concertId: Long, @RequestParam date: Long
    ): ResponseEntity<Response<List<SeatDto>>> {
        return ResponseEntity.ok(
            Response("Success", concertFacade.getAvailableSeatsByConcertAndDate(concertId, date))
        )
    }
}