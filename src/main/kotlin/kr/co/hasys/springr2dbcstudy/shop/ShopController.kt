package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

@RestController
class ShopController {

    @Autowired
    private lateinit var dataBaseClient: DatabaseClient

    @Autowired
    private lateinit var shopRepository: ShopRepository

    /**
     * DatabaseClient 버전
     */
    @GetMapping("/v1/shops")
    fun get1(): Flux<ShopResponse> = dataBaseClient.sql("select * from shop").map { t, u -> ShopResponse(t.get("id", String::class.java)!!, t.get("name", String::class.java)!!) }.all()


    /**
     * R2dbcRepository 버전
     */
    @GetMapping("/v2/shops")
    fun get2(): Flux<ShopResponse> = shopRepository.findAll().map { ShopResponse(it.id ?: "", it.name) }

    @PostMapping("/v2/shops")
    @ResponseStatus(HttpStatus.CREATED)
    fun post2(@RequestBody request: ShopRequest): Mono<ShopResponse> =
            shopRepository.save(Shop(request.name)).map { ShopResponse(it.id!!, it.name) }

    @PutMapping("/v2/shops/{id}")
    fun put2(@RequestBody request: ShopRequest, @PathVariable id: String): Mono<Shop> {

        return shopRepository.findById(id)
                .switchIfEmpty { throw ShopNotFoundException() }
                .flatMap {
                    it.name = request.name
                    shopRepository.save(it)
                }.toMono()
    }

    @DeleteMapping("/v2/shops/{id}")
    fun delete2(@PathVariable id: String): Mono<Void> {
        return shopRepository.deleteById(id)
                .onErrorMap { ShopHasStylerException() }
    }

    @ExceptionHandler(value = [ShopNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound() {

    }

    @ExceptionHandler(value = [ShopHasStylerException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest() {

    }

}