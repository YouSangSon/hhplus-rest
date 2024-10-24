package yousang.rest_server.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val userId: Long, val uuid: String
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return emptyList() // 권한이 필요한 경우 수정
    }

    override fun getPassword(): String? {
        return null // 비밀번호는 사용하지 않거나, 필요한 경우 반환
    }

    override fun getUsername(): String {
        return userId.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true // 계정 만료 여부를 관리하는 경우 수정
    }

    override fun isAccountNonLocked(): Boolean {
        return true // 계정 잠금 여부를 관리하는 경우 수정
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true // 자격 증명 만료 여부를 관리하는 경우 수정
    }

    override fun isEnabled(): Boolean {
        return true // 계정 활성화 여부를 관리하는 경우 수정
    }
}