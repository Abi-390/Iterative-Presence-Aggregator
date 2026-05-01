package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.ClassService
import dtos._
import utils.ApiHandler

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ClassController @Inject() (
                                cc:ControllerComponents,
                                classService: ClassService
                                ) (implicit ec:ExecutionContext)
                                  extends AbstractController(cc) {
  implicit val createClassReads: Reads[CreateClassRequest] = Json.reads[CreateClassRequest]


  // Extract JWT from header

  private def extractToken(request: RequestHeader): Option[String] =
    request.headers.get("Authorization").collect {
      case header if header.startsWith("Bearer ") =>
        header.substring(7) // remove "Bearer "
    }



  // CREATE CLASS
  def createClass: Action[JsValue] = Action.async(parse.json) { request =>

    extractToken(request) match {
      case None =>
        Future.successful(
          ApiHandler.unauthorized("Missing Authorization token")
        )
      case Some(token) =>
        request.body.validate[CreateClassRequest].fold(
          errors =>
            Future.successful(
              ApiHandler.validationError(JsError.toJson(errors))
            ),
          data =>
            ApiHandler.handle(classService.createClass(data,token))
        )
    }
  }
}
