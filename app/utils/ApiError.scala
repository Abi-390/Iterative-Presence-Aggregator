package utils

import play.api.libs.json._

case class ApiError(
                     message: String
                   )

object ApiError {
  implicit val writes: Writes[ApiError] = Json.writes[ApiError]
}