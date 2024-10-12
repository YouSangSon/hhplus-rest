package yousang.rest_server.common.dto

data class Response<T>(
    val result: String,
    val body: T? = null,
    val message: String? = null
)