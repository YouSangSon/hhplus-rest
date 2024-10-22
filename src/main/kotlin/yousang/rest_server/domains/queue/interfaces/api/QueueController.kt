package yousang.rest_server.domains.queue.interfaces.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yousang.rest_server.common.dto.Response
import yousang.rest_server.domains.queue.application.QueueFacade
import yousang.rest_server.domains.queue.interfaces.dto.QueueDto
import yousang.rest_server.security.JwtTokenProvider

@RestController
@RequestMapping("/api/v1/queue")
class QueueController(
    private val queueFacade: QueueFacade,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/issue-token")
    fun issueQueueToken(@RequestParam userId: Long): ResponseEntity<Response<String>> {
        val (user, queue) = queueFacade.findUserAndQueue(userId)
        val token = jwtTokenProvider.createQueueToken(user.uuid, queue)

        return ResponseEntity.ok(Response("Success", token))
    }

    @GetMapping("/status")
    fun getQueueStatus(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Response<QueueDto>> {
        val token = authHeader.replace("Bearer ", "")
        val claims = jwtTokenProvider.getClaims(token)
        val queuePosition = claims["queuePosition"] as Int
        val expiredAt = claims["expiredAt"] as Long

        return ResponseEntity.ok(Response("Success", QueueDto(queuePosition, expiredAt)))
    }
}