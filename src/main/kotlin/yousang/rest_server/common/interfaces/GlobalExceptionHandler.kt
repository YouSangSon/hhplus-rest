package yousang.rest_server.common.interfaces

import org.hibernate.QueryException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import yousang.rest_server.common.dto.Response
import yousang.rest_server.common.utils.logger

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Response<Nothing>> {
        val response = Response<Nothing>(
            result = "error", message = "Invalid request"
        )

        logger.error { ex.message }

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Response<Nothing>> {
        val response = Response<Nothing>(
            result = "error", message = "Internal server error"
        )

        logger.error { ex.message }

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(QueryException::class)
    fun handleQueryException(ex: QueryException): ResponseEntity<Response<Nothing>> {
        val response = Response<Nothing>(
            result = "error", message = "Internal server error"
        )

        logger.error { ex.message }

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotImplementedError::class)
    fun handleNotImplementedError(ex: NotImplementedError): ResponseEntity<Response<Nothing>> {
        val response = Response<Nothing>(
            result = "error", message = "Not implemented"
        )

        logger.error { ex.message }

        return ResponseEntity(response, HttpStatus.NOT_IMPLEMENTED)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<Response<Nothing>> {
        val response = Response<Nothing>(
            result = "error", message = "Not found"
        )

        logger.error { ex.message }

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}