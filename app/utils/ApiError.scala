package utils

import play.api.libs.json._

case class ApiError(
                     message: String,
                     code: String = "GENERIC_ERROR"
                   )

object ApiError {
  implicit val writes: Writes[ApiError] = Json.writes[ApiError]
}