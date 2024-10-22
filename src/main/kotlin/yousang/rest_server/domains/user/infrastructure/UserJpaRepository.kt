package yousang.rest_server.domains.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.user.domain.models.UserEntity

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

}