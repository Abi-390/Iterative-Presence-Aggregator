package models

import play.api.libs.json._

case class Students (
                   id :  Int,
                   classId :  Option[Int],
                   name : String,
                   rollNumber : Option[String]
                   )

object Students {
  implicit val format : OFormat[Students] = Json.format[Students]

  val safeWritesL:Writes[Students] = Writes{ student =>
   Json.obj(
     "id" -> student.id,
     "classId" -> student.classId,
     "name" -> student.name,
     "rollNumber" -> student.rollNumber
   )
  }
}
