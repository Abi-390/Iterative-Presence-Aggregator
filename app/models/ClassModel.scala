package models

import play.api.libs.json._

case class ClassModel(
                       id: Int,
                       teacherId: Option[Int],
                       name: String
                     )

object ClassModel {

  implicit val format: OFormat[ClassModel] = Json.format[ClassModel]

  val safeWrites: Writes[ClassModel] = Writes { class_model =>
    Json.obj(
      "id" -> class_model.id,
      "teacherId" -> class_model.teacherId,
      "name" -> class_model.name
    )
  }
}