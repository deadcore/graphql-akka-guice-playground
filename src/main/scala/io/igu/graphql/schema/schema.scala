package io.igu.graphql

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import io.igu.graphql.actors.QueryActions
import io.igu.graphql.service.ApplicationContext
import sangria.schema.{Argument, Field, IntType, ListType, ObjectType, OptionInputType, OptionType, StringType, fields}

import scala.concurrent.duration.DurationInt

package object schema {

  private implicit val timeout: Timeout = 5 seconds

  val IdArg = Argument("id", StringType)
  val OffsetArg = Argument("offset", OptionInputType(IntType), 0)
  val LimitArg = Argument("limit", OptionInputType(IntType), 100)

  def entityFields[T](name: String, tpe: ObjectType[ApplicationContext, T], actor: ActorRef) = fields[ApplicationContext, Unit](
    Field(name, OptionType(tpe), arguments = IdArg :: Nil, resolve = c => (actor ? QueryActions.Get(c.arg(IdArg))).mapTo[Option[T]]),
    Field(name + "s", ListType(tpe), arguments = OffsetArg :: LimitArg :: Nil, resolve = c => (actor ? QueryActions.List(c.arg(OffsetArg), c.arg(LimitArg))).mapTo[Seq[T]])
  )
}
