package com.wallace.demo.rest.server.demo

import org.scalatest.{BeforeAndAfter, FeatureSpec, GivenWhenThen}
import org.specs2.matcher.ShouldMatchers
import spray.testkit.ScalatestRouteTest

/**
  * Created by 10192057 on 2018/4/17 0017.
  */
abstract class AccSpec extends FeatureSpec with ShouldMatchers with GivenWhenThen with BeforeAndAfter with ScalatestRouteTest {
  //feature("Query") {
  //    scenario("场景1") {
  //Given("params")
  // When("Execute query")
  // Get().withHeaders(`Remote-Address`(Ip)) ~> myRoute ~> check {
  //        status.intValue should be(200)
  //        result = responseAs[GisFile]
  //      }
  // Then("Get expect result")
  //}
  //}
}
