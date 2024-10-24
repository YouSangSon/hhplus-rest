package yousang.rest_server.domains.reservations.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservations.domain.ReservableResourceRepository
import yousang.rest_server.domains.reservations.domain.ReservationHistoryRepository
import yousang.rest_server.domains.reservations.domain.ReservationRepository
import yousang.rest_server.domains.reservations.domain.SeatRepository
import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.SeatEntity
import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity as ReservableResourceEntity1

@Repository
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val reservationCustomRepository: ReservationCustomRepository,
) : ReservationRepository {
    override fun save(reservationEntity: ReservationEntity): ReservationEntity {
        return reservationJpaRepository.save(reservationEntity)
    }

    override fun findByUserId(userId: Long): List<ReservationEntity> {
        return reservationCustomRepository.findByUserId(userId)
    }

    override fun findById(reservationId: Long): ReservationEntity? {
        return reservationCustomRepository.findById(reservationId)
    }
}

@Repository
class SeatRepositoryImpl(
    private val seatJpaRepository: SeatJpaRepository, private val seatCustomRepository: SeatCustomRepository
) : SeatRepository {
    override fun findById(seatId: Long): SeatEntity? {
        return seatCustomRepository.findById(seatId)
    }

    override fun findAllByResourceId(reservableResourceId: Long): List<SeatEntity> {
        return seatCustomRepository.findAllByResourceId(reservableResourceId)
    }

    override fun save(seatEntity: SeatEntity): SeatEntity {
        return seatJpaRepository.save(seatEntity)
    }

    override fun findByIdWithLock(seatId: Long): SeatEntity? {
        return seatCustomRepository.findByIdWithLock(seatId)
    }
}

@Repository
class ReservationHistoryRepositoryImpl(
    private val reservationHistoryJpaRepository: ReservationHistoryJpaRepository,
    private val reservationHistoryCustomRepository: ReservationHistoryCustomRepository
) : ReservationHistoryRepository {
    override fun save(reservationHistoryEntity: ReservationHistoryEntity): ReservationHistoryEntity {
        return reservationHistoryJpaRepository.save(reservationHistoryEntity)
    }

    override fun findByReservationId(reservationId: Long): ReservationHistoryEntity? {
        return reservationHistoryCustomRepository.findByReservationId(reservationId)
    }

    override fun findByTimestampAfter(timestamp: Long): List<ReservationHistoryEntity> {
        return reservationHistoryCustomRepository.findByTimestampAfter(timestamp)
    }
}

@Repository
class ReservableResourceRepositoryImpl(
    private val reservableResourceJpaRepository: ReservableResourceJpaRepository,
    private val reservableResourceCustomRepository: ReservableResourceCustomRepository
) : ReservableResourceRepository {
    override fun findById(reservableResourceId: Long): ReservableResourceEntity1? {
        return reservableResourceCustomRepository.findById(reservableResourceId)
    }

    override fun save(reservableResourceEntity: ReservableResourceEntity): ReservableResourceEntity {
        return reservableResourceJpaRepository.save(reservableResourceEntity)
    }

    override fun findByName(name: String): List<ReservableResourceEntity> {
        return reservableResourceCustomRepository.findByName(name)
    }

    override fun findByDateRange(startDate: Long, endDate: Long): List<ReservableResourceEntity> {
        return reservableResourceCustomRepository.findByDateRange(startDate, endDate)
    }
}