package com.wallace.demo.rest.sever.demo.rest

import akka.actor.{Actor, ActorRefFactory}
import com.wallace.demo.rest.sever.demo.common.LogSupport
import org.json4s.{DefaultFormats, Formats}
import spray.http.HttpResponse
import spray.http.MediaTypes._
import spray.httpx.Json4sSupport
import spray.routing.{HttpService, Route}

/**
  * Created by wallace on 2018/8/27.
  */
class RedisClientActor extends Actor with RedisClientService {
  def actorRefFactory: ActorRefFactory = context

  def receive: Receive = runRoute(redisClientRoute)
}

trait RedisClientService extends HttpService with Json4sSupport with LogSupport {
  implicit def json4sFormats: Formats = DefaultFormats

  val redisClientRoute: Route =
    path("query") {
      get {
        parameter('key) {
          (key: String) =>
            detach() {
              respondWithMediaType(`application/json`) {
                complete {
                  try {

                    ???
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
    }

}
