package utils

import dtos.AuthResponse
import play.api.mvc._
import play.api.libs.json._
import play.api.http.Status

import scala.concurrent.{ExecutionContext, Future}

object ApiHandler {



  def handleMessage[T : Writes](result : T)(implicit ec: ExecutionContext): Future[Result] = {


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