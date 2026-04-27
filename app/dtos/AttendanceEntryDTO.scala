package dtos

import play.api.libs.json._

/**
 * DTO: CreateAttendanceEntryRequest
 *
 * Purpose:
 * - Used to create a new attendance entry
 *
 */
case class CreateAttendanceEntryRequest(
                                       recordId:Option[Int],
                                       studentId:Option[Int],
                                       isPresent:Boolean
                                       )
object CreateAttendanceEntryRequest {
  implicit val reads: Reads[CreateAttendanceEntryRequest] = Json.reads[CreateAttendanceEntryRequest]
}


/**
 * DTO: UpdateAttendanceEntryRequest
 *
 * Purpose:
 * - Used to update attendance entry
 *
 * Notes:
 * - Partial update supported
 */
case class UpdateAttendanceEntryRequest(
                                         recordId: Option[Int],
                                         studentId: Option[Int],
                                         isPresent: Option[Boolean]
                                       )
object UpdateAttendanceEntryRequest {
  implicit val reads: Reads[UpdateAttendanceEntryRequest] = Json.reads[UpdateAttendanceEntryRequest]
}

/**
 * DTO: AttendanceEntryResponse
 *
 * Purpose:
 * - Used to send attendance data to frontend
 */

case class AttendanceEntryResponse(
                                  id:Int,
                                  recordId: Option[Int],
                                  studentId: Option[Int],
                                  isPresent: Boolean
                                  )
object AttendanceEntryResponse {
  implicit val writes: OWrites[AttendanceEntryResponse] = Json.writes[AttendanceEntryResponse]
}

/**
 * DTO: AttendanceEntryListResponse
 *
 * Purpose:
 * - Used to return list of attendance entries
 */
case class AttendanceEntryListResponse(
                                        entries: Seq[AttendanceEntryResponse]
                                      )

object AttendanceEntryListResponse {
  implicit val writes: OWrites[AttendanceEntryListResponse] = Json.writes[AttendanceEntryListResponse]
}