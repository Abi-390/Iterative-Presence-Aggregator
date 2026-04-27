package models

import play.api.libs.json._


object UserRole {
  val Admin = "ADMIN"
  val Teacher = "TEACHER"
}

case class Users(
                 id: Int,
                 fullName: String,
                 email: String,
                 passwordHash: String,
                 role: String = UserRole.Teacher
               )

object Users {
  // standard JSON format
  implicit val format: OFormat[Users] = Json.format[Users]

  // Safe writes: passwordHash frontend t push kra nai
  val safeWrites: Writes[Users] = Writes { user =>
    Json.obj(
      "id" -> user.id,
      "fullName" -> user.fullName,
      "email" -> user.email,
      "role" -> user.role
    )
  }
}