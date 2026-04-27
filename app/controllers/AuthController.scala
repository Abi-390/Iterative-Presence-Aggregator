package controllers

import actions.AuthAction
import javax.inject._

import concurrent.{ExecutionContext, Future}
import play.api.mvc._
import play.api.libs.json._
import play.api.Logging

import dtos._
import utils.ApiHandler
import services.AuthService

@Singleton
class AuthController @Inject()(cc: ControllerComponents, authService: AuthService, authAction: AuthAction)(implicit ec: ExecutionContext) extends AbstractController(cc) with Logging {

  implicit val createUserReads: Reads[CreateUserRequest] = Json.reads[CreateUserRequest]
  implicit val loginReads: Reads[LoginRequest] = Json.reads[LoginRequest]
  implicit val userResponse: Reads[UserResponse] = Json.reads[UserResponse]
  implicit val authResponse: Reads[AuthResponse] = Json.reads[AuthResponse]

  def register = Action.async(parse.json) { request =>
    request.body.validate[CreateUserRequest].fold(
      errors =>
        Future.successful(
          ApiHandler.validationError(JsError.toJson(errors))
        ),
      data =>
        ApiHandler.handleMessage(authService.register(data))
    )
  }
}
