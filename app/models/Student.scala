package models

import play.api.libs.json._

case class Student (
                   id :  Int,
                   classId :  Option[Int],
                   name : String,
                   rollNumber : Option[String]
                   )

object Student {
  implicit val format : OFormat[Student] = Json.format[Student]

  val safeWritesL:Writes[Student] = Writes{ student =>
   Json.obj(
     "id" -> student.id,
     "classId" -> student.classId,
     "name" -> student.name,
     "rollNumber" -> student.rollNumber
   )
  }
}
