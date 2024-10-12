package yousang.rest_server.concert.interfaces.dto

data class SeatRequest(val userId: String, val date: String, val seat: Int)