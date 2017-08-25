package io.igu.graphql

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.google.inject.{AbstractModule, Scopes, TypeLiteral}
import io.igu.graphql.actors.{GraphQLActor, TransactionActor, UserActor}
import io.igu.graphql.guice.AkkaModule
import io.igu.graphql.providers._
import io.igu.graphql.schema.{Query, TransactionSchema, UserSchema}
import io.igu.graphql.service.ApplicationContext
import net.codingwell.scalaguice.{ScalaModule, ScalaMultibinder}
import sangria.execution.Executor
import sangria.schema.Schema

import scala.concurrent.ExecutionContext

object Module extends AbstractModule with ScalaModule with AkkaModule {

  def configure(): Unit = {

    bind[ActorSystem].toProvider[ActorSystemProvider].in(Scopes.SINGLETON)
    bind[ActorMaterializer].toProvider[ActorMaterializerProvider].in(Scopes.SINGLETON)
    bind[ExecutionContext].toProvider[ExecutionContextProvider].in(Scopes.SINGLETON)
    bind[Route].toProvider[RouteProvider].in(Scopes.SINGLETON)

    bind(new TypeLiteral[Executor[ApplicationContext, Unit]] {}).toProvider(classOf[ExecutorProvider]).in(Scopes.SINGLETON)
    bind(new TypeLiteral[Schema[ApplicationContext, Unit]] {}).toProvider(classOf[SchemaProvider]).in(Scopes.SINGLETON)

    bindActor[GraphQLActor]("graph-ql-actor")
    bindActor[UserActor]("users-actor")
    bindActor[TransactionActor]("transaction-actor")

    val queryMultibinder = ScalaMultibinder.newSetBinder[Query](binder)
    queryMultibinder.addBinding.to[UserSchema]
    queryMultibinder.addBinding.to[TransactionSchema]
  }

}