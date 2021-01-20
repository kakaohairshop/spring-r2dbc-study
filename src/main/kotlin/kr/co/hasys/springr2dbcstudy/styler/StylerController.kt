package kr.co.hasys.springr2dbcstudy.styler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class StylerController {

    @Autowired
    private lateinit var stylerRepository: StylerRepository

    @GetMapping("/v1/shops/{shopId}/stylers")
    fun get(@PathVariable shopId: String, @RequestParam(required = false) name: String?): Flux<StylerResponse> {

        name?.let { return stylerRepository.findByName(name).map { StylerResponse(it.id ?: "", it.name) } }

        return stylerRepository.findByShopId(shopId).map { StylerResponse(it.id ?: "", it.name) }
    }
}