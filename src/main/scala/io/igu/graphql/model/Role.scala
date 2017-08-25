package io.igu.graphql.model

import sangria.macros.derive
import sangria.macros.derive.GraphQLName
import sangria.schema.EnumType

@GraphQLName("Role")
object Role extends Enumeration {
  val USER, ADMIN = Value

  implicit val Type: EnumType[Role.Value] = derive.deriveEnumType[Role.Value]()
}