package io.igu.graphql.model

import io.igu.graphql.service.ApplicationContext
import sangria.macros.derive.{GraphQLDescription, GraphQLName, deriveObjectType}

@GraphQLName("Address")
@GraphQLDescription("An address of the system")
case class Address(street: String, city: String, state: String, zip: String, country: String)

object Address {
  implicit val Type = deriveObjectType[ApplicationContext, Address]()
}