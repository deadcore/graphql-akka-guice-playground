package io.igu.graphql.schema

import javax.inject.Named

import akka.actor.ActorRef
import com.google.inject.Inject
import io.igu.graphql.model.Transaction
import io.igu.graphql.service.ApplicationContext
import sangria.macros.derive.deriveObjectType
import sangria.schema.Field

class TransactionSchema @Inject()(@Named("transaction-actor") actor: ActorRef) extends Query {
  val query: List[Field[ApplicationContext, Unit]] = entityFields[Transaction]("transaction", Transaction.objectType, actor)
}
