package io.igu.graphql.model

import io.igu.graphql.service.ApplicationContext
import sangria.macros.derive.{GraphQLDescription, GraphQLName, deriveObjectType}

@GraphQLName("Transaction")
@GraphQLDescription("A transaction")
case class Transaction(direction: Direction.Value) {

}

object Transaction {
  implicit val objectType = deriveObjectType[ApplicationContext, Transaction]()
}