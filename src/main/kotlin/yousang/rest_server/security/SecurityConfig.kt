package yousang.rest_server.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenFilter: JwtTokenFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }  // Disable CSRF as we are using JWT (stateless)
            .cors{} // Enable CORS, you can customize the allowed origins if needed
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless sessions
            }.authorizeHttpRequests { auth ->
                auth.requestMatchers(*ALLOWED_URLS).permitAll()  // Public endpoints
                    .anyRequest().authenticated()  // All other endpoints require authentication
            }.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)  // Add JWT filter

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    companion object {
        private val ALLOWED_URLS = arrayOf(
            "",
            "/api/v1/users/register",
            "/api/v1/users/login",
            "/swagger-ui/**",
            "/css/**",
            "/images/**",
            "/js/**",
        )
    }
}