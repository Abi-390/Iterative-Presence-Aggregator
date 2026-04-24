package models

import play.api.libs.json._

case class AttendanceRecord(
                      id : Int,
                      classId : Option[Int],
                      date : java.time.LocalDate
                           )

object AttendanceRecord {
  implicit val format : OFormat[AttendanceRecord] = Json.format[AttendanceRecord]

  val safeWrites:Writes[AttendanceRecord] = Writes { attendance_rec =>
    Json.obj(
      "id" -> attendance_rec.id,
      "classId" -> attendance_rec.classId,
      "date" -> attendance_rec.date
    )
  }
}
