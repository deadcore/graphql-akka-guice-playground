package io.igu.graphql.guice

import akka.actor.{Actor, ActorRef}
import com.google.inject._
import io.igu.graphql.providers.ActorProvider
import net.codingwell.scalaguice.ScalaModule
import org.slf4j.LoggerFactory

trait AkkaModule extends ScalaModule {
  this: AbstractModule =>

  private val logger = LoggerFactory.getLogger(this.getClass)

  def bindActor[T <: Actor](name: String)(implicit manifest: Manifest[T]): Unit = {
    logger.debug(s"Binding actor ${manifest.runtimeClass.getCanonicalName} to name $name")

    bind[ActorRef]
      .annotatedWithName(name)
      .toProvider[ActorProvider[T]]
  }
}