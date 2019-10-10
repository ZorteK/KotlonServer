package zortek.openclassroom

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.NotFoundException
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import zortek.openclassroom.extension.debug
import zortek.openclassroom.model.Cours

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(ContentNegotiation) {
        jackson {}
    }

    install(StatusPages) {
        exception<Throwable> { e ->
            if(e is NotFoundException){
                call.response.status(HttpStatusCode.NotFound)
                call.respond(mapOf("Statut" to HttpStatusCode.NotFound.value, "Message " to e.localizedMessage ))
            }else {
                call.response.status(HttpStatusCode.InternalServerError)
                call.respond(e)
            }
        }
    }


    val client = HttpClient {}

    routing {
        get("/") {
            call.respondText("Welcome to OpenClassrooms brand new server !")
        }

        get("/course/top") {
            call.respond(tousLesCours.filter(Cours::isActif))
        }

        get("/course/{id}") {
            val cours = tousLesCours
                    .filter { Integer.valueOf(call.parameters["id"]) == it.id }
                    .getOrElse(0) { throw NotFoundException("No course where found...") }
            cours.debug()
            call.respond(cours)
        }
    }
}
