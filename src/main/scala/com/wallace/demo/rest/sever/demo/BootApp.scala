package com.wallace.demo.rest.sever.demo

import akka.actor.{ActorRef, ActorSystem, Props}
import com.wallace.demo.rest.sever.demo.rest.AppServiceActor
import spray.servlet.WebBoot

/**
  * Created by wallace on 2018/8/26.
  */
class BootApp extends WebBoot {
  override def system: ActorSystem = Services.system

  override def serviceActor: ActorRef =  system.actorOf(Props[AppServiceActor], "AppService")

  system.registerOnTermination {
    // put additional cleanup code here
    system.log.info("Application shut down")
  }
}
