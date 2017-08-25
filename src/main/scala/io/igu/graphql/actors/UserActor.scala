package io.igu.graphql.actors

import java.time.LocalDateTime

import akka.actor.Actor
import io.igu.graphql.model.User

class UserActor extends Actor {
  override def receive = {
    case QueryActions.List(offset, limit) => sender() ! Seq(User(
      1,
      email = "jack.liddiard@iiincubator.com",
      created = LocalDateTime.now(),
      firstName = "Jack",
      lastName = "Liddiard",
      roles = None,
      active = true,
      address = None
    ))
  }
}
