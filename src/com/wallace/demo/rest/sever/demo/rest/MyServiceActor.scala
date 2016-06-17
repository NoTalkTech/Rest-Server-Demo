package com.wallace.demo.rest.sever.demo.rest

import akka.actor.{Actor, ActorRefFactory}
import spray.routing._
import spray.http._
import MediaTypes._
import com.wallace.demo.rest.sever.demo.common.LogSupport
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport

/**
  * Created by 10192057 on 2016/6/16.
  */
class MyServiceActor extends Actor with MyService {
  def receive: Receive = runRoute(myRoute)

  def actorRefFactory: ActorRefFactory = context

}

trait MyService extends HttpService with Json4sSupport with LogSupport {

  implicit def json4sFormats: Formats = DefaultFormats

  val myRoute =
  //    path("test") {
  //      get {
  //        respondWithMediaType(`text/html`) {
  //          complete {
  //            try {
  //              <html>
  //                <body>
  //                  <h1>Say hello to
  //                    <i>spray-routing</i>
  //                    on
  //                    <i>spray-can</i>
  //                    !</h1>
  //                </body>
  //              </html>
  //            } catch {
  //              case e: Exception =>
  //                val msg = "Respond Failed!"
  //                HttpResponse(500, msg)
  //
  //            }
  //          }
  //
  //        }
  //      }
  //    } ~
    path("wallace") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            try {
              Person(List("Wallace", "Lina", "Lucy"), List(25, 26, 24))
            } catch {
              case e: Exception =>
                val msg = "Respond Failed!"
                HttpResponse(500, msg)
            }
          }
        }
      }
    } ~
    path("wallace" / "index") {
      get {
        complete {
          "hello,world!"
        }
      }
    }
}

