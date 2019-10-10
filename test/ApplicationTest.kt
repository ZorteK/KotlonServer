package zortek.openclassroom

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Welcome to OpenClassrooms brand new server !", response.content)
            }
        }
    }

    @Test
    fun test_top() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/course/top").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("""[{"id":1,"title":"Je suis un cours","actif":true},{"id":2,"title":"Je suis un long","actif":true}]""", response.content)
            }
        }
    }

    @Test
    fun test_search_ok() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/course/2").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("""{"id":2,"title":"Je suis un long","actif":true}""", response.content)
            }
        }
    }

    @Test
    fun test_search_ko() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/course/22").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("""{"Statut":404,"Message ":"No course where found..."}""", response.content)
            }
        }
    }
}
