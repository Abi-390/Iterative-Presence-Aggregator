package dtos

import play.api.libs.json._


/**
 * DTO: CreateUserRequest
 *
 * Purpose:
 * - Used when creating/registering a new user (Teacher/Admin)
 * - Accepts raw password from frontend (will be hashed in service layer)
 *
 * Notes:
 * - role is optional → default role can be assigned in service (e.g., TEACHER)
 */
case class CreateUserRequest(
                            fullName: String,
                            email:String,
                            password:String,
                            role:Option[String]  //by default TEACHER
                            )
object CreateUserRequest {
  // Converts incoming JSON → CreateUserRequest
  implicit val reads: Reads[CreateUserRequest] = Json.reads[CreateUserRequest]
}


/**
 * DTO: LoginRequest
 *
 * Purpose:
 * - Used for user authentication (login)
 * - Takes email and password from frontend
 *
 * Notes:
 * - Password will be verified using PasswordUtil (BCrypt)
 */
case class LoginRequest(
                       email:String,
                       password:String
                       )

object LoginRequest {
  // Converts incoming JSON → LoginRequest
  implicit val reads:Reads[LoginRequest] = Json.reads[LoginRequest]
}

/**
 * DTO: UserResponse
 *
 * Purpose:
 * - Used to send user data back to frontend
 *
 * Security:
 * - Does NOT include passwordHash (very important)
 *
 * Notes:
 * - Always use this DTO instead of exposing User model directly
 */

case class UserResponse(
                       id:Int,
                       fullname:String,
                       email:String,
                       role:String
                       )

object UserResponse {
  // Converts UserResponse → JSON (safe output)
  implicit val writes: OWrites[UserResponse] = Json.writes[UserResponse]
}

/**
 * DTO: AuthResponse
 *
 * Purpose:
 * - Used after successful login/register
 * - Returns JWT token + user details
 *
 * Notes:
 * - Token is separated from UserResponse (clean architecture)
 */
case class AuthResponse(
                         accessToken: String,
                         user: UserResponse
                       )
object AuthResponse {
  // Converts AuthResponse → JSON
  implicit val writes: OWrites[AuthResponse] = Json.writes[AuthResponse]
}
/**
 * DTO: UpdateUserRequest
 *
 * Purpose:
 * - Used to update existing user details
 *
 * Notes:
 * - All fields are optional → supports partial updates (PATCH behavior)
 * - Only provided fields will be updated
 */
case class UpdateUserRequest(
                               fullName: Option[String],
                               email: Option[String],
                               role: Option[String]
                             )

object UpdateUserRequest {
  // Converts incoming JSON → UpdateUserRequest
  implicit val reads: Reads[UpdateUserRequest] = Json.reads[UpdateUserRequest]
}