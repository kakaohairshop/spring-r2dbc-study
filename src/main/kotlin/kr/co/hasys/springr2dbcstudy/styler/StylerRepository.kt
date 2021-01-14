package kr.co.hasys.springr2dbcstudy.styler

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface StylerRepository : R2dbcRepository<Styler, String> {

    fun findByShopId(shopId: String): Flux<Styler>

    @Query("SELECT * FROM styler WHERE name = :name")
    fun findByName(name: String): Flux<Styler>
}