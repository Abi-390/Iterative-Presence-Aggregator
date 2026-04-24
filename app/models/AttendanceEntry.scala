package models

import play.api.libs.json._

case class AttendanceEntry (
                           id : Int,
                           recordId : Option[Int],
                           studentId : Option[Int],
                           isPresent : Boolean
                           )

object AttendanceEntry {
  implicit val format:OFormat[AttendanceEntry]  = Json.format[AttendanceEntry]

  val safeWrites:Writes[AttendanceEntry] = Writes{ attendance_ent =>
    Json.obj(
      "id" -> attendance_ent.id,
      "recordId" -> attendance_ent.recordId,
      "studentId" -> attendance_ent.studentId,
      "isPresent" -> attendance_ent.isPresent
    )
  }
}