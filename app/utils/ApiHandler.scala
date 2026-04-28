package utils

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

object ApiHandler {

  def handle[T](
                 result: Future[Either[ApiError, T]]
               )(implicit writes: Writes[T], ec: ExecutionContext): Future[Result] = {

    result.map {
      case Right(data) =>
        Results.Ok(
          Json.toJson(ApiSuccess(data = data))
        )

      case Left(error) =>
        Results.BadRequest(
          Json.toJson(ApiFailure(error = error))
        )
    }
  }

  def validationError(errors: JsValue): Result = {
    Results.BadRequest(
      Json.toJson(
        ApiFailure(
          error = ApiError(
            message = "Invalid request payload"
          )
        )
      )
    )
  }
}
