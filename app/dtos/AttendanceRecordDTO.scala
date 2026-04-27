package dtos

import play.api.libs.json._
import java.time.LocalDate

/**
 * Used to create attendance record (per class per day)
 */
case class CreateAttendanceRecordRequest(
                                          classId: Option[Int],
                                          date: LocalDate
                                        )

object CreateAttendanceRecordRequest {
  implicit val reads: Reads[CreateAttendanceRecordRequest] = Json.reads[CreateAttendanceRecordRequest]
}
/**
 * Used to send attendance record to frontend
 */
case class AttendanceRecordResponse(
                                     id: Int,
                                     classId: Option[Int],
                                     date: LocalDate
                                   )

object AttendanceRecordResponse {
  implicit val writes: OWrites[AttendanceRecordResponse] = Json.writes[AttendanceRecordResponse]
}
/**
 * Used to return list of attendance records
 */
case class AttendanceRecordListResponse(
                                         records: Seq[AttendanceRecordResponse]
                                       )

object AttendanceRecordListResponse {
  implicit val writes: OWrites[AttendanceRecordListResponse] = Json.writes[AttendanceRecordListResponse]
}