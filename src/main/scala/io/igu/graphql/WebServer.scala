package io.igu.graphql

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Future

class WebServer @Inject()(routes: Route)(implicit as: ActorSystem, am: ActorMaterializer) {
  def start: Future[Http.ServerBinding] = {
//    val clientRouteLogged = DebuggingDirectives.logRequestResult("Client ReST", Logging.InfoLevel)(router.routes)

    Http().bindAndHandle(routes, "localhost", 8080)
  }
}