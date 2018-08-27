package com.wallace.demo.rest.sever.demo

import akka.actor.{Actor, ActorRef, ActorRefFactory, ActorSystem, Props}
import com.wallace.demo.rest.sever.demo.rest.AppServiceActor
import spray.routing.HttpService
import spray.servlet.WebBoot

/**
  * Created by wallace on 2018/8/26.
  */
class BootApp extends WebBoot {
  override def system: ActorSystem = Services.system

  val appServiceActor: ActorRef = system.actorOf(Props[AppServiceActor], "AppService")

  class RootServiceActor extends Actor with HttpService {
    override def receive: Receive = runRoute {
      path("test" / "pwd") {
        complete(System getProperty "user.dir")
      } ~
        pathPrefix("wallace") {
          appServiceActor ! _
        }
    }

    def actorRefFactory: ActorRefFactory = context
  }

  val serviceActor: ActorRef = system.actorOf(Props(new RootServiceActor))

  system.registerOnTermination {
    // put additional cleanup code here
    system.log.info("Application shut down")
  }
}
