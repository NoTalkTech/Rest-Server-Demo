package com.wallace.demo.rest.sever.demo

import akka.actor.{ActorRef, ActorSystem, Props}
import com.wallace.demo.rest.sever.demo.rest.MyServiceActor
import spray.servlet.WebBoot

/**
  * Created by wallace on 2018/8/26.
  */
class BootApp extends WebBoot {
  override def system: ActorSystem = Services.system

  val apiActor: ActorRef = system.actorOf(Props[MyServiceActor], "MyService")

  override def serviceActor: ActorRef = apiActor
}
