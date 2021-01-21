package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

/**
 * DatabaseClient
 */
@RestController
class ShopControllerV1 {

    @Autowired
    private lateinit var dataBaseClient: DatabaseClient

    @GetMapping("/v1/shops")
    fun get(): Flux<ShopResponse> = dataBaseClient.sql("select * from shop").map { t, u -> ShopResponse(t.get("id", String::class.java)!!, t.get("name", String::class.java)!!) }.all()

}