package io.igu.graphql.providers

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.google.inject._
import org.slf4j.LoggerFactory

import scala.reflect.ClassTag

class ActorProvider[T <: Actor] @Inject()(m: TypeLiteral[T], injector: Injector, system: ActorSystem) extends Provider[ActorRef] {

  implicit val classTag: ClassTag[T] = ClassTag.apply(m.getRawType)

  private val logger = LoggerFactory.getLogger(this.getClass)

  def get: ActorRef = {
    logger.info(s"Binding instance of actor [$key]]")
    system.actorOf(Props[T](injector.getInstance(key)))
  }

  private def key: Key[T] = Key.get(m)
}