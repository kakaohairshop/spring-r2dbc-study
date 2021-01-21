package kr.co.hasys.springr2dbcstudy.shop

import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


/**
 * R2dbcEntityTemplate
 */
@RestController
class ShopControllerV2(val connectionFactory: ConnectionFactory) {

    private val r2dbcEntityTemplate = R2dbcEntityTemplate(connectionFactory)

    @GetMapping("/v2/shops")
    fun gets() = r2dbcEntityTemplate
            .select(Shop::class.java)
            .all()
            .map { ShopResponse(it.id ?: "", it.name) }


    @GetMapping("/v2/shops/{id}")
    fun get(@PathVariable id: String) = r2dbcEntityTemplate
            .selectOne(query(where("id").`is`(id)), Shop::class.java)
            .switchIfEmpty(Mono.error(ShopNotFoundException()))
            .map { ShopResponse(it.id ?: "", it.name) }

    @PostMapping("/v2/shops")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody request: ShopRequest) = r2dbcEntityTemplate
            .insert(Shop::class.java)
            .using(Shop(request.name))
            .map { ShopResponse(it.id ?: "", it.name) }

    @PutMapping("/v2/shops/{id}")
    fun put(@RequestBody request: ShopRequest, @PathVariable id: String) = r2dbcEntityTemplate
            .selectOne(query(where("id").`is`(id)), Shop::class.java)
            .switchIfEmpty(Mono.error(ShopNotFoundException()))
            .flatMap {
                r2dbcEntityTemplate
                        .update(Shop::class.java)
                        .matching(query(where("id").`is`(id)))
                        .apply(Update.update("name", request.name))
            }

    @DeleteMapping("/v2/shops/{id}")
    fun delete(@PathVariable id: String) = r2dbcEntityTemplate
            .delete(Shop::class.java)
            .matching(query(where("id").`is`(id)))
            .all()
            .onErrorMap { ShopHasStylerException() }
}