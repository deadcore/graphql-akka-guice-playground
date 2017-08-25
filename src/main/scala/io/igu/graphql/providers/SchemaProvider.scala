package io.igu.graphql.providers

import javax.inject.Inject

import com.google.inject.Provider
import io.igu.graphql.schema.SchemaDefinition
import io.igu.graphql.service.ApplicationContext
import sangria.schema.Schema


class SchemaProvider @Inject()(schema: SchemaDefinition) extends Provider[Schema[ApplicationContext, Unit]] {
  override def get(): Schema[ApplicationContext, Unit] = schema.schema
}
