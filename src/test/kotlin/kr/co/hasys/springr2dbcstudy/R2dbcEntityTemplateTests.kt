package kr.co.hasys.springr2dbcstudy

import io.r2dbc.spi.ConnectionFactory
import kr.co.hasys.springr2dbcstudy.shop.Shop
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Query

@SpringBootTest
class R2dbcEntityTemplateTests {

    @Autowired
    private lateinit var connectionFactory: ConnectionFactory

    @Test
    fun shop_insert_select() {

        R2dbcEntityTemplate(connectionFactory)
                .insert(Shop::class.java)
                .using(Shop("Jane"))
                .block()

        val result = R2dbcEntityTemplate(connectionFactory)
                .select(Shop::class.java)
                .matching(Query.query(where("name").`is`("Jane")).limit(1).offset(0))
                .one()
                .block()

        assertThat(result!!.name).isEqualTo("Jane")
    }

}