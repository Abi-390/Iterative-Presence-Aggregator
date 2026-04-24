package utils


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.typesafe.config.ConfigFactory


// Like jwt helper in Node:
// jwt.sign({ userId }, secret)
// jwt.verify(token, secret)
object JwtUtil {
  // In production read from application.conf
  // For now hardcoded — we'll improve later
  private val config = ConfigFactory.load()
  private val secret= config.getString("jwt.secret")
  private val algorithm = Algorithm.HMAC256(secret)
  private val issuer = "student_management"


  // Generate token — like jwt.sign({ userId, email }, secret)
  def generateToken(userId:String,email:String):String=
    JWT.create()
      .withIssuer(issuer)
      .withClaim("userId", userId)// like payload.userId in Node
      .withClaim("email", email) // like payload.email in Node
      .sign(algorithm)

    // Verify and decode token
    // Returns Option[String] — Some(userId) if valid, None if invalid/expired
    // Like: try { jwt.verify(token, secret) } catch(err) { null }
    def verifyToken(token:String)=
         try {
           val decoded = JWT
           .require(algorithm)
           .withIssuer(issuer)
           .build()
           .verify(token)
           Some(decoded.getClaim("userId").asString())
           // returns the userId from inside the token
           } catch {
           case _: JWTVerificationException => None
           // token invalid or expired → return None
           // like catch block returning null in Node
           }
}
