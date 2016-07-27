package com.wallace.demo.rest.sever.demo.rest

import akka.actor.ActorRefFactory
import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest

/**
  * Created by 10192057 on 2016/6/17.
  */
class MyServiceSpec extends Specification with Specs2RouteTest with MyService {

  def actorRefFactory: ActorRefFactory = system

  "MyService" should {
    "return Json String" in {
      Get("/wallace") ~> myRoute ~> check {
        log.info(
          s"""
             |[Entity]: ${response.entity.asString}
             |[Entity => Option]: ${response.entity.toOption}
             |[Entity => Case Class]: ${responseAs[Person]}
             |[Message]: ${response.message}
             |[Protocol]: ${response.protocol}
             |[Status]: ${status.intValue}
           """.stripMargin)

        status.intValue mustEqual 200

        response.entity.toOption.get.asString must contain("{\"name\":[\"Wallace\",\"Lina\",\"Lucy\"],\"age\":[25,26,24]}")

        responseAs[Person].name must contain("Wallace")
        //responseAs[String] must contain("test")
      }

      Get("/wallace/index") ~> myRoute ~> check {
        status.intValue mustEqual 200

        response.entity.asString must contain("hello,world!")
      }
    }
  }
}
