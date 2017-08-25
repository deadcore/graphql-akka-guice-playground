package io.igu.graphql.providers

import com.google.inject.Provider

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ExecutionContextProvider extends Provider[ExecutionContext] {
  override def get(): ExecutionContextExecutor = ExecutionContext.global
}
