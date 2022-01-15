package com.github.makssobolevs

import akka.actor.ActorSystem
import akka.actor.Props
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.makssobolevs.TestActor.Message
import akka.pattern.ask
import akka.util.Timeout
import com.github.makssobolevs.TestActor.ReplyMessage

import scala.concurrent.duration.DurationInt


object App {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Test")
    implicit val ec = system.dispatcher

    val testActor = system.actorOf(Props(new TestActor))

    implicit val timeout = Timeout(10.seconds)


    val testRoute: Route =
      pathPrefix("test") {
        get {
          val result = testActor ? Message("test")
          onSuccess(result) {
            case ReplyMessage(reply) =>
              complete(HttpResponse(StatusCodes.OK, entity = reply))
          }
        }
      }

    Http()
      .newServerAt("localhost", 8080)
      .bind(testRoute)
  }
}
