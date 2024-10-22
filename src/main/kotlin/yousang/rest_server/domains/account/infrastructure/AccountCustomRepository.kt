package yousang.rest_server.domains.user.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AccountCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {}