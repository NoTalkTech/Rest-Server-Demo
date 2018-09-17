package com.wallace.demo.rest.sever.demo

import akka.actor.{ActorRefFactory, ActorSystem}

/**
  * Created by wallace on 2018/8/26.
  */
package object Services {

  val defaultName = "Boot-Services"

  implicit val system: ActorSystem = ActorSystem(defaultName)

  implicit def actorRefFactory: ActorRefFactory = system

}
