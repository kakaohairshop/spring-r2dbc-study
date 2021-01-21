package kr.co.hasys.springr2dbcstudy.shop

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


/**
 * R2dbcEntityTemplate
 */
@RestController
class ShopControllerV2 {

    @Autowired
    private lateinit var connectionFactory: ConnectionFactory

    @GetMapping("/v2/shops")
    fun get(): Flux<ShopResponse> {
        return R2dbcEntityTemplate(connectionFactory)
                .select(Shop::class.java)
                .all()
                .map { ShopResponse(it.id ?: "", it.name) }
    }

    @PostMapping("/v2/shops")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody request: ShopRequest): Mono<ShopResponse> {

        return R2dbcEntityTemplate(connectionFactory)
                .insert(Shop::class.java)
                .using(Shop(request.name))
                .map { ShopResponse(it.id ?: "", it.name) }
    }

    @PutMapping("/v2/shops/{id}")
    fun put(@RequestBody request: ShopRequest, @PathVariable id: String): Mono<Int> {

        val r2dbcEntityTemplate = R2dbcEntityTemplate(connectionFactory)

        return r2dbcEntityTemplate
                .selectOne(query(where("id").`is`(id)), Shop::class.java)
                .switchIfEmpty(Mono.error(ShopNotFoundException()))
                .flatMap {
                    r2dbcEntityTemplate
                            .update(Shop::class.java)
                            .matching(query(where("id").`is`(id)))
                            .apply(Update.update("name", request.name))
                }
    }

    @DeleteMapping("/v2/shops/{id}")
    fun delete(@PathVariable id: String): Mono<Int> {

        return R2dbcEntityTemplate(connectionFactory)
                .delete(Shop::class.java)
                .matching(query(where("id").`is`(id)))
                .all()
                .onErrorMap { ShopHasStylerException() }
    }


}