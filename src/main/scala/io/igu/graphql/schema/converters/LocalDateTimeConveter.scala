package io.igu.graphql.schema.converters

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import sangria.ast.StringValue
import sangria.schema.ScalarType
import sangria.validation.ValueCoercionViolation

import scala.util.{Failure, Success, Try}

trait LocalDateTimeConveter {

  case object LocalDateTimeCoercionViolation extends ValueCoercionViolation("Date value expected")

  case object LocaleCoercionViolation extends ValueCoercionViolation("Locale value expected")

  private[schema] def parseLocalDateTime(s: String) = Try(LocalDateTime.parse(s)) match {
    case Success(date) => Right(date)
    case Failure(_) => Left(LocalDateTimeCoercionViolation)
  }

  implicit val LocalDateTimeType: ScalarType[LocalDateTime] = ScalarType[LocalDateTime]("LocalDateTime",
    coerceOutput = (value, caps) => value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    coerceUserInput = {
      case s: String => parseLocalDateTime(s)
      case _ => Left(LocalDateTimeCoercionViolation)
    },
    coerceInput = {
      case StringValue(s, _, _) => parseLocalDateTime(s)
      case _ => Left(LocalDateTimeCoercionViolation)
    })

}
