package kr.co.hasys.springr2dbcstudy.styler

import org.springframework.data.annotation.Id

data class Styler(@Id val id: String?, val name: String, val shopId: String)