package com.wallace.demo.rest.sever.demo.rest

import akka.actor.{Actor, ActorRefFactory}
import com.wallace.demo.rest.sever.demo.common.LogSupport
import org.json4s.{DefaultFormats, Formats}
import spray.http.MediaTypes._
import spray.http._
import spray.httpx.Json4sSupport
import spray.routing._

/**
  * Created by 10192057 on 2016/6/16.
  */
class MyServiceActor extends Actor with MyService {
  def receive: Receive = runRoute(myRoute)

  def actorRefFactory: ActorRefFactory = context

}

trait MyService extends HttpService with Json4sSupport with LogSupport {

  implicit def json4sFormats: Formats = DefaultFormats

  //  implicit def str2drillReqHandlerColumn(json: String): PrbColumnLinkage = {
  //    try {
  //      val gson = new Gson
  //      val mapType = new TypeToken[PrbColumnLinkage] {}.getType
  //      gson.fromJson(json, mapType).asInstanceOf[PrbColumnLinkage]
  //    } catch {
  //      case t: Throwable => t.printStackTrace(); null;
  //    }
  //  }

  val myRoute: Route =
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
        parameter('name, 'age) {
          (name: String, age: String) =>
            detach() {
              respondWithMediaType(`application/json`) {
                complete {
                  try {
                    val p = Person(List("Wallace", "Lina", "Lucy", name), List(25, 26, 24, age.toInt))
                    val data: Map[String, Int] = p.name.zip(p.age).toMap
                    data
                  } catch {
                    case e: Exception =>
                      val msg = "Respond Failed!"
                      HttpResponse(500, msg)
                  }
                }
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

