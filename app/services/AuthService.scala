package services

import models.Users
import db.UserContext
import dtos.{AuthResponse, CreateUserRequest, LoginRequest, UserResponse}
import utils.{JwtUtil, PasswordUtil}

import javax.inject.{Inject, Singleton}

@Singleton class AuthService @Inject()(userContext: UserContext) {


  import userContext.ctx._

  private val emailRegex =
    """^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$""".r

  private def isValidEmail(email: String): Boolean =
    emailRegex.matches(email.trim.toLowerCase)

  def register(req: CreateUserRequest): AuthResponse = {

    val hashedPassword = PasswordUtil.hash(req.password)

    val newUser = Users(0, req.fullName, req.email, hashedPassword, req.role.getOrElse("TEACHER"))

    val generatedId = userContext.ctx.run(
      query[Users].insertValue(lift(newUser)).returningGenerated(_.id)
    )

    val token = JwtUtil.generateToken(generatedId)

    AuthResponse(
      accessToken = token,
      user = dtos.UserResponse(generatedId, newUser.fullName, newUser.email, newUser.role)
    )


  }

  def login(req: LoginRequest): Option[AuthResponse] = {
    val maybeUser = userContext.ctx.run(
      query[Users].filter(u => u.email == lift(req.email))
    ).headOption

    maybeUser.flatMap(user =>
      if (PasswordUtil.verify(req.password, user.passwordHash)) {
        val token = JwtUtil.generateToken(user.id)
        Some(AuthResponse(
          accessToken = token, user = UserResponse(user.id, user.fullName, user.email, user.role)
        ))
      } else {
        None
      })
  }

}



