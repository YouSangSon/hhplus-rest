package yousang.rest_server.domains.queue.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.queue.domain.QueueService
import yousang.rest_server.domains.queue.domain.models.Queue
import yousang.rest_server.domains.user.domain.UserService
import yousang.rest_server.domains.user.domain.models.User

@Service
class QueueFacade(
    private val queueService: QueueService,
    private val userService: UserService,
) {
    @Transactional(readOnly = true)
    fun findUserAndQueue(userId: Long): Pair<User, Queue> {
        return Pair(userService.findById(userId), queueService.findByUserId(userId))
    }
}