package org.poc

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit.WireMockRule
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes=[org.poc.Application::class], webEnvironment = RANDOM_PORT)
class MyServiceTest {

    @LocalServerPort
    var randomServerPort: Int = 0

    @get:Rule
    var wireMockRule = WireMockRule(8181)

    @Test
    fun `Calling foo calls bar on third party service`() {
        wireMockRule.stubFor(WireMock.get("/bar")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""{
                            |"inner": "value"
                        |}""".trimMargin())
                ))

        RestAssured.get("http://localhost:${randomServerPort}/foo")
                .then()
                .body("key", equalTo("value"))

        wireMockRule.verify(getRequestedFor(urlEqualTo("/bar")))
    }

}