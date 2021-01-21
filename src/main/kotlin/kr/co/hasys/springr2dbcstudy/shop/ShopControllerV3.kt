package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

/**
 * R2dbcRepository
 */
@RestController
class ShopControllerV3(val shopRepository: ShopRepository) {

    @GetMapping("/v3/shops")
    fun gets(): Flux<ShopResponse> = shopRepository.findAll()
            .map { ShopResponse(it.id ?: "", it.name) }

    @GetMapping("/v3/shops/{id}")
    fun get(@PathVariable id: String): Mono<ShopResponse> = shopRepository.findById(id)
            .switchIfEmpty { throw ShopNotFoundException() }
            .map {
                ShopResponse(it.id ?: "", it.name)
            }

    @PostMapping("/v3/shops")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody request: ShopRequest): Mono<ShopResponse> = shopRepository.save(Shop(request.name))
            .map { ShopResponse(it.id!!, it.name) }

    @PutMapping("/v3/shops/{id}")
    fun put(@RequestBody request: ShopRequest, @PathVariable id: String) = shopRepository.findById(id)
            .switchIfEmpty { throw ShopNotFoundException() }
            .flatMap {
                it.name = request.name
                shopRepository.save(it)
            }

    @DeleteMapping("/v3/shops/{id}")
    fun delete(@PathVariable id: String) = shopRepository.deleteById(id)
            .onErrorMap { ShopHasStylerException() }


}