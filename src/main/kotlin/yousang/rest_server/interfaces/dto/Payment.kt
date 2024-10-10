package yousang.rest_server.interfaces.dto

data class PaymentRequest(val userId: String, val date: String, val seat: Int)