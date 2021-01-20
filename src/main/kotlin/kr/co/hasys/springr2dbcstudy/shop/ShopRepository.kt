package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ShopRepository : R2dbcRepository<Shop, String> {

    fun findByName(name: String): Flux<Shop>

    fun findFirstByName(name: String): Mono<Shop>
}