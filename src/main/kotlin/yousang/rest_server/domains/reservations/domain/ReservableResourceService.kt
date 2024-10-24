package yousang.rest_server.domains.reservations.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity

@Service
class ReservableResourceService(
    private val reservableResourceRepository: ReservableResourceRepository
) {
    fun getResourceById(resourceId: Long): ReservableResourceEntity {
        try {
            return reservableResourceRepository.findById(resourceId)
                ?: throw Exception("no available resources were found.")
        } catch (e: Exception) {
            throw Exception("no available resources were found.")
        } catch (e: QueryException) {
            throw QueryException("no available resources were found.")
        }
    }
}