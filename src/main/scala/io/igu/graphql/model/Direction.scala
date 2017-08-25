package io.igu.graphql.model

import sangria.macros.derive
import sangria.macros.derive.GraphQLName
import sangria.schema.EnumType

@GraphQLName("Direction")
object Direction extends Enumeration {
  val BUY, SELL = Value

  implicit val directionType: EnumType[Direction.Value] = derive.deriveEnumType[Direction.Value]()
}