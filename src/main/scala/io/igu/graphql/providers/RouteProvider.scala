package io.igu.graphql.providers

import javax.inject.Inject

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Provider
import io.igu.graphql.routes.{DefaultRoute, GraphQLRoutes}

class RouteProvider @Inject()(graphQLRoutes: GraphQLRoutes, defaultRoute: DefaultRoute) extends Provider[Route] {
  override def get(): Route = graphQLRoutes.route ~
    defaultRoute.route
}
