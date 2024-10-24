package yousang.rest_server.domains.queue.interfaces.api

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yousang.rest_server.common.interfaces.dto.Response
import yousang.rest_server.domains.queue.application.QueueFacade
import yousang.rest_server.security.CustomUserDetails

@RestController
@RequestMapping("/api/v1/queue")
class QueueController(
    private val queueFacade: QueueFacade,
) {
    @PostMapping("/join")
    fun joinQueue(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        val result = queueFacade.joinQueue(userDetails.userId)

        return ResponseEntity.ok(Response("success", data = result))
    }

    @GetMapping("/position")
    fun getQueuePosition(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        val result = queueFacade.getQueuePosition(userDetails.userId)

        return ResponseEntity.ok(Response("success", data = result))
    }
}