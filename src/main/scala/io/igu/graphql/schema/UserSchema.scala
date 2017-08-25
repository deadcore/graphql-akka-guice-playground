package io.igu.graphql.schema

import javax.inject.Named

import akka.actor.ActorRef
import com.google.inject.Inject
import io.igu.graphql.model.{Address, Role, User}
import io.igu.graphql.service.ApplicationContext
import sangria.macros.derive
import sangria.macros.derive.deriveObjectType

class UserSchema @Inject()(@Named("users-actor") actor: ActorRef) extends Query {
  val query = entityFields[User]("user", User.Type, actor)
}
