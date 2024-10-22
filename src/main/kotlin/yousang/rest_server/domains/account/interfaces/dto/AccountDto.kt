package yousang.rest_server.domains.user.interfaces.dto

import yousang.rest_server.domains.account.domain.models.PaymentStatus

data class ChargeBalanceRequest(
    val amount: Long
)

data class PaymentRequest(
    val userId: Long,
    val seatId: Long,
    val amount: Long,
    val paymentAt: Long,
)

data class PaymentResponse(
    val paymentId: Long,
    val userId: Long,
    val seatId: Long,
    val status: PaymentStatus,
    val message: String
)