package io.igu.graphql.actors

object QueryActions {
  case class List(offset: Int, limit: Int)
  case class Get(id: String, version: Option[Long] = None)
}