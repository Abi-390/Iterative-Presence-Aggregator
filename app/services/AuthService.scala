package services

import models.Users
import db.UserContext
import dtos.{AuthResponse, CreateUserRequest, LoginRequest, UserResponse}
import utils.{ApiError, JwtUtil, PasswordUtil}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthService @Inject()(userContext: UserContext)(implicit ec: ExecutionContext) {

  import userContext.ctx._

  private val emailRegex =
    """^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$""".r

  private def isValidEmail(email: String): Boolean =
    emailRegex.matches(email.trim.toLowerCase)

  //  REGISTER
  def register(req: CreateUserRequest): Future[Either[ApiError, AuthResponse]] = Future {

    //  Email validation
    if (!isValidEmail(req.email)) {
      Left(ApiError("Invalid email format"))
    }


    //  Password validation
    else if (req.password.trim.isEmpty) {
      Left(ApiError("Password cannot be empty"))
    }

    else {
      val existingUser = userContext.ctx.run(
        query[Users].filter(_.email == lift(req.email))
      ).headOption

      if (existingUser.isDefined) {
        Left(ApiError("Email already exists"))
      } else {

        val hashedPassword = PasswordUtil.hash(req.password)

        val newUser = Users(
          id = 0,
          fullName = req.fullName,
          email = req.email,
          passwordHash = hashedPassword,
          role = req.role.getOrElse("TEACHER")
        )

        val generatedId = userContext.ctx.run(
          query[Users].insertValue(lift(newUser)).returningGenerated(_.id)
        )

        val token = JwtUtil.generateToken(generatedId)

        Right(
          AuthResponse(
            accessToken = token,
            user = UserResponse(
              generatedId,
              newUser.fullName,
              newUser.email,
              newUser.role
            )
          )
        )
      }
    }
  }

  //  LOGIN
  def login(req: LoginRequest): Future[Either[ApiError, AuthResponse]] = Future {

    val maybeUser = userContext.ctx.run(
      query[Users].filter(_.email == lift(req.email))
    ).headOption

    maybeUser match {

      case None =>
        Left(ApiError("User not found"))

      case Some(user) =>
        if (!PasswordUtil.verify(req.password, user.passwordHash)) {
          Left(ApiError("Invalid password"))
        } else {

          val token = JwtUtil.generateToken(user.id)

          Right(
            AuthResponse(
              accessToken = token,
              user = UserResponse(
                user.id,
                user.fullName,
                user.email,
                user.role
              )
            )
          )
        }
    }
  }
}