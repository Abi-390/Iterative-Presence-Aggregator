package services

import models.Classes
import models.Users
import db.ClassContext
import  utils.{ApiError,JwtUtil}
import dtos.{CreateClassRequest,ClassResponse}
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ClassService @Inject()(classContext: ClassContext)(implicit ec: ExecutionContext) {
     import classContext.ctx._

     //CREATE CLASS
     def createClass(
                      req: CreateClassRequest,
                      token: String
                    ): Future[Either[ApiError, ClassResponse]] = Future {

          //  VERIFY JWT
          JwtUtil.verifyToken(token) match {

               case None =>
                    Left(ApiError("Unauthorized: Invalid or expired token"))

               case Some(id) =>

                    //  Validation
                    if (req.name.trim.isEmpty) {
                         Left(ApiError("Class name can't be empty"))
                    } else {

                         val newClass = Classes(
                              id = 0,
                              teacherId = req.teacherId.orElse(Some(id)), // fallback to token user
                              name = req.name
                         )

                         val generatedId = classContext.ctx.run {
                              query[Classes]
                                .insertValue(lift(newClass))
                                .returningGenerated(_.id)
                         }

                         Right(
                              ClassResponse(
                                   id = generatedId,
                                   teacherId = newClass.teacherId,
                                   name = newClass.name
                              )
                         )
                    }
          }
     }


}
