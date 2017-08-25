package io.igu

import com.google.inject.ImplementedBy
import io.igu.graphql.ApplicationImpl

@ImplementedBy(classOf[ApplicationImpl])
trait Application {
  def start: Unit
}