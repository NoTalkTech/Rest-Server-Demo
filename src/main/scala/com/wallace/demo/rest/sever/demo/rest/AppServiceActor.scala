package com.wallace.demo.rest.sever.demo.rest

import akka.actor.{Actor, ActorRefFactory}
import com.wallace.demo.rest.sever.demo.apiservices.app.common.{NewPerson, Person}
import com.wallace.demo.rest.sever.demo.common.LogSupport
import com.wallace.demo.rest.sever.demo.database.DataBaseInfo
import org.json4s.{DefaultFormats, Formats}
import spray.http.MediaTypes._
import spray.http._
import spray.httpx.Json4sSupport
import spray.routing._

/**
  * Created by 10192057 on 2016/6/16.
  */
class AppServiceActor extends Actor with AppServices {
  def receive: Receive = runRoute(appRoute)

  def actorRefFactory: ActorRefFactory = context

}

trait AppServices extends HttpService with Json4sSupport with LogSupport {

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

  val appRoute: Route =
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
    path("add") {
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
      path("index") {
        get {
          complete {
            "hello,world!"
          }
        }
      } ~
      path("redisclient") {
        post {
          decompressRequest() {
            entity(as[DataBaseInfo]) { DataBaseInfo =>
              detach() {
                complete {
                  try {
                    //TODO Add redis client service
                    DataBaseInfo.toString
                  } catch {
                    case e: Exception =>
                      HttpResponse(500, e.getMessage)
                  }
                }
              }
            }
          }
        }
      } ~
      path("newperson") {
        post {
          decompressRequest() {
            entity(as[NewPerson]) {
              person =>
                detach() {
                  complete {
                    try {
                      //TODO Add redis client service
                      log.info(person.toString)
                      "success"
                    } catch {
                      case e: Exception =>
                        HttpResponse(500, e.getMessage)
                    }
                  }
                }
            }
          }
        }
      }
}
