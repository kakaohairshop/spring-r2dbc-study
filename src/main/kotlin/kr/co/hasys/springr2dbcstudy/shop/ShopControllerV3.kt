package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

/**
 * R2dbcRepository
 */
@RestController
class ShopControllerV3 {

    @Autowired
    private lateinit var shopRepository: ShopRepository

    @GetMapping("/v3/shops")
    fun get(): Flux<ShopResponse> = shopRepository.findAll().map { ShopResponse(it.id ?: "", it.name) }

    @PostMapping("/v3/shops")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody request: ShopRequest): Mono<ShopResponse> =
            shopRepository.save(Shop(request.name)).map { ShopResponse(it.id!!, it.name) }

    @PutMapping("/v3/shops/{id}")
    fun put(@RequestBody request: ShopRequest, @PathVariable id: String): Mono<Shop> {

        return shopRepository.findById(id)
                .switchIfEmpty { throw ShopNotFoundException() }
                .flatMap {
                    it.name = request.name
                    shopRepository.save(it)
                }
    }

    @DeleteMapping("/v3/shops/{id}")
    fun delete(@PathVariable id: String): Mono<Void> {
        return shopRepository.deleteById(id)
                .onErrorMap { ShopHasStylerException() }
    }

}