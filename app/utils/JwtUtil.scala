package utils


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.typesafe.config.ConfigFactory


object JwtUtil {
  private val config = ConfigFactory.load()
  private val secret= config.getString("jwt.secret")
  private val algorithm = Algorithm.HMAC256(secret)
  private val issuer = "student_management"


  // Generate token — like jwt.sign({ userId, email }, secret)
  def generateToken(id:String,email:String):String=
    JWT.create()
      .withIssuer(issuer)
      .withClaim("id", id)// like payload.userId in Node
      .withClaim("email", email) // like payload.email in Node
      .sign(algorithm)


    def verifyToken(token:String)=
         try {
           val decoded = JWT
           .require(algorithm)
           .withIssuer(issuer)
           .build()
           .verify(token)
           Some(decoded.getClaim("id").asInt())
           // returns the userId from inside the token
           } catch {
           case _: JWTVerificationException => None
           // token invalid or expired → return None
           // like catch block returning null in Node
           }
}
