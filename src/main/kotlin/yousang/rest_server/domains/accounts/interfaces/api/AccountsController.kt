package yousang.rest_server.domains.users.interfaces.api


import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yousang.rest_server.common.interfaces.dto.Response
import yousang.rest_server.domains.users.application.AccountsFacade
import yousang.rest_server.domains.users.interfaces.dto.AccountDomainsRequest
import yousang.rest_server.security.CustomUserDetails

@RestController
@RequestMapping("/api/v1/accounts")
class AccountsController(
    private val accountsFacade: AccountsFacade
) {
    @PostMapping("/deposit")
    fun deposit(@RequestBody request: AccountDomainsRequest): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        request.userId = userDetails.userId
        val response = accountsFacade.deposit(request)

        return ResponseEntity.ok(Response(result = "success", data = response))
    }

    @PostMapping("/withdraw")
    fun withdraw(@RequestBody request: AccountDomainsRequest): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        request.userId = userDetails.userId
        val response = accountsFacade.withdraw(request)

        return ResponseEntity.ok(Response(result = "success", data = response))
    }

    @GetMapping("/balance")
    fun getBalance(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails

        val response = accountsFacade.getBalance(userDetails.userId)

        return ResponseEntity.ok(Response(result = "success", data = response))
    }
}