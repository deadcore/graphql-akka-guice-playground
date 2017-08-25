package io.igu.graphql.actors

import javax.inject.Inject

import akka.actor.Actor
import akka.pattern.pipe
import akka.util.Timeout
import io.igu.graphql.clients.GdaxClient

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationLong

class TransactionActor @Inject()(gdaxClient: GdaxClient)(implicit executionContext: ExecutionContext) extends Actor {
  private implicit val timeout: Timeout = 5 seconds

  def receive = {
    case QueryActions.List(offset, limit) => gdaxClient.fills.map(_.map(_.toTransaction)) pipeTo sender()
  }
}
