package io.igu.graphql.providers

import akka.actor.ActorSystem
import com.google.inject.Provider

class ActorSystemProvider extends Provider[ActorSystem] {
  override def get() = ActorSystem("graphql-server")
}
