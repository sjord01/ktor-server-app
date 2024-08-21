package example.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// import io.ktor.http.* -> to make use of ContentType

fun Application.configureRouting() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }

    routing {

        /* Invoking staticResources() tells Ktor that we want our application to be
        able to provide standard website content, such as HTML and JavaScript files.
        Although this content may be executed within the browser, it is considered
        static from the server's point of view.

        The URL /content specifies the path that should be used to fetch this content.

        The path mycontent is the name of the folder within which the static content
        will live. Ktor will look for this folder within the resources directory.
        */
        staticResources("/content", "mycontent")

        get("/") {
            call.respondText("Hello World!")
        }

        get("/ktor-page-01") {
            val text = "<h1>Hello From Ktor</h1>"
            val type = ContentType.parse("text/html")
            call.respondText(text, type)
        }

        get("/error-test") {
            throw IllegalStateException("Too Busy")
        }
    }
}