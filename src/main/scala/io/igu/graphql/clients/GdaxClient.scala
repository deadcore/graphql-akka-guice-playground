package io.igu.graphql.clients

import java.time.Instant
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling._
import akka.stream.ActorMaterializer
import com.google.inject.ImplementedBy
import io.igu.graphql.clients.model.Fill
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[GdaxClientImpl])
trait GdaxClient {
  def fills: Future[Seq[Fill]]
}

class GdaxClientImpl @Inject()(implicit actorSystem: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContext) extends GdaxClient with SprayJsonSupport {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val secretKey = System.getenv("GDAX_SECRET")


  def fills: Future[List[Fill]] = {
    val timestamp = Instant.now().getEpochSecond.toString
    val request = HttpRequest(uri = "https://api.gdax.com/fills").withHeaders(
      RawHeader("CB-ACCESS-KEY", System.getenv("GDAX_KEY")),
      RawHeader("CB-ACCESS-SIGN", generateSignature("/fills", "GET", "", timestamp)),
      RawHeader("CB-ACCESS-TIMESTAMP", timestamp),
      RawHeader("CB-ACCESS-PASSPHRASE", System.getenv("GDAX_PASSPHARSE"))
    )

    logger.info(s"Request: $request")

    Http().singleRequest(request).flatMap {
        case HttpResponse(OK, headers, entity, protocol) => Unmarshal(entity).to[List[Fill]]
        case HttpResponse(Unauthorized, headers, entity, protocol) => throw new RuntimeException(s"Oh no you were unauthorised. The response was this: $entity")
        case HttpResponse(_, headers, entity, protocol) => throw new RuntimeException(s"Oh no some really did go wrong. The response was this: $entity")
      }
  }

  private def generateSignature(requestPath: String, method: String, body: String, timestamp: String): String = {
    val prehash = timestamp + method.toUpperCase + requestPath + body
    val secretDecoded = Base64.getDecoder.decode(secretKey)
    val keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256")
    val sha256 = Mac.getInstance("HmacSHA256")
    sha256.init(keyspec)
    Base64.getEncoder.encodeToString(sha256.doFinal(prehash.getBytes))
  }

}