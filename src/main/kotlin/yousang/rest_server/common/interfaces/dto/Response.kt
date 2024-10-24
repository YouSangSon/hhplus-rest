package yousang.rest_server.common.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val result: String, val data: T? = null, val message: String? = null
)