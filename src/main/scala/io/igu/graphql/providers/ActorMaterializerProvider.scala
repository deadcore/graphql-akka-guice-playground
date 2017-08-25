package io.igu.graphql.providers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.inject.{Inject, Provider}

class ActorMaterializerProvider @Inject()(implicit actorSystem: ActorSystem) extends Provider[ActorMaterializer] {
  override def get() = ActorMaterializer()
}
