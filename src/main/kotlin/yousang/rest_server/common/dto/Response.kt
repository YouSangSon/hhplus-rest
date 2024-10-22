package yousang.rest_server.common.dto

data class Response<T>(
    val result: String,
    val data: T? = null,
    val message: String? = null
)