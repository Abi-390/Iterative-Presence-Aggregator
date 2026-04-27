package utils

import dtos.AuthResponse
import play.api.mvc._
import play.api.libs.json._
import play.api.http.Status

import scala.concurrent.{ExecutionContext, Future}

object ApiHandler {

  def handle[T](
                 result: Future[Either[ApiError, T]],
                 successStatus: Int = Status.OK
               )(implicit writes: Writes[T], ec: ExecutionContext): Future[Result] = {

    result.map {
      case Right(data) =>
        Results.Status(successStatus)(
          Json.toJson(ApiSuccess(data = data))
        )

      case Left(error) =>
        Results.BadRequest(
          Json.toJson(ApiFailure(error = error))
        )
    }
  }

  def handleMessage(result: AuthResponse)(implicit ec: ExecutionContext): Future[Result] = {

//    result.map {
//      case Right(authResponse: AuthResponse) =>
//
//
//      case Left(error) =>
//        Results.BadRequest(
//          Json.toJson(ApiFailure(error = error))
//        )
//    }
    Future.successful(
      Results.Ok(
        Json.toJson(ApiSuccess(data = result))
      )
    )

  }

  def validationError(errors: JsValue): Result = {
    Results.BadRequest(
      Json.toJson(
        ApiFailure(
          error = ApiError(
            message = "Invalid request payload",
            code = "VALIDATION_ERROR"
          )
        )
      )
    )
  }
}