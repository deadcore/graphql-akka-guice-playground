package io.igu.graphql.providers

import javax.inject.Inject

import com.google.inject.Provider
import io.igu.graphql.schema.SchemaDefinition
import io.igu.graphql.service.ApplicationContext
import sangria.execution.Executor

import scala.concurrent.ExecutionContext

class ExecutorProvider @Inject()(schemaDefinition: SchemaDefinition)(implicit executionContext: ExecutionContext) extends Provider[Executor[ApplicationContext, Unit]] {
  override def get() = Executor(schemaDefinition.schema)
}
