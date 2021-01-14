package kr.co.hasys.springr2dbcstudy.shop

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShopResponse(val id: String, val name: String)