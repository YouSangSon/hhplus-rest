package yousang.rest_server.interfaces.dto

data class TokenRequest(val userId: String)
data class Token(val token: String, val queuePosition: Int, val estimatedWaitTime: Int)