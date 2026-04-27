package models

import play.api.libs.json._

case class Classes(
                       id: Int,
                       teacherId: Option[Int],
                       name: String
                     )

object Classes {

  implicit val format: OFormat[Classes] = Json.format[Classes]

  val safeWrites: Writes[Classes] = Writes { class_model =>
    Json.obj(
      "id" -> class_model.id,
      "teacherId" -> class_model.teacherId,
      "name" -> class_model.name
    )
  }
}