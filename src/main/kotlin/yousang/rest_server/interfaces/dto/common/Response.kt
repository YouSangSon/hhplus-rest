package yousang.rest_server.interfaces.dto.common

data class Response<T>(
    val result: String,
    val body: T? = null,
    val message: String? = null
)