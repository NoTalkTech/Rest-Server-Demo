package com.wallace.demo.rest.sever.demo

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import com.wallace.demo.rest.sever.demo.common.LogSupport
import com.wallace.demo.rest.sever.demo.rest.MyServiceActor
import spray.can.Http
import akka.pattern.ask

import scala.concurrent.duration._

/**
  * Created by 10192057 on 2016/6/16.
  */
object Boot extends App with LogSupport {
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  implicit val timeout = Timeout(5.seconds)

  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
