package io.igu.graphql.routes

import javax.inject.Singleton

import akka.http.scaladsl.server.Directives.{get, getFromResource}
import akka.http.scaladsl.server.Route

@Singleton
class DefaultRoute {

  val route: Route = get {
    getFromResource("web/graphiql.html")
  }

}
