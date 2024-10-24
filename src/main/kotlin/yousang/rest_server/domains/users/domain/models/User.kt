package yousang.rest_server.domains.users.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yousang.rest_server.common.domain.models.BaseEntity

@Entity
@Table(name = "tbl_users")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "username", nullable = false, unique = true) var username: String,
    @Column(name = "email", nullable = false, unique = true) var email: String,
    @Column(name = "password", nullable = false) var password: String,
    @Column(name = "uuid", nullable = false, unique = true) var uuid: String? = null,
    @Column(name = "access_token") var accessToken: String? = null,
    @Column(name = "refresh_token") var refreshToken: String? = null,
    @Column(name = "account_id", nullable = true) var accountId: Long? = null // store id of AccountEntity
) : BaseEntity() {

}