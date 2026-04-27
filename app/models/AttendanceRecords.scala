package models

import play.api.libs.json._

case class AttendanceRecords(
                      id : Int,
                      classId : Option[Int],
                      date : java.time.LocalDate
                           )

object AttendanceRecords {
  implicit val format : OFormat[AttendanceRecords] = Json.format[AttendanceRecords]

  val safeWrites:Writes[AttendanceRecords] = Writes { attendance_rec =>
    Json.obj(
      "id" -> attendance_rec.id,
      "classId" -> attendance_rec.classId,
      "date" -> attendance_rec.date
    )
  }
}
