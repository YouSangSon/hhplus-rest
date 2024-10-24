package yousang.rest_server.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import yousang.rest_server.common.utils.logger

@Component
class LoggingInterceptor : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest, response: HttpServletResponse, handler: Any
    ): Boolean {
        request.setAttribute("startTime", System.currentTimeMillis())
        logger.info { "Incoming request: ${request.method} ${request.requestURI}" }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?
    ) {
        val startTime = request.getAttribute("startTime") as Long
        val duration = System.currentTimeMillis() - startTime

        logger.info { "Completed request: ${request.method} ${request.requestURI} in $duration ms" }
    }
}