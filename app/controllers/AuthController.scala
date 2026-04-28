package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.AuthService
import dtos.{CreateUserRequest, LoginRequest}

@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents, authService: AuthService)extends BaseController {

  def register: Action[JsValue] = Action(parse.json){request =>
    request.body.validate[CreateUserRequest].fold(errors =>{
      BadRequest(Json.obj("status"->"Error","Message"->JsError.toJson(errors)))
    }, registerReq => {
      try{
        val response = authService.register(registerReq)
        Ok(Json.toJson(response))
      } catch {
        case e: Exception =>
          InternalServerError(Json.obj("message"->s"Registartion failed: ${e.getMessage}"))
      }
    })
  }

  def login: Action[JsValue] = Action(parse.json){request =>
    request.body.validate[LoginRequest].fold(errors =>{
      BadRequest(Json.obj("status" -> "Error","Message"->JsError.toJson(errors)))
    }, loginReq =>{
      try {
        // authService.login returns Option[AuthResponse]
        authService.login(loginReq).map { response =>
          // Case: Some(AuthResponse) -> Password was correct
          println("Logged in successfully")
          Ok(Json.toJson(response))


        }.getOrElse {
          // Case: None -> User not found OR password wrong
          println("Failed to login , invalid email or password")
          Unauthorized(Json.obj("message" -> "Invalid email or password"))
        }
      } catch {
        case e: Exception =>
          InternalServerError(Json.obj("message" -> s"Login failed: ${e.getMessage}"))
      }
    })
  }

}
