package kr.co.hasys.springr2dbcstudy.shop

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ShopRepositoryTests {

    @Autowired
    private lateinit var shopRepository: ShopRepository

    @Test
    fun insert_select() {

        shopRepository.save(Shop("Jane")).block()

        val result = shopRepository.findFirstByName("Jane").block()

        assertThat(result!!.name).isEqualTo("Jane")
    }

    @Test
    fun update() {

        val shop = shopRepository.findById("1").block()

        shop!!.name = "John"

        shopRepository.save(shop).block()

        assertThat(shop.name).isEqualTo("John")
    }

    @Test
    fun insert_delete(){

        var shop = shopRepository.save(Shop("Jane")).block()

        shopRepository.delete(shop!!).block()
    }

}