package io.igu.graphql.actors

import javax.inject.{Inject, Singleton}

import akka.actor.Actor
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, OK}
import io.igu.graphql.service.ApplicationContext
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.sprayJson._
import sangria.parser.{QueryParser, SyntaxError}
import spray.json.{JsArray, JsNumber, JsObject, JsString, JsValue}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class GraphQLActor @Inject()(executor: Executor[ApplicationContext, Unit], repository: ApplicationContext)(implicit executionContext: ExecutionContext) extends Actor with SprayJsonSupport {

  def receive: PartialFunction[Any, Unit] = {
    case GraphQLActor.Request(query, operation, vars) => sender() ! handle(query, operation, vars)
  }

  private def handle(query: String, operation: Option[String], vars: JsObject): ToResponseMarshallable = QueryParser.parse(query) match {
    case Success(queryAst) => execute(queryAst, operation, vars)
      .map(OK -> _)
      .recover {
        case error: QueryAnalysisError => BadRequest -> error.resolveError
        case error: ErrorWithResolver => InternalServerError -> error.resolveError
      }
    case Failure(error: SyntaxError) => BadRequest -> JsObject(
      "syntaxError" → JsString(error.getMessage),
      "locations" → JsArray(JsObject(
        "line" → JsNumber(error.originalError.position.line),
        "column" → JsNumber(error.originalError.position.column)))
    )
  }

  private def execute(queryAst: Document, operation: Option[String], vars: JsObject): Future[JsValue] = executor.execute(queryAst, repository, (), operation, vars)
}

object GraphQLActor {

  case class Request(query: String, operation: Option[String], vars: JsObject)

}