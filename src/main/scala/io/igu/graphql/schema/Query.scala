package io.igu.graphql.schema

import io.igu.graphql.service.ApplicationContext
import sangria.schema.Field

trait Query {

  val query: List[Field[ApplicationContext, Unit]]

}
