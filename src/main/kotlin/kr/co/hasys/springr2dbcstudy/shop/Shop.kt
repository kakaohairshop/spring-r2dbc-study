package kr.co.hasys.springr2dbcstudy.shop

import org.springframework.data.annotation.Id

data class Shop(@Id val id: String?, val name: String) {

    constructor(name: String) : this(null, name)
}
