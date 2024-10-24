package yousang.rest_server.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.users.domain.UserService

@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userId = username.toLongOrNull() ?: throw UsernameNotFoundException("invalid user ID format.")
        val user = userService.getUserById(userId)

        return CustomUserDetails(user.id!!, user.uuid!!)
    }
}