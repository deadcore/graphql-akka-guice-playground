package io.igu.graphql.model

import java.time.LocalDateTime

import io.igu.graphql.schema.converters
import io.igu.graphql.service.ApplicationContext
import sangria.macros.derive.{GraphQLDescription, GraphQLName, deriveObjectType}
import sangria.schema.ObjectType

@GraphQLName("User")
@GraphQLDescription("A user of the system.")
case class User(id: Long,
                email: String,
                created: LocalDateTime,
                firstName: String,
                lastName: String,
                roles: Option[Seq[Role.Value]],
                active: Boolean,
                address: Option[Address])

object User extends converters.TypeConverters {
  implicit val Type: ObjectType[ApplicationContext, User] = deriveObjectType[ApplicationContext, User]()
}