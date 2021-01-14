package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository : R2dbcRepository<Shop, String>