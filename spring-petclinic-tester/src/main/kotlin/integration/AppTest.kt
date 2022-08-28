package integration

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

//private const val HOST = "http://localhost:8080"
private const val HOST = "http://api-gateway:8080"

class AppTest {
    suspend fun canRun() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        while (true) {
            try {
                val ownerResponse = client.request("$HOST/api/customer/owners")
                val ownerBody: JsonArray = ownerResponse.body()
                println("Found ${ownerBody.size} owners")

                val ownerByIdResponse = client.request("$HOST/api/gateway/owners/4")
                val ownerById: JsonObject = ownerByIdResponse.body()
                println("Owner by id returned ${ownerById["firstName"]?.jsonPrimitive?.content}")

                val editOwnerRequest = JsonObject(
                    mapOf
                        (
                        "firstName" to JsonPrimitive("Harold"),
                        "lastName" to JsonPrimitive("Davies"),
                        "address" to JsonPrimitive("564 Friendly St"),
                        "city" to JsonPrimitive("Windsor"),
                        "id" to JsonPrimitive(5),
                        "telephone" to JsonPrimitive("111222333444"),
                    )
                )
                val editOwnerResponse = client.put("$HOST/api/customer/owners/4") {
                    contentType(ContentType.Application.Json)
                    setBody(editOwnerRequest)
                }
                if (editOwnerResponse.status.isSuccess()) {
                    println("Edit owner success")
                } else {
                    val error = editOwnerResponse.bodyAsText()
                    println("Edit owner error $error")
                }

                val vetsResponse = client.request("$HOST/api/vet/vets")
                val vetsBody: JsonArray = vetsResponse.body()
                println("Found ${vetsBody.size} vets")

//            val name = body[0].jsonObject["firstName"]?.jsonPrimitive?.content
//            println("response: ${response.status} ${body}")
//            println("$name")
            } catch (e: Throwable) {
                println("Warning: ${e.message}")
            }
            delay(5000)
        }
    }
}
