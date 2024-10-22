package yousang.rest_server.domains.user.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PaymentCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {}