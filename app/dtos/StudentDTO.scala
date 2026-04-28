package dtos

import play.api.libs.json._

/**
 * Used to create a new student
 */
case class  CreateStudentRequest(
                                name:String,
                                classId:Option[Int],
                                rollNumber:Option[String]
                                )
object CreateStudentRequest {
  implicit val reads: Reads[CreateStudentRequest] = Json.reads[CreateStudentRequest]
}

/**
 *  Purpose:
 *  - Used to update student details
 *  Note:
 * - All fields optional (partial update)
 */

case class UpdateStudentRequest(
                               name:Option[String],
                               classId:Option[Int],
                               rollNumber:Option[String]
                               )
object UpdateStudentRequest {
  implicit val reads: Reads[UpdateStudentRequest] = Json.reads[UpdateStudentRequest]
}

/**
 * Used to send student data to frontend
 */
case class  StudentResponse(
                           id:Int,
                           name:String,
                           classId:Option[Int],
                           rollNumber:Option[String]
                           )
object StudentResponse {
  implicit val writes: OWrites[StudentResponse] = Json.writes[StudentResponse]
}

/**
 * DTO: StudentListResponse
 *
 * Purpose:
 * - Used to return list of students
 *
 * Notes:
 * - Wraps multiple StudentResponse objects
 */
case class StudentListResponse(
                                students: Seq[StudentResponse]
                              )

object StudentListResponse {
  implicit val writes: OWrites[StudentListResponse] = Json.writes[StudentListResponse]
}