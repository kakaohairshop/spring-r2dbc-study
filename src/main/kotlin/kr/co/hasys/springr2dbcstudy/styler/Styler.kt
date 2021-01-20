package kr.co.hasys.springr2dbcstudy.styler

import org.springframework.data.annotation.Id

data class Styler(@Id val id: String?, var name: String, var shopId: String)