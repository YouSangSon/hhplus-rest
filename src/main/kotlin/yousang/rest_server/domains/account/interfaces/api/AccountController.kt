package yousang.rest_server.domains.user.interfaces.api


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yousang.rest_server.common.dto.Response
import yousang.rest_server.common.utils.logger
import yousang.rest_server.domains.user.application.AccountFacade
import yousang.rest_server.domains.user.interfaces.dto.ChargeBalanceRequest

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountFacade: AccountFacade
) {
    @PostMapping("/{userId}/charge")
    fun chargeBalance(
        @PathVariable userId: Long, @RequestBody request: ChargeBalanceRequest
    ): ResponseEntity<Response<String>> {
        val user = accountFacade.chargeBalance(userId, request.amount)
        logger.debug { "${user.email} charged ${request.amount} and became ${user.account?.balance}" }

        return ResponseEntity.ok(Response("success", message = "charge balance successful"))
    }

    @PostMapping("/{userId}/use")
    fun useBalance(
        @PathVariable userId: Long, @RequestBody request: ChargeBalanceRequest
    ): ResponseEntity<Response<String>> {
        val user = accountFacade.useBalance(userId, request.amount)
        logger.debug { "${user.email} used ${request.amount} and became ${user.account?.balance}" }

        return ResponseEntity.ok(Response("success", message = "use balance successful"))
    }

    @GetMapping("/{userId}/balance")
    fun getBalance(@PathVariable userId: Long): ResponseEntity<Response<Long>> {
        val balance = accountFacade.getBalance(userId)
        logger.debug { "${balance.email} has ${balance.account?.balance}" }

        return ResponseEntity.ok(Response("success", data = balance.account?.balance))
    }
}