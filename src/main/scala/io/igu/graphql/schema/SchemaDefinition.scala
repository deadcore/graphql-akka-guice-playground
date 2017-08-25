package io.igu.graphql.schema

import javax.inject.Inject

import io.igu.graphql.service.ApplicationContext
import sangria.schema._

class SchemaDefinition @Inject()(quiries: Set[Query]) {

  val Query = ObjectType("Query",
    quiries.flatMap(_.query).toList
  )

  val schema: Schema[ApplicationContext, Unit] = Schema(Query, None)
}
