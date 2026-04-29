package dtos

import play.api.libs.json._


/**
 * DTO: CreateClassRequest
 *
 * Purpose:
 * - Used to create a new class
 */
case class CreateClassRequest(
                             name:String,
                             teacherId:Option[Int]
                             )
object CreateClassRequest {
  implicit val reads: Reads[CreateClassRequest] = Json.reads[CreateClassRequest]
}

/**
 * DTO: UpdateClassRequest
 *
 * Purpose:
 * - Used to update class details
 * - Supports partial updates
 */
case class UpdateClassRequest(
                             name:Option[String],
                             teacherId:Option[Int]
                             )
object UpdateClassRequest {
  implicit val reads: Reads[UpdateClassRequest] = Json.reads[UpdateClassRequest]
}

/**
 * DTO: ClassResponse
 *
 * Purpose:
 * - Used to send class data to frontend
 */
case class ClassResponse(
                        id:Int,
                        teacherId:Option[Int],
                        name:String
                        )
object ClassResponse{
  implicit val writes:OWrites[ClassResponse]=Json.writes[ClassResponse]
}

/**
 * DTO: ClassListResponse
 *
 * Purpose:
 * - Used to return multiple classes
 *
 * Notes:
 * -Wraps multiple class as objects
 */
case class ClassListResponse(
                              classes: Seq[ClassResponse]
                            )

object ClassListResponse {
  implicit val writes: OWrites[ClassListResponse] = Json.writes[ClassListResponse]
}