package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * DatabaseClient
 */
@RestController
class ShopControllerV1(val dataBaseClient: DatabaseClient) {

    @GetMapping("/v1/shops")
    fun get() = dataBaseClient.sql("select * from shop")
            .map { t, u -> ShopResponse(t.get("id", String::class.java)!!, t.get("name", String::class.java)!!) }
            .all()

}