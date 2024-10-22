package yousang.rest_server.domains.user.interfaces.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
//    private val userFacade: UserFacade
) {
//    @PostMapping("/sign-up")
//    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<Response<String>> {
//        val user = userFacade.signUp(signUpRequest)
//        logger.debug { "sign-up: $user" }
//        return ResponseEntity.ok(Response("success", message = "sign-up Successful"))
//    }
//
//    @PostMapping("/login")
//    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Response<String>> {
//        val user = userFacade.login(loginRequest)
//        logger.debug { "login: $user" }
//        return ResponseEntity.ok(Response("success", message = "login Successful"))
//    }
}