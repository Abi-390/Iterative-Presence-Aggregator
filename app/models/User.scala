package models

import play.api.libs.json._


object UserRole {
  val Admin = "ADMIN"
  val Teacher = "TEACHER"
}

case class User(
                 id: Int,
                 fullName: String,
                 email: String,
                 passwordHash: String,
                 role: String = UserRole.Teacher
               )

object User {
  // standard JSON format
  implicit val format: OFormat[User] = Json.format[User]

  // Safe writes: passwordHash frontend t push kra nai
  val safeWrites: Writes[User] = Writes { user =>
    Json.obj(
      "id" -> user.id,
      "fullName" -> user.fullName,
      "email" -> user.email,
      "role" -> user.role
    )
  }
}