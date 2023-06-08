package woowacourse.shopping.data.cart

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object MockCartServer {
    val server = MockWebServer()
    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            return when {
                path == "/cart-items" && request.method == "GET" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(getCartProducts())
                }
                path == "/cart-items" && request.method == "POST" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Location", "/cart-items/1")
                        .setResponseCode(200)
                }
                path == "/cart-items/10" && request.method == "PATCH" -> {
                    MockResponse()
                        .setResponseCode(200)
                }
                path == "/cart-items/10" && request.method == "DELETE" -> {
                    MockResponse()
                        .setResponseCode(200)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun getCartProducts(): String {
        return """
                [ {
                  "id" : 1,
                  "quantity" : 10,
                  "product" : {
                    "id" : 1,
                    "name" : "치킨",
                    "price" : 10000,
                    "imageUrl" : "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80"
                  }
                }, {
                  "id" : 2,
                  "quantity" : 4,
                  "product" : {
                    "id" : 2,
                    "name" : "샐러드",
                    "price" : 2000,
                    "imageUrl" : "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80"
                  }
                } ]
            """.trimIndent()
    }

    init {
        server.dispatcher = dispatcher
        server.start()
    }
}