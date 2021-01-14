package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

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
    fun get2(): Flux<ShopResponse> = shopRepository.findAll().map { ShopResponse(it.id, it.name) }
}