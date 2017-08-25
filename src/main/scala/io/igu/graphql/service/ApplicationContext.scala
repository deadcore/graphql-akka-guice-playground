package io.igu.graphql.service

import javax.inject.{Inject, Named}

import akka.actor.ActorRef
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ApplicationContextImpl])
trait ApplicationContext {
}

class ApplicationContextImpl extends ApplicationContext {
}
