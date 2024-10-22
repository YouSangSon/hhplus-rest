package yousang.rest_server.domains.user.application

//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//import yousang.rest_server.domains.user.domain.AccountService
//import yousang.rest_server.domains.user.domain.UserService
//import yousang.rest_server.domains.user.domain.models.AccountEntity
//import yousang.rest_server.domains.user.domain.models.User
//import yousang.rest_server.domains.user.domain.models.UserEntity
//import yousang.rest_server.domains.user.interfaces.dto.LoginRequest
//import yousang.rest_server.domains.user.interfaces.dto.SignUpRequest
//
//@Service
//class UserFacade(
//    private val userService: UserService, private val accountService: AccountService
//) {
//    @Transactional
//    fun signUp(signUpRequest: SignUpRequest): User {
//        val user = userService.findByUsername(signUpRequest.username)
//        if (user != null) {
//            throw IllegalArgumentException("the user already exists.")
//        }
//
//        val userEntity = UserEntity(
//            username = signUpRequest.username, password = signUpRequest.password, email = signUpRequest.email
//        )
//        userService.save(userEntity)
//
//        val accountEntity = AccountEntity(user = userEntity, balance = 0L)
//        userEntity.account = accountEntity
//
//        accountService.save(accountEntity)
//
//        return userEntity.toModel()
//    }
//
//    @Transactional(readOnly = true)
//    fun login(loginRequest: LoginRequest): User {
//        val user = userService.findByEmail(loginRequest.email) ?: throw IllegalArgumentException("no user found")
//
//        if (user.password != loginRequest.password) {
//            throw IllegalArgumentException("the passwords don't match.")
//        }
//
//        return user.toModel()
//    }
//}