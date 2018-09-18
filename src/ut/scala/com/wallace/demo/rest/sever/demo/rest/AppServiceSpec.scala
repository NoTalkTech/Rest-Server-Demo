package com.wallace.demo.rest.sever.demo.rest

import akka.actor.ActorRefFactory
import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest

/**
  * Created by 10192057 on 2016/6/17.
  */
class AppServiceSpec extends Specification with Specs2RouteTest with AppServices {

  def actorRefFactory: ActorRefFactory = system

  case class NewPerson(name: String, age: Int)

  "ApiService" should {
    "get person" in {
      Get("/add?name=huangbiyu&age=27") ~> appRoute ~> check {
        log.info(
          s"""
             |[Entity]: ${response.entity.asString}
             |[Entity => Option]: ${response.entity.toOption}
             |[Entity => Case Class]: ${responseAs[Map[String, Int]]}
             |[Message]: ${response.message}
             |[Protocol]: ${response.protocol}
             |[Status]: ${status.intValue}
           """.stripMargin)

        status.intValue mustEqual 200

        response.entity.toOption.get.asString must contain("{\"Wallace\":25,\"Lina\":26,\"Lucy\":24,\"huangbiyu\":27}")

        responseAs[Map[String, Int]].get("Wallace") must beSome(25)
        //responseAs[String] must contain("test")
      }
    }
  }

  "ApiService" should {
    "get index" in {
      Get("/index") ~> appRoute ~> check {
        status.intValue mustEqual 200

        response.entity.asString must contain("hello,world!")
      }
    }
  }

  "ApiService" should {
    "post new person" in {
      val context: Option[NewPerson] = Option(NewPerson("wallace", 27))
      Post("/newperson", context) ~> appRoute ~> check {
        status.intValue mustEqual 200

        response.entity.asString must contain("success")
      }
    }
  }
}
