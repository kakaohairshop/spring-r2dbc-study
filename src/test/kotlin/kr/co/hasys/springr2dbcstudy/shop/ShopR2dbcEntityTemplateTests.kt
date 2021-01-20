package kr.co.hasys.springr2dbcstudy.shop

import io.r2dbc.spi.ConnectionFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update.update


@SpringBootTest
class ShopR2dbcEntityTemplateTests {

    @Autowired
    private lateinit var connectionFactory: ConnectionFactory

    @Test
    fun insert_select() {

        R2dbcEntityTemplate(connectionFactory)
                .insert(Shop::class.java)
                .using(Shop("Jane"))
                .block()

        val result = R2dbcEntityTemplate(connectionFactory)
                .selectOne(query(where("name").`is`("Jane")), Shop::class.java)
                .block()

        assertThat(result!!.name).isEqualTo("Jane")
    }

    @Test
    fun update() {

        R2dbcEntityTemplate(connectionFactory)
                .update(Shop::class.java)
                .matching(query(where("id").`is`("1")))
                .apply(update("name", "John"))
                .block()

        val result = R2dbcEntityTemplate(connectionFactory)
                .selectOne(query(where("id").`is`("1")), Shop::class.java)
                .block()

        assertThat(result!!.name).isEqualTo("John")
    }

    @Test
    fun insert_delete(){

        R2dbcEntityTemplate(connectionFactory)
                .insert(Shop::class.java)
                .using(Shop("Jane"))
                .block()

        R2dbcEntityTemplate(connectionFactory)
                .delete(Shop::class.java)
                .matching(query(where("id").`is`("2")))
                .all()
                .block()

        val result = R2dbcEntityTemplate(connectionFactory)
                .selectOne(query(where("id").`is`("2")), Shop::class.java)
                .block()

        assertThat(result).isNull()
    }


}