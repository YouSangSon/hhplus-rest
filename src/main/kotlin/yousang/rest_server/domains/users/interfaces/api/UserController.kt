package yousang.rest_server.domains.users.interfaces.api

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yousang.rest_server.common.interfaces.dto.Response
import yousang.rest_server.domains.users.application.UserFacade
import yousang.rest_server.domains.users.interfaces.dto.UserDomainsRequest
import yousang.rest_server.security.CustomUserDetails

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userFacade: UserFacade
) {
    @PostMapping("/register")
    fun register(@RequestBody request: UserDomainsRequest): ResponseEntity<Response<Any>> {
        val response = userFacade.register(request)
        return ResponseEntity.ok(Response("success", data = response))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserDomainsRequest): ResponseEntity<Response<Any>> {
        val response = userFacade.login(request)
        return ResponseEntity.ok(Response("success", data = response))
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: UserDomainsRequest): ResponseEntity<Response<Any>> {
        val response = userFacade.refreshAccessToken(request)
        return ResponseEntity.ok(Response("success", data = response))
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails
        userFacade.logout(userDetails.userId)

        return ResponseEntity.ok(Response(result = "success"))
    }

    @DeleteMapping("/delete")
    fun deleteUser(): ResponseEntity<Response<Any>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails
        userFacade.deleteUser(userDetails.userId)

        return ResponseEntity.ok(Response(result = "success"))
    }
}