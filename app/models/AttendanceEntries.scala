package models

import play.api.libs.json._

case class AttendanceEntries (
                           id : Int,
                           recordId : Option[Int],
                           studentId : Option[Int],
                           isPresent : Boolean
                           )

object AttendanceEntries {
  implicit val format:OFormat[AttendanceEntries]  = Json.format[AttendanceEntries]

  val safeWrites:Writes[AttendanceEntries] = Writes{ attendance_ent =>
    Json.obj(
      "id" -> attendance_ent.id,
      "recordId" -> attendance_ent.recordId,
      "studentId" -> attendance_ent.studentId,
      "isPresent" -> attendance_ent.isPresent
    )
  }
}