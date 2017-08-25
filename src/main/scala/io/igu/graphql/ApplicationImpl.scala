package io.igu.graphql

import javax.inject.Inject

import io.igu.Application

class ApplicationImpl @Inject()(webServer: WebServer) extends Application {
  def start: Unit = webServer.start
}