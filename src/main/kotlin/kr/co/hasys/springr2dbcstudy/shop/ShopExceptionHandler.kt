package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ShopExceptionHandler {

    @ExceptionHandler(value = [ShopNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound() {

    }

    @ExceptionHandler(value = [ShopHasStylerException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest() {

    }

}