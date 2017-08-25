package io.igu.graphql.routes

import javax.inject.Named

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.Inject
import io.igu.graphql.actors.GraphQLActor
import spray.json.{JsObject, JsString, JsValue}

import scala.concurrent.duration.DurationInt

// @Inject()(@Actor(classOf[GraphQLActor]) actor: ActorRef)

class GraphQLRoutes @Inject()(@Named("graph-ql-actor") actor: ActorRef)(implicit val system: ActorSystem) extends SprayJsonSupport {

  private implicit val timeout: Timeout = 5 seconds

  val route = path("graph") {
    post {
      entity(as[JsValue]) { document =>
        val (query, operation, vars) = unmarshalJson(document)
        handle(query, operation, vars)
      }
    }
  }

  private def unmarshalJson(document: JsValue) = {
    val JsObject(fields) = document

    val JsString(query) = fields("query")

    val operation = fields.get("operationName") collect {
      case JsString(op) ⇒ op
    }

    val vars = fields.get("variables") match {
      case Some(obj: JsObject) => obj
      case _ => JsObject.empty
    }

    (query, operation, vars)
  }

  private def handle(query: String, operation: Option[String], vars: JsObject) = {
    val bids = (actor ? GraphQLActor.Request(query, operation, vars)).mapTo[ToResponseMarshallable]
    complete(bids)
  }

}
