package yousang.rest_server.domains.users.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.users.domain.models.UserEntity

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {

}