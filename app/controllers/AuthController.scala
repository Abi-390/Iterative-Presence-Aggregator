package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.AuthService
import dtos.CreateUserRequest

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


}
