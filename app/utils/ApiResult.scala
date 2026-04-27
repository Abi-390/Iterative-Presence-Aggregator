package utils

import play.api.libs.json._

case class ApiSuccess[T](
                          success: Boolean = true,
                          data: T
                        )

case class ApiFailure(
                       success: Boolean = false,
                       error: ApiError
                     )

object ApiSuccess {
  implicit def writes[T: Writes]: Writes[ApiSuccess[T]] = Json.writes[ApiSuccess[T]]
}

object ApiFailure {
  implicit val writes: Writes[ApiFailure] = Json.writes[ApiFailure]
}