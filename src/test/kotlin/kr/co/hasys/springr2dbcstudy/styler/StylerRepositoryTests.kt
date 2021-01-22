package kr.co.hasys.springr2dbcstudy.styler

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
class StylerRepositoryTests {

    @Autowired
    private lateinit var sut: StylerRepository

    private lateinit var styler: Styler

    @BeforeEach
    fun before() {
        styler = Styler(null, "테스트", "1")
        styler = sut.save(styler).block()!!
    }

    @Test
    fun updateWithModifying() {
        sut.updateWithModifying("P", styler.id!!)
                .`as`(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete()
    }

    @Test
    fun updateWithoutModifying() {

        // rowUpdated를 알 수 없다. 0 리턴
        sut.updateWithoutModifying("S", styler.id!!)
                .`as`(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete()
    }
}