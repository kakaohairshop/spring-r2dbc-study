package kr.co.hasys.springr2dbcstudy.styler

import kr.co.hasys.springr2dbcstudy.shop.Shop
import org.springframework.data.annotation.Id

data class Styler(@Id val id: String, val name: String, val shopId: String)

/**
 * R2dbcBadGrammarException: Column "STYLER.SHOP" 예외 발생
 */
//data class Styler(@Id val id: String, val name: String, val shop: Shop)